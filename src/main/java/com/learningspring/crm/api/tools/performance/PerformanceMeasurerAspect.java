package com.learningspring.crm.api.tools.performance;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class PerformanceMeasurerAspect {

    private static final Logger log = Logger.getLogger(PerformanceMeasurerAspect.class);

    @Around("execution(* com.learningspring.crm.api.controller.CustomerApiController.*(..))")
    public Object measureLatencyAdvice(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object retVal = pjp.proceed();
        stopWatch.stop();

        log.info(pjp.getSignature() + "["+ stopWatch.getTotalTimeMillis() + "ms.]");

        return retVal;
    }
}
