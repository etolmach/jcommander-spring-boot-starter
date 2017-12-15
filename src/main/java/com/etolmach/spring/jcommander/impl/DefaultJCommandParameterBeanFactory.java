package com.etolmach.spring.jcommander.impl;

import com.beust.jcommander.Parameters;
import com.etolmach.spring.jcommander.JCommandParameterBeanFactory;
import com.etolmach.spring.jcommander.exception.CannotInstantiateParameterBeanException;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author etolmach
 */

@Component
@ConditionalOnSingleCandidate(JCommandParameterBeanFactory.class)
public class DefaultJCommandParameterBeanFactory implements JCommandParameterBeanFactory {

    private final Map<Class<?>, String[]> parameterBeanDefinitions = new HashMap<>();

    @Setter(onMethod = @__(@Autowired))
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
            Object bean = applicationContext.getBean(beanDefinitionName);
            Class<?> beanClass = bean.getClass();
            Parameters annotation = beanClass.getAnnotation(Parameters.class);
            if (annotation != null) {
                parameterBeanDefinitions.put(beanClass, annotation.commandNames());
            }
        }
    }

    @Override
    public Map<String, Object> createForAll() {
        Map<String, Object> parameterBeans = new HashMap<>();

        parameterBeanDefinitions.forEach((clazz, commandNames) -> {
            try {
                Object parameterBean = clazz.newInstance();
                for (String commandName : commandNames) {
                    parameterBeans.put(commandName, parameterBean);
                }
            } catch (ReflectiveOperationException e) {
                throw new CannotInstantiateParameterBeanException(clazz, e);
            }
        });

        return parameterBeans;
    }
    
}