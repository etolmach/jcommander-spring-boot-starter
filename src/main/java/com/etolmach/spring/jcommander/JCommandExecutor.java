package com.etolmach.spring.jcommander;

/**
 * @author etolmach
 */
public interface JCommandExecutor {

    /**
     * Executes provided {@link JCommandWrapper}
     *
     * @param command Command wrapper to execute
     */
    void execute(JCommandWrapper command);

}
