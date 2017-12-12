package com.etolmach.spring.jcommander.impl;

import com.etolmach.spring.jcommander.*;
import com.etolmach.spring.jcommander.exception.CannotInvokeCommandHandlerException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author etolmach
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultJCommandExecutorTest {

    private static final String COMMAND_NAME = "mock name";
    private static final Method METHOD = Object.class.getMethods()[0];

    private static final String PARAM_1 = "dummy param value";
    private static final Integer PARAM_2 = 12345678;
    private static final Boolean PARAM_3 = true;
    private static final BigDecimal PARAM_4 = BigDecimal.valueOf(12345.678);

    private DefaultJCommandExecutor executor;
    private TestCommandParameters1 parameterBean;

    @Mock
    private JCommandHandlerContext commandHandlerContext;
    @Mock
    private JCommandParameterExtractor parameterExtractor;
    @Mock
    private ApplicationContext applicationContext;
    @Mock
    private JCommandWrapper command;
    @Mock
    private JCommandHandler handler;
    @Mock
    private TestControllerWithHandlerMethods1 controller;

    @Before
    public void setUp() {
        executor = new DefaultJCommandExecutor(commandHandlerContext, parameterExtractor, applicationContext);
        parameterBean = new TestCommandParameters1();
    }

    @Test
    public void testExecute() throws NoSuchMethodException {
        Method method = TestControllerWithHandlerMethods1.class.getDeclaredMethod(TestControllerWithHandlerMethods1.METHOD_1,
                String.class, Integer.class, Integer.class, Boolean.class, BigDecimal.class);

        when(command.getName()).thenReturn(TestControllerWithHandlerMethods1.COMMAND_1);
        when(command.getParameterBean()).thenReturn(parameterBean);

        when(handler.getController()).thenReturn(controller);
        when(handler.getMethod()).thenReturn(method);

        when(parameterExtractor.extract(TestCommandParameters1.PARAM_1, parameterBean)).thenReturn(PARAM_1);
        when(parameterExtractor.extract(TestCommandParameters1.PARAM_2_NAME_1, parameterBean)).thenReturn(PARAM_2);
        when(parameterExtractor.extract(TestCommandParameters1.PARAM_2_NAME_2, parameterBean)).thenReturn(PARAM_2);
        when(applicationContext.getBean(Boolean.class)).thenReturn(PARAM_3);

        Map<String, BigDecimal> bigDecimalBeans = new HashMap<>();
        bigDecimalBeans.put(TestControllerWithHandlerMethods1.PARAMETER_4_QUALIFIER, PARAM_4);
        when(applicationContext.getBeansOfType(BigDecimal.class)).thenReturn(bigDecimalBeans);

        when(commandHandlerContext.getHandler(TestControllerWithHandlerMethods1.COMMAND_1)).thenReturn(handler);

        executor.execute(command);

        // parameter names of two PARAM_2 arguments are different are different
        verify(controller).controller1method1(PARAM_1, PARAM_2, PARAM_2, PARAM_3, PARAM_4);
    }

    @Test(expected = CannotInvokeCommandHandlerException.class)
    public void testExecute_cannotInvoke() throws NoSuchMethodException {
        Method privateMethod = TestControllerWithHandlerMethods1.class.getDeclaredMethod(TestControllerWithHandlerMethods1.PRIVATE_METHOD);
        when(command.getName()).thenReturn(TestControllerWithHandlerMethods1.COMMAND_1);
        when(command.getParameterBean()).thenReturn(parameterBean);

        when(handler.getController()).thenReturn(controller);
        when(handler.getMethod()).thenReturn(privateMethod);
        when(handler.getCommand()).thenReturn(TestControllerWithHandlerMethods1.COMMAND_1);

        when(commandHandlerContext.getHandler(TestControllerWithHandlerMethods1.COMMAND_1)).thenReturn(handler);

        executor.execute(command);
    }
}