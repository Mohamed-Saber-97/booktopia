package org.example.booktopia.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Order(0)
@Component
public class MeasurementAspect {
    private static final Logger logger = LoggerFactory.getLogger(MeasurementAspect.class);

    @Around(value = "execution(* org.example.booktopia.service..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Instant startTime = Instant.now();

        // Build log message
        StringBuilder logMessage = new StringBuilder("KPI: ");
        logMessage.append("[")
                  .append(joinPoint.getKind())
                  .append("] ")
                  .append("for: ")
                  .append(joinPoint.getSignature())
                  .append(" ")
                  .append("withArgs: (")
                  .append(Arrays.stream(joinPoint.getArgs())
                                .map(Object::toString)
                                .collect(Collectors.joining(",")))
                  .append(")");

        // Proceed with method execution
        Object returnValue = joinPoint.proceed();

        // Calculate execution time and log
        Duration executionTime = Duration.between(startTime, Instant.now());
        logMessage.append(" took: ")
                  .append(executionTime.toMillis())
                  .append(" ms.");
        logger.info(logMessage.toString());

        return returnValue;
    }
}
