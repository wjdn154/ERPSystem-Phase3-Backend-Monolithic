package com.megazone.ERPSystem_phase3_Monolithic.common.config.aop;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.entities.Segment;
import com.amazonaws.xray.entities.Subsegment;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // API 요청 전후로 로깅
    @Around("execution(* com.megazone.ERPSystem_phase3_Monolithic.financial.controller..*(..)) || " +
            "execution(* com.megazone.ERPSystem_phase3_Monolithic.hr.controller..*(..)) || " +
            "execution(* com.megazone.ERPSystem_phase3_Monolithic.logistics.controller..*(..)) || " +
            "execution(* com.megazone.ERPSystem_phase3_Monolithic.production.controller..*(..))")
    public Object logApiRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        logger.info("API 메소드 시작: " + methodName);
        Object result;
        try {
            result = joinPoint.proceed(); // 메소드 실행
            logger.info("API 메소드 종료: " + methodName);
        } catch (Exception e) {
            logger.error("API 메소드 실행 중 예외 발생: " + methodName, e);
            throw e;
        }
        return result;
    }

    // 서비스 Layer 메소드 호출 전후로 로깅 및 메인 세그먼트 생성
    @Around("execution(* com.megazone.ERPSystem_phase3_Monolithic.financial.service..*(..)) || " +
            "execution(* com.megazone.ERPSystem_phase3_Monolithic.hr.service..*(..)) || " +
            "execution(* com.megazone.ERPSystem_phase3_Monolithic.logistics.service..*(..)) || " +
            "execution(* com.megazone.ERPSystem_phase3_Monolithic.production.service..*(..))")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        logger.info("서비스 메소드 시작: " + methodName);

        // 메인 세그먼트 생성
        Segment segment = AWSXRay.beginSegment("Service-" + methodName);
        Object result;
        try {
            result = joinPoint.proceed();
            logger.info("서비스 메소드 종료: " + methodName);
        } catch (Exception e) {
            segment.addException(e); // 예외 기록
            logger.error("서비스 메소드 실행 중 예외 발생: " + methodName, e);
            throw e;
        } finally {
            AWSXRay.endSegment(); // 메인 세그먼트 종료
        }
        return result;
    }

    // 리포지토리 Layer 메소드 호출 전후로 로깅 및 조건부 서브세그먼트 생성
    @Around("execution(* com.megazone.ERPSystem_phase3_Monolithic.financial.repository..*(..)) || " +
            "execution(* com.megazone.ERPSystem_phase3_Monolithic.hr.repository..*(..)) || " +
            "execution(* com.megazone.ERPSystem_phase3_Monolithic.logistics.repository..*(..)) || " +
            "execution(* com.megazone.ERPSystem_phase3_Monolithic.production.repository..*(..))")
    public Object logRepositoryMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        logger.info("리포지토리 메소드 시작: " + methodName);

        // 메인 세그먼트가 있는지 확인 후 서브세그먼트 조건부 생성
        if (!AWSXRay.getCurrentSegmentOptional().isPresent()) {
//            logger.warn("메인 세그먼트가 없어 서브세그먼트를 생성하지 않고 실행합니다: " + methodName);
            return joinPoint.proceed();
        }

        Subsegment subsegment = AWSXRay.beginSubsegment("Repository-" + methodName);
        Object result;
        try {
            result = joinPoint.proceed();
            logger.info("리포지토리 메소드 종료: " + methodName);
        } catch (Exception e) {
            subsegment.addException(e); // 예외 기록
            logger.error("리포지토리 메소드 실행 중 예외 발생: " + methodName, e);
            throw e;
        } finally {
            AWSXRay.endSubsegment(); // 서브세그먼트 종료
        }
        return result;
    }
}
