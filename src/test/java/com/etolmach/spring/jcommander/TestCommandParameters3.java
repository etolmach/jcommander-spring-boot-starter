package com.etolmach.spring.jcommander;

import com.beust.jcommander.Parameters;

/**
 * @author etolmach
 */
@Parameters(commandNames = TestCommandParameters3.COMMAND_NAME)
public class TestCommandParameters3 {

    public static final String COMMAND_NAME = "test-command-3";

    public TestCommandParameters3() throws ReflectiveOperationException {
        throw new ReflectiveOperationException("");
    }
}