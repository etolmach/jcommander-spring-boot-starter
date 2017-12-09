package com.etolmach.spring.jcommander;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import lombok.Getter;

/**
 * @author etolmach
 */
@Getter
@Parameters(commandNames = {TestCommandParameters1.COMMAND_NAME_1, TestCommandParameters1.COMMAND_NAME_2})
public class TestCommandParameters1 {

    public static final String COMMAND_NAME_1 = "test-command-1-name-1";
    public static final String COMMAND_NAME_2 = "test-command-1-name-2";

    public static final String PARAM_1 = "-param1";
    public static final String PARAM_2_NAME_1 = "-param2name1";
    public static final String PARAM_2_NAME_2 = "-param2name2";

    public static final String PRIVATE_FIELD = "privateField";

    @Parameter(names = PARAM_1)
    private String param1 = "dummy param-1 value";

    @Parameter(names = {PARAM_2_NAME_1, PARAM_2_NAME_2})
    private Integer param2 = 12345;

    private Boolean privateField = true;
}