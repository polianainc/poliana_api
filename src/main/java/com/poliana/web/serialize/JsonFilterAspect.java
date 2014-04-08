package com.poliana.web.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * @author David Gilmore
 * @date 4/7/14
 */
@Aspect
public class JsonFilterAspect {

    @Autowired
    private HttpMessageConverter messageConverter;

    protected final Logger log = Logger.getLogger(getClass());

    @Pointcut("execution(@JsonFilter public * com.poliana.web..*.*(..))")
    public void filterJsonResponses() {}

    @Around("filterJsonResponses()")
    public Object around(ProceedingJoinPoint method) throws Throwable {

        MethodSignature msig = (MethodSignature) method.getSignature();

        JsonFilter annotation = msig.getMethod().getAnnotation(JsonFilter.class);
        Class<?> mixin = annotation.mixin();

        MappingJackson2HttpMessageConverter converter = (MappingJackson2HttpMessageConverter) messageConverter;
        ObjectMapper mapper = converter.getObjectMapper();
        mapper.addMixInAnnotations(msig.getMethod().getReturnType(), mixin);

        converter.setObjectMapper(mapper);

        try {
            return method.proceed();
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
