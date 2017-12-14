package com.etolmach.spring.jcommander.impl;

import com.beust.jcommander.Parameters;
import com.etolmach.spring.jcommander.JCommandParameterBeanFactory;
import com.etolmach.spring.jcommander.exception.CannotInstantiateParameterObjectException;
import com.etolmach.spring.jcommander.exception.CommandParametersBeanNotFoundException;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author etolmach
 */

@Component
@ConditionalOnSingleCandidate(JCommandParameterBeanFactory.class)
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DefaultJCommandParameterBeanFactory implements JCommandParameterBeanFactory {

    private final Map<String, Class<?>> commandParameterBeanClasses = new HashMap<>();

    @Setter(onMethod = @__(@Autowired))
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
            Object bean = applicationContext.getBean(beanDefinitionName);
            Class<?> beanClass = bean.getClass();
            Parameters annotation = beanClass.getAnnotation(Parameters.class);
            if (annotation != null) {
                for (String commandName : annotation.commandNames()) {
                    commandParameterBeanClasses.put(commandName, beanClass);
                }
            }
        }
    }

    @Override
    public Object createFor(String command) {
        Class<?> clazz = commandParameterBeanClasses.get(command);
        if (clazz == null) {
            throw new CommandParametersBeanNotFoundException(command);
        }
        return newInstance(command, clazz);
    }

    @Override
    public Map<String, Object> createForAll() {
        return commandParameterBeanClasses
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, this::newInstance));
    }

    /**
     * Instantiate new parameter bean by map entry
     */
    private Object newInstance(Map.Entry<String, Class<?>> entry) {
        return newInstance(entry.getKey(), entry.getValue());
    }

    /**
     * Instantiate new parameter bean
     */
    private Object newInstance(String name, Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new CannotInstantiateParameterObjectException(name, clazz, e);
        }
    }
}