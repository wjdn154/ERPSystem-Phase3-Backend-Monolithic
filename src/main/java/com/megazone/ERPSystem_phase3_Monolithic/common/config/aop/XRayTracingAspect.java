package com.megazone.ERPSystem_phase3_Monolithic.common.config.aop;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.entities.Segment;
import com.amazonaws.xray.entities.Subsegment;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class XRayTracingAspect {

    // 스레드 로컬로 메인 세그먼트 생성 여부를 확인
    private static final ThreadLocal<Boolean> mainSegmentCreated = ThreadLocal.withInitial(() -> false);

    private final HttpServletRequest httpServletRequest;

    // HttpServletRequest를 주입받기 위한 생성자
    public XRayTracingAspect(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    // HTTP 요청 트레이싱
    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object traceHttpRequests(ProceedingJoinPoint joinPoint) throws Throwable {
        String requestURI = httpServletRequest.getRequestURI(); // 요청 URI
        String method = httpServletRequest.getMethod(); // HTTP 메서드 (GET, POST 등)

        // HTTP 요청에 대한 메인 세그먼트 생성
        Segment segment = AWSXRay.beginSegment("HTTP-" + method + " " + requestURI);

        Object result;
        try {
            result = joinPoint.proceed(); // HTTP 요청 처리
        } catch (Exception e) {
            segment.addException(e); // 예외 기록
            throw e;
        } finally {
            AWSXRay.endSegment(); // HTTP 요청 세그먼트 종료
        }
        return result;
    }

    // 서비스 Layer 메소드 호출 전후로 메인 세그먼트 생성
    @Around("execution(* com.megazone.ERPSystem_phase3_Monolithic.financial.service..*(..)) || " +
            "execution(* com.megazone.ERPSystem_phase3_Monolithic.hr.service..*(..)) || " +
            "execution(* com.megazone.ERPSystem_phase3_Monolithic.logistics.service..*(..)) || " +
            "execution(* com.megazone.ERPSystem_phase3_Monolithic.production.service..*(..))")
    public Object traceServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();

        // 메인 세그먼트가 이미 생성된 경우 새로 생성하지 않음
        if (mainSegmentCreated.get()) {
            return joinPoint.proceed();
        }

        // 메인 세그먼트 생성
        Segment segment = AWSXRay.beginSegment("Service-" + methodName);
        mainSegmentCreated.set(true);  // 플래그 설정

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            segment.addException(e);
            throw e;
        } finally {
            AWSXRay.endSegment(); // 메인 세그먼트 종료
            mainSegmentCreated.remove(); // 트랜잭션 종료 후 플래그 초기화
        }
        return result;
    }

    // 리포지토리 Layer 메소드 호출 전후로 서브세그먼트 생성
    @Around("execution(* com.megazone.ERPSystem_phase3_Monolithic.financial.repository..*(..)) || " +
            "execution(* com.megazone.ERPSystem_phase3_Monolithic.hr.repository..*(..)) || " +
            "execution(* com.megazone.ERPSystem_phase3_Monolithic.logistics.repository..*(..)) || " +
            "execution(* com.megazone.ERPSystem_phase3_Monolithic.production.repository..*(..))")
    public Object traceRepositoryMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();

        // 메인 세그먼트가 없는 경우 서브세그먼트 생성 생략
        if (!AWSXRay.getCurrentSegmentOptional().isPresent()) {
            return joinPoint.proceed();
        }

        Subsegment subsegment = AWSXRay.beginSubsegment("Repository-" + methodName);

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            subsegment.addException(e);
            throw e;
        } finally {
            AWSXRay.endSubsegment(); // 서브세그먼트 종료
        }
        return result;
    }
}
