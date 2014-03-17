package com.poliana.web.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

/**
 * @author David Gilmore
 * @date 3/17/14
 */
@Aspect
public class ControllerParameterSanitation {

    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void controller() {}

    @Pointcut("execution(* && args (bioguideId)")
    public void bioguidePointcut() {}

    @Pointcut("within(@org.springframework.web.bind.annotation.RequestMapping *)")
    public void requestMapping() {}

    @Before("controller() && bioguidePointcut() && requestMapping()")
    public void aroundControllerMethod(JoinPoint joinPoint) throws Throwable {
        System.out.println("Invoked: " + niceName(joinPoint));
    }

    @AfterReturning("controller() && bioguidePointcut() && requestMapping()")
    public void afterControllerMethod(JoinPoint joinPoint) {
    }

    private String niceName(JoinPoint joinPoint) {
        return joinPoint.getTarget().getClass()
                + "#" + joinPoint.getSignature().getName()
                + "\n\targs:" + Arrays.toString(joinPoint.getArgs());
    }
}
