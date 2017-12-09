package com.etolmach.spring.jcommander;

import java.lang.reflect.Method;

/**
 * @author etolmach
 */
public interface JCommandHandler {

    /**
     * Get controller bean
     *
     * @return Controller bean
     */
    Object getController();

    /**
     * Get handler method
     *
     * @return Handler method
     */
    Method getMethod();

    /**
     * Get command name
     *
     * @return Command name
     */
    String getCommand();

}
