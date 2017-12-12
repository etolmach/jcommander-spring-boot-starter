package com.etolmach.spring.jcommander.impl;

import com.etolmach.spring.jcommander.JCommandHandler;
import com.etolmach.spring.jcommander.TestControllerWithHandlerMethods1;
import com.etolmach.spring.jcommander.TestControllerWithHandlerMethods2;
import com.etolmach.spring.jcommander.TestControllerWithoutHandlerMethods;
import com.etolmach.spring.jcommander.annotation.CommandController;
import com.etolmach.spring.jcommander.exception.HandlerNotFoundException;
import com.etolmach.spring.jcommander.exception.MultipleHandlersFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author etolmach
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultJCommandHandlerContextTest {

    private static final String CONTROLLER_1 = "controller1";
    private static final String CONTROLLER_2 = "controller2";
    private static final String UNKNOWN_COMMAND = "unknown-command";

    private DefaultJCommandHandlerContext context;

    @Mock
    private ApplicationContext applicationContext;
    @Mock
    private TestControllerWithHandlerMethods1 controller1;
    @Mock
    private TestControllerWithHandlerMethods2 controller2;

    @Before
    public void setUp() {
        context = new DefaultJCommandHandlerContext(applicationContext);
    }

    @Test
    public void init_noControllerBeans() {
        when(applicationContext.getBeansWithAnnotation(CommandController.class)).thenReturn(Collections.emptyMap());

        context.init();

        assertTrue(context.getHandlers().isEmpty());
    }

    @Test
    public void init_noHandlerMethods() {
        Map<String, Object> controllers = new HashMap<>();
        controllers.put(null, mock(TestControllerWithoutHandlerMethods.class));

        when(applicationContext.getBeansWithAnnotation(CommandController.class)).thenReturn(controllers);

        context.init();

        assertTrue(context.getHandlers().isEmpty());
    }

    @Test
    public void init_withHandlerMethods() {
        Map<String, Object> controllers = new HashMap<>();
        controllers.put(CONTROLLER_1, controller1);
        controllers.put(CONTROLLER_2, controller2);
        when(applicationContext.getBeansWithAnnotation(CommandController.class)).thenReturn(controllers);

        context.init();

        assertEquals(4, context.getHandlers().size());

        assertContainsHandler(controller1, controller1.METHOD_1, controller1.COMMAND_1);
        assertContainsHandler(controller1, controller1.METHOD_2, controller1.COMMAND_2);
        assertContainsHandler(controller2, controller2.METHOD_1, controller2.COMMAND_1);
        assertContainsHandler(controller2, controller2.METHOD_2, controller2.COMMAND_2);
    }

    @Test(expected = MultipleHandlersFoundException.class)
    public void init_multipleHandlersForSameCommand() {
        Map<String, Object> controllers = new HashMap<>();
        controllers.put(CONTROLLER_1, controller1);
        controllers.put(CONTROLLER_2, controller1);
        when(applicationContext.getBeansWithAnnotation(CommandController.class)).thenReturn(controllers);

        context.init();
    }

    @Test(expected = HandlerNotFoundException.class)
    public void getHandler_unknownCommand() {
        context.getHandler(UNKNOWN_COMMAND);
    }

    private void assertContainsHandler(Object controller, String method, String command) {
        JCommandHandler handler = context.getHandler(command);
        assertNotNull(handler);
        assertEquals(controller, handler.getController());
        assertEquals(method, handler.getMethod().getName());
        assertEquals(command, handler.getCommand());
    }
}