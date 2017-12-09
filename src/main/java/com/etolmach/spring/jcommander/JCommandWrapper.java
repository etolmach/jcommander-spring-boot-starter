package com.etolmach.spring.jcommander;

/**
 * @author etolmach
 */
public interface JCommandWrapper {

    /**
     * Get name
     *
     * @return Command name
     */
    String getName();

    /**
     * Get parameter bean
     *
     * @return Parameter bean
     */
    Object getParameterBean();

}
