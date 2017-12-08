package com.etolmach.spring.jcommander;

/**
 * @author etolmach
 */
public interface JCommandParameterExtractor {

    /**
     * Extract parameter from parameter bean by name
     *
     * @param parameterName Parameter name
     * @param parameterBean Parameter bean
     * @return Parameter value
     */
    Object extract(String parameterName, Object parameterBean);

}
