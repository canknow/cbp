package com.canknow.cbp.base.monitor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;


@Aspect
@Component
@Slf4j
public class ExecuteTimeAspect {
    @Pointcut("@annotation(executeTime)")
    private void pointCut(ExecuteTime executeTime) {

    }

    @Around("pointCut(executeTime)")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint, ExecuteTime executeTime) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object proceed = proceedingJoinPoint.proceed();
        stopWatch.stop();

        log.info("execute-time-name : [{}], execution-time : [{}], class-method : [{}]",
                executeTime.name(),
                stopWatch.getTotalTimeMillis(),
                proceedingJoinPoint.getTarget().getClass().getName() + "." + proceedingJoinPoint.getSignature().getName());

        return proceed;
    }
}
