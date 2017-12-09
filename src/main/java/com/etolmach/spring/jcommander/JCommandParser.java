package com.etolmach.spring.jcommander;

/**
 * @author etolmach
 */
public interface JCommandParser {

    /**
     * Parse the array of command-line arguments
     *
     * @param args Command-line arguments
     * @return Command wrapper
     */
    JCommandWrapper parse(String[] args);

}
