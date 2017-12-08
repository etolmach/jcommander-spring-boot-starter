package com.etolmach.spring.jcommander.exception;

import java.text.MessageFormat;

/**
 * @author etolmach
 */
public class JCommanderException extends RuntimeException {

    public JCommanderException(String pattern, Object... arguments) {
        super(MessageFormat.format(pattern, arguments));
    }

    public JCommanderException(Throwable cause, String pattern, Object... arguments) {
        super(MessageFormat.format(pattern, arguments), cause);
    }
}
