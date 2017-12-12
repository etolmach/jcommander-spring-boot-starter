package com.etolmach.spring.jcommander.exception;

/**
 * @author etolmach
 */
public class CommandParametersBeanNotFoundException extends JCommanderException {

    public CommandParametersBeanNotFoundException(String command) {
        super("Cannot find parameters bean for command ''{0}''.", command);
    }

}
