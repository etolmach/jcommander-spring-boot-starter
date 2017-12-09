package com.etolmach.spring.jcommander.exception;

/**
 * @author etolmach
 */
public class CannotInstantiateParameterBeanException extends JCommanderException {

    public CannotInstantiateParameterBeanException(Class<?> clazz, Exception cause) {
        super(cause, "Cannot instantiate parameter bean from class ''{0}''.", clazz.getName());
    }

}
