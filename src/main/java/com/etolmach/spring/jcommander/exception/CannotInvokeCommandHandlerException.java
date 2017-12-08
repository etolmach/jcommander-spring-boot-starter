package com.etolmach.spring.jcommander.exception;

import java.lang.reflect.Method;

/**
 * @author etolmach
 */
public class CannotInvokeCommandHandlerException extends JCommanderException {

    public CannotInvokeCommandHandlerException(Method method, String command, Exception cause) {
        super(cause, "Cannot invoke command handler method ''{0}'' for command ''{1}''.", method.getName(), command);
    }

}
