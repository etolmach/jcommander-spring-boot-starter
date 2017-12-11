package com.etolmach.spring.jcommander;

import com.etolmach.spring.jcommander.annotation.CommandController;
import com.etolmach.spring.jcommander.annotation.CommandHandler;

/**
 * @author etolmach
 */
@CommandController
public class TestControllerWithHandlerMethods1 {

    public static final String COMMAND_1 = "controller1command1";
    public static final String COMMAND_2 = "controller1command2";

    public static final String METHOD_1 = "controller1method1";
    public static final String METHOD_2 = "controller1method2";

    @CommandHandler(command = COMMAND_1)
    public void controller1method1(String param1, Integer param2) {

    }

    @CommandHandler(command = COMMAND_2)
    public void controller1method2(String param1, Integer param2) {

    }

}