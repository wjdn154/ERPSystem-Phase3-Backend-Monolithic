package com.megazone.ERPSystem_phase3_Monolithic.common.config.aop;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.entities.Segment;
import com.amazonaws.xray.entities.Subsegment;
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

    /**
     * 서비스 계층의 메소드 호출을 트레이싱
     */
    @Around("execution(* com.megazone.ERPSystem_phase3_Monolithic.financial.service..*(..)) || " +
            "execution(* com.megazone.ERPSystem_phase3_Monolithic.hr.service..*(..)) || " +
            "execution(* com.megazone.ERPSystem_phase3_Monolithic.logistics.service..*(..)) || " +
            "execution(* com.megazone.ERPSystem_phase3_Monolithic.production.service..*(..))")
    public Object traceServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();

        // 이미 세그먼트 또는 상위 서브세그먼트가 존재하는 경우 서브세그먼트를 생성
        if (AWSXRay.getCurrentSegmentOptional().isPresent()) {
            Subsegment subsegment = AWSXRay.beginSubsegment("Service-" + methodName);

            Object result;
            try {
                result = joinPoint.proceed();
            } catch (Exception e) {
                subsegment.addException(e); // 예외 추가
                throw e;
            } finally {
                AWSXRay.endSubsegment(); // 서브세그먼트 종료
            }
            return result;
        }

        // 새로운 메인 세그먼트를 생성
        Segment segment = AWSXRay.beginSegment("Service-" + methodName);
        mainSegmentCreated.set(true);

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            segment.addException(e); // 예외 추가
            throw e;
        } finally {
            AWSXRay.endSegment(); // 메인 세그먼트 종료
            mainSegmentCreated.remove(); // 플래그 초기화
        }
        return result;
    }

    /**
     * 리포지토리 계층의 메소드 호출을 트레이싱
     */
    @Around("execution(* com.megazone.ERPSystem_phase3_Monolithic.financial.repository..*(..)) || " +
            "execution(* com.megazone.ERPSystem_phase3_Monolithic.hr.repository..*(..)) || " +
            "execution(* com.megazone.ERPSystem_phase3_Monolithic.logistics.repository..*(..)) || " +
            "execution(* com.megazone.ERPSystem_phase3_Monolithic.production.repository..*(..))")
    public Object traceRepositoryMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();

        // 부모 세그먼트 또는 서브세그먼트가 없는 경우 서브세그먼트를 생성하지 않음
        if (!AWSXRay.getCurrentSegmentOptional().isPresent()) {
            return joinPoint.proceed();
        }

        // 서브세그먼트 생성
        Subsegment parentSubsegment = AWSXRay.beginSubsegment("Repository-" + methodName);
        Subsegment childSubsegment = AWSXRay.beginSubsegment("SubRepository-" + methodName);

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            childSubsegment.addException(e); // 자식 서브세그먼트에 예외 추가
            throw e;
        } finally {
            AWSXRay.endSubsegment(); // 자식 서브세그먼트 종료
            AWSXRay.endSubsegment(); // 부모 서브세그먼트 종료
        }
        return result;
    }
}
