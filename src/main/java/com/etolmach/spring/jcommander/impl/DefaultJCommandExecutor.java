package com.etolmach.spring.jcommander.impl;

import com.etolmach.spring.jcommander.*;
import com.etolmach.spring.jcommander.annotation.CommandParameter;
import com.etolmach.spring.jcommander.exception.CannotInvokeCommandHandlerException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author etolmach
 */

@Component
@ConditionalOnSingleCandidate(JCommandExecutor.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DefaultJCommandExecutor implements JCommandExecutor {

    @NonNull
    private final JCommandHandlerContext commandHandlerContext;

    @NonNull
    private final JCommandParameterExtractor parameterExtractor;

    @NonNull
    private final ApplicationContext appCtx;

    @Override
    public void execute(JCommandWrapper command) {
        JCommandHandler handler = commandHandlerContext.getHandler(command.getName());
        Object controller = handler.getController();
        Method method = handler.getMethod();
        Object[] arguments = getArguments(method.getParameters(), command);

        try {
            method.invoke(controller, arguments);
        } catch (ReflectiveOperationException e) {
            throw new CannotInvokeCommandHandlerException(handler.getMethod(), handler.getCommand(), e);
        }
    }

    /**
     * Retrieve all arguments for command
     */
    private Object[] getArguments(Parameter[] parameters, JCommandWrapper command) {
        Object parameterBean = command.getParameterBean();
        Class<?> parameterBeanClass = parameterBean.getClass();

        Object[] arguments = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            arguments[i] = getArgumentForParameter(parameters[i], parameterBean, parameterBeanClass);
        }
        return arguments;
    }

    /**
     * Retrieve the parameter value from the parameter bean.<br>
     * If the parameter is annotate
     */
    private Object getArgumentForParameter(Parameter parameter, Object parameterBean, Class<?> parameterBeanClass) {
        CommandParameter annotation = parameter.getAnnotation(CommandParameter.class);
        return annotation == null ? getBeanArgument(parameter, parameterBean) : getCommandParameterArgument(parameterBean, annotation);
    }

    /**
     * Get autowired bean argument
     */
    private Object getBeanArgument(Parameter parameter, Object parameterBean) {
        return parameter.getType().equals(parameterBean.getClass()) ? parameterBean : getGlobalBean(parameter);
    }

    /**
     * Get value of parameter annotated with @{@link CommandParameter}
     */
    private Object getCommandParameterArgument(Object parameterBean, CommandParameter annotation) {
        String parameterName = annotation.name();
        return parameterExtractor.extract(parameterName, parameterBean);
    }

    /**
     * Autowire bean from global context
     */
    private Object getGlobalBean(Parameter parameter) {
        Qualifier qualifier = parameter.getAnnotation(Qualifier.class);
        Class<?> parameterClass = parameter.getType();
        return qualifier == null ? appCtx.getBean(parameterClass) : appCtx.getBeansOfType(parameterClass).get(qualifier.value());
    }

}
