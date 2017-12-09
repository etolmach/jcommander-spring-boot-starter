package com.etolmach.spring.jcommander;

import com.beust.jcommander.Parameters;

/**
 * @author etolmach
 */
@Parameters(commandNames = {
        TestCommandParameters2.COMMAND_NAME_1,
        TestCommandParameters2.COMMAND_NAME_2,
        TestCommandParameters2.COMMAND_NAME_3
})
public class TestCommandParameters2 {

    public static final String COMMAND_NAME_1 = "test-command-2-name-1";
    public static final String COMMAND_NAME_2 = "test-command-2-name-2";
    public static final String COMMAND_NAME_3 = "test-command-2-name-3";

}