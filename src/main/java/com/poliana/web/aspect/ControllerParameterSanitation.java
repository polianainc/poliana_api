package com.poliana.web.aspect;

import com.poliana.web.util.RestPreconditions;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author David Gilmore
 * @date 3/17/14
 */
@Aspect
public class ControllerParameterSanitation {

    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void controller() {}

    @Pointcut("args(bioguideId, ..)")
    public void bioguidePointcut(String bioguideId) {}

    @Pointcut("within(@org.springframework.web.bind.annotation.RequestMapping *)")
    public void requestMapping() {}

    @Around("controller() && bioguidePointcut(bioguideId) && requestMapping()")
    public String aroundControllerWithBioguideIdMethod(JoinPoint joinPoint, String bioguideId) throws Throwable {

        bioguideId = bioguideId.toUpperCase();

        RestPreconditions.checkValidBioguideId(bioguideId);

        return bioguideId;
    }

//    @AfterReturning("controller() && bioguidePointcut() && requestMapping()")
//    public void afterControllerMethod(JoinPoint joinPoint) {
//    }

}
