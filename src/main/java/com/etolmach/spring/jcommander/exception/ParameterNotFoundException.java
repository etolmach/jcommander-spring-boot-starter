package com.etolmach.spring.jcommander.exception;

/**
 * @author etolmach
 */
public class ParameterNotFoundException extends JCommanderException {

    public ParameterNotFoundException(String parameter, Class<?> clazz) {
        super("No such parameter ''{0}'' in parameter class ''{1}''.", parameter, clazz.getName());
    }

}
