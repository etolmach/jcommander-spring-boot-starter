package com.etolmach.spring.jcommander.impl;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.etolmach.spring.jcommander.JCommandParameterExtractor;
import com.etolmach.spring.jcommander.exception.ParameterNotFoundException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author etolmach
 */

@Component
@ConditionalOnSingleCandidate(JCommandParameterExtractor.class)
public class DefaultJCommandParameterExtractor implements JCommandParameterExtractor {

    private Map<Class<?>, Map<String, Field>> fieldsMaps = new HashMap<>();

    @Override
    public Object extract(String parameterName, Object parameterBean) {
        Field field = getField(parameterName, parameterBean.getClass());
        try {
            return field.get(parameterBean);
        } catch (IllegalAccessException e) {
            throw new ParameterException(e);
        }
    }

    /**
     * Get field by parameter name and and class (with caching)
     */
    protected Field getField(String parameterName, Class<?> parameterObjectClass) {
        Map<String, Field> fieldMap = getFieldMap(parameterObjectClass);
        Field field = fieldMap.get(parameterName);
        if (field == null) {
            throw new ParameterNotFoundException(parameterName, parameterObjectClass);
        }
        return field;
    }

    /**
     * Get all fields by class (with caching)
     */
    private Map<String, Field> getFieldMap(Class<?> parameterObjectClass) {
        Map<String, Field> fieldMap = fieldsMaps.get(parameterObjectClass);
        if (fieldMap == null) {
            fieldMap = mapAnnotatedFields(parameterObjectClass);
            fieldsMaps.put(parameterObjectClass, fieldMap);
        }
        return fieldMap;
    }

    /**
     * Map all fields annotated with @{@link Parameter}
     */
    private Map<String, Field> mapAnnotatedFields(Class<?> parameterObjectClass) {
        Map<String, Field> fields = new HashMap<>();
        for (Field field : parameterObjectClass.getDeclaredFields()) {
            Parameter annotation = field.getAnnotation(Parameter.class);
            if (annotation != null) {
                field.setAccessible(true);
                for (String name : annotation.names()) {
                    fields.put(name, field);
                }
            }
        }
        return fields;
    }
}
