package com.etolmach.spring.jcommander.exception;

/**
 * @author etolmach
 */
public class HandlerNotFoundException extends JCommanderException {

    public HandlerNotFoundException(String command) {
        super("Cannot find handler for command ''{0}''.", command);
    }

}
