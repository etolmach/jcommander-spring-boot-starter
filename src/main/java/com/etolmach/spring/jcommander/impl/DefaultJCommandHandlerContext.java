package com.etolmach.spring.jcommander.impl;

import com.etolmach.spring.jcommander.JCommandHandler;
import com.etolmach.spring.jcommander.JCommandHandlerContext;
import com.etolmach.spring.jcommander.annotation.CommandController;
import com.etolmach.spring.jcommander.annotation.CommandHandler;
import com.etolmach.spring.jcommander.exception.HandlerNotFoundException;
import com.etolmach.spring.jcommander.exception.MultipleHandlersFoundException;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author etolmach
 */

@Component
@ConditionalOnSingleCandidate(JCommandHandlerContext.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DefaultJCommandHandlerContext implements JCommandHandlerContext {

    @Getter
    private Map<String, JCommandHandler> handlers = new HashMap<>();

    @NonNull
    private final ApplicationContext context;

    @PostConstruct
    public void init() {
        context.getBeansWithAnnotation(CommandController.class)
                .values().stream()
                .filter(this::isControllerBean)
                .map(this::wrapCommands)
                .flatMap(List::stream)
                .forEach(this::registerHandler);
    }

    @Override
    public JCommandHandler getHandler(String command) {
        JCommandHandler handler = handlers.get(command);
        if (handler == null) {
            throw new HandlerNotFoundException(command);
        }
        return handler;
    }

    /**
     * Register command handler in global hash map
     */
    private void registerHandler(JCommandHandler handler) {
        String command = handler.getCommand();
        if (handlers.containsKey(command)) {
            throw new MultipleHandlersFoundException(command, handlers.get(command), handler);
        } else {
            handlers.put(command, handler);
        }
    }

    /**
     * Check if bean is annotated with @{@link CommandController}
     */
    private boolean isControllerBean(Object bean) {
        return bean.getClass().isAnnotationPresent(CommandController.class);
    }

    /**
     * Wrap all the handler methods annotated with @{@link CommandHandler} from controller bean
     */
    private List<JCommandHandler> wrapCommands(Object controller) {
        List<JCommandHandler> handlers = new ArrayList<>();
        for (Method method : controller.getClass().getMethods()) {
            CommandHandler annotation = method.getAnnotation(CommandHandler.class);
            if (annotation != null) {
                handlers.add(new DefaultJCommandHandler(controller, method, annotation.command()));
            }
        }
        return handlers;
    }
}