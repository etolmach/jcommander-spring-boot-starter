package com.etolmach.spring.jcommander;

import com.etolmach.spring.jcommander.annotation.CommandController;
import com.etolmach.spring.jcommander.annotation.CommandHandler;
import com.etolmach.spring.jcommander.annotation.CommandParameter;
import org.springframework.beans.factory.annotation.Qualifier;

import java.math.BigDecimal;

/**
 * @author etolmach
 */
@CommandController
public class TestControllerWithHandlerMethods1 {

    public static final String COMMAND_1 = "controller1command1";
    public static final String COMMAND_2 = "controller1command2";

    public static final String METHOD_1 = "controller1method1";
    public static final String METHOD_2 = "controller1method2";
    public static final String PRIVATE_METHOD = "privateMethod";

    public static final String PARAMETER_4_QUALIFIER = "param4Qualifier";

    @CommandHandler(command = COMMAND_1)
    public void controller1method1(
            @CommandParameter(name = TestCommandParameters1.PARAM_1) String param1,
            @CommandParameter(name = TestCommandParameters1.PARAM_2_NAME_1) Integer param2_1,
            @CommandParameter(name = TestCommandParameters1.PARAM_2_NAME_2) Integer param2_2,
            Boolean param3,
            @Qualifier(PARAMETER_4_QUALIFIER) BigDecimal param4) {

    }

    @CommandHandler(command = COMMAND_2)
    public void controller1method2(String param1, Integer param2) {

    }

    private void privateMethod() {

    }

}