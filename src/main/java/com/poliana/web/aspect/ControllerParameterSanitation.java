package com.poliana.web.aspect;

import com.poliana.web.util.RestPreconditions;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * This class watches input to controllers and ensures fast failures for malformed ids and handles simple case resolutions
 *
 * @author David Gilmore
 * @date 3/17/14
 */
@Aspect
public class ControllerParameterSanitation {

    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void controller() {}

    @Pointcut("args(bioguideId, ..)")
    public void bioguide(String bioguideId) {}

    @Pointcut("execution(@org.springframework.web.bind.annotation.RequestMapping * *(.., @com.poliana.web.aspect.BioguideId (*), ..))")
    public void bioguideAnnotation() {}

    @Around("controller() && bioguideAnnotation() && bioguide(bioguideId)")
    public Object aroundControllerMethodWithBioguideId(ProceedingJoinPoint method, String bioguideId) throws Throwable {

        String newBioguideId = bioguideId.toUpperCase();

        RestPreconditions.checkValidBioguideId(newBioguideId);

        Object[] arguments = method.getArgs();

        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i] instanceof String && arguments[i].equals(bioguideId))
                arguments[i] = newBioguideId;
        }

        Object ret = method.proceed(arguments);

        return ret;
    }

    @Pointcut("args(industryId, ..)")
    public void industry(String industryId) {}

    @Pointcut("execution(@org.springframework.web.bind.annotation.RequestMapping * *(.., @com.poliana.web.aspect.IndustryId (*), ..))")
    public void industryAnnotation() {}

    @Around("controller() && industryAnnotation() && industry(industryId)")
    public Object aroundControllerMethodWithIndustryId(ProceedingJoinPoint method, String industryId) throws Throwable {

        String newIndustryId = industryId.toUpperCase();

        RestPreconditions.checkValidIndustryId(newIndustryId);

        Object[] arguments = method.getArgs();

        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i] instanceof String && arguments[i].equals(industryId))
                arguments[i] = newIndustryId;
        }

        Object ret = method.proceed(arguments);

        return ret;
    }

    @Pointcut("args(categoryId, ..)")
    public void industryCategory(String categoryId) {}

    @Pointcut("execution(@org.springframework.web.bind.annotation.RequestMapping * *(.., @com.poliana.web.aspect.IndustryCategoryId (*), ..))")
    public void industryCategoryAnnotation() {}

    @Around("controller() && industryCategoryAnnotation() && industryCategory(categoryId)")
    public Object aroundControllerMethodWithIndustryCategoryId(ProceedingJoinPoint method, String categoryId) throws Throwable {

        String newIndustryCategoryId = categoryId.toUpperCase();

        RestPreconditions.checkValidIndustryCategoryId(newIndustryCategoryId);

        Object[] arguments = method.getArgs();

        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i] instanceof String && arguments[i].equals(categoryId))
                arguments[i] = newIndustryCategoryId;
        }

        Object ret = method.proceed(arguments);

        return ret;
    }

    @Pointcut("args(pacId, ..)")
    public void pac(String pacId) {}

    @Pointcut("execution(@org.springframework.web.bind.annotation.RequestMapping * *(.., @com.poliana.web.aspect.PacId (*), ..))")
    public void pacAnnotation() {}

    @Around("controller() && pacAnnotation() && pac(pacId)")
    public Object aroundControllerMethodWithPacId(ProceedingJoinPoint method, String pacId) throws Throwable {

        String newPacId = pacId.toUpperCase();

        RestPreconditions.checkValidPacId(newPacId);

        Object[] arguments = method.getArgs();

        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i] instanceof String && arguments[i].equals(pacId))
                arguments[i] = newPacId;
        }

        Object ret = method.proceed(arguments);

        return ret;
    }
}
