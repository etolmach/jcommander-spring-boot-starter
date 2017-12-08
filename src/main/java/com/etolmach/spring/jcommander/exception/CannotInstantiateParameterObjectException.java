package com.etolmach.spring.jcommander.exception;

/**
 * @author etolmach
 */
public class CannotInstantiateParameterObjectException extends JCommanderException {

    public CannotInstantiateParameterObjectException(String command, Class<?> clazz, Exception cause) {
        super(cause, "Cannot instantiate parameter bean from class ''{0}'' for command ''{1}''.", clazz.getName(), command);
    }

}
