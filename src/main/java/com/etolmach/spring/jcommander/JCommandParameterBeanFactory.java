package com.etolmach.spring.jcommander;

import java.util.Map;

/**
 * @author etolmach
 */
public interface JCommandParameterBeanFactory {

    /**
     * Create parameter beans for all commands
     *
     * @return Map of parameter beans
     */
    Map<String, Object> createForAll();

}