package com.etolmach.spring.jcommander.impl;

import com.etolmach.spring.jcommander.JCommandHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

/**
 * @author etolmach
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultJCommandHandlerTest {

    private static final String COMMAND_NAME = "command name";
    private static final Method METHOD = Object.class.getMethods()[0];

    @Mock
    private Object controller;

    @Test
    public void getController() {
        JCommandHandler handler = new DefaultJCommandHandler(controller, METHOD, COMMAND_NAME);

        assertEquals(controller, handler.getController());
    }

    @Test
    public void getMethod() {
        JCommandHandler handler = new DefaultJCommandHandler(controller, METHOD, COMMAND_NAME);

        assertEquals(METHOD, handler.getMethod());
    }

    @Test
    public void getCommand() {
        JCommandHandler handler = new DefaultJCommandHandler(controller, METHOD, COMMAND_NAME);

        assertEquals(COMMAND_NAME, handler.getCommand());
    }
}