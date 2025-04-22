package me.vasujain.mfo_practical_13.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // @Before advice - executes before the method
    @Before("execution(* me.vasujain.mfo_practical_13.service.ProductService.createProduct(..))")
    public void logBeforeProductCreation(JoinPoint joinPoint) {
        logger.info("[Before] Method called: {} with arguments: {}",
                joinPoint.getSignature().getName(),
                joinPoint.getArgs());
    }

    // @After advice - executes after the method (regardless of outcome)
    @After("execution(* me.vasujain.mfo_practical_13.service.ProductService.getProductById(..))")
    public void logAfterProductRetrieval(JoinPoint joinPoint) {
        logger.info("[After] Completed: {} with arguments: {}",
                joinPoint.getSignature().getName(),
                joinPoint.getArgs());
    }

    // @AfterReturning advice - executes after successful method execution
    @AfterReturning(
            pointcut = "execution(* me.vasujain.mfo_practical_13.service.CategoryService.getCategoryById(..))",
            returning = "result")
    public void logAfterCategoryReturned(JoinPoint joinPoint, Object result) {
        logger.info("[AfterReturning] Category fetched: {} using method: {}",
                result,
                joinPoint.getSignature().getName());
    }

    // @AfterThrowing advice - executes when an exception is thrown
    @AfterThrowing(
            pointcut = "execution(* me.vasujain.mfo_practical_13.service.ProductService.*(..))",
            throwing = "ex")
    public void logAfterExceptionThrown(JoinPoint joinPoint, Throwable ex) {
        logger.error("[AfterThrowing] Exception caught in {}: {}",
                joinPoint.getSignature().getName(),
                ex.getMessage());
    }

    // @Around advice - surrounds the method execution
    @Around("execution(* me.vasujain.mfo_practical_13.controller.*.*(..))")
    public Object trackExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        // Proceed with the method execution
        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        logger.info("⏱ [Around] Execution time of {}.{}: {} ms",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                duration);

        return result;
    }
}