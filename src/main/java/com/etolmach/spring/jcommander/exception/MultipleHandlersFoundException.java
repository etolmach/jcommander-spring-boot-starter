package com.etolmach.spring.jcommander.exception;

import com.etolmach.spring.jcommander.JCommandHandler;

/**
 * @author etolmach
 */
public class MultipleHandlersFoundException extends JCommanderException {

    public MultipleHandlersFoundException(String command, JCommandHandler handler1, JCommandHandler handler2) {
        super("At least two handlers (''{0}'' , ''{1}'') found for command ''{2}''.", handler1, handler2, command);
    }

}
