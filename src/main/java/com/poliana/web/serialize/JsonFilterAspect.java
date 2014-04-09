package com.poliana.web.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author David Gilmore
 * @date 4/7/14
 */
@Aspect
public class JsonFilterAspect {

    protected final Logger log = Logger.getLogger(getClass());

    @Pointcut("execution(@JsonFilter public * com.poliana.web..*.*(..))")
    public void filterJsonResponses() {}

    @Around("filterJsonResponses()")
    public void around(ProceedingJoinPoint method) throws Throwable {

        MethodSignature msig = (MethodSignature) method.getSignature();

        JsonFilter annotation = msig.getMethod().getAnnotation(JsonFilter.class);

        final Class<?> mixin = annotation.mixin();

        ObjectMapper mapper = new ObjectMapper();

        mapper.addMixInAnnotations(msig.getMethod().getReturnType(), mixin);

        try {
            mapper.writeValue(WebContext.getInstance().getResponse().getOutputStream(), method.proceed());
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
