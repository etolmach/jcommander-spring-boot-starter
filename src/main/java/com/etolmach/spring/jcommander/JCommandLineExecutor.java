package com.etolmach.spring.jcommander;

/**
 * @author etolmach
 */
public interface JCommandLineExecutor {

    /**
     * Parse the arguments and trigger the corresponding command handler
     *
     * @param args Array of command-line arguments
     */
    void execute(String[] args);

}
