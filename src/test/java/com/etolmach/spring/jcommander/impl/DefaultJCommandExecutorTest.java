//package com.etolmach.spring.jcommander.impl;
//
//import com.etolmach.spring.jcommander.*;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.context.ApplicationContext;
//
//import java.lang.reflect.Method;
//
//import static org.mockito.Mockito.when;
//
///**
// * @author etolmach
// */
//@RunWith(MockitoJUnitRunner.class)
//public class DefaultJCommandExecutorTest {
//
//    private static final String COMMAND_NAME = "mock name";
//    private static final Method METHOD = Object.class.getMethods()[0];
//
//    private JCommandExecutor executor;
//
//    @Mock
//    private JCommandHandlerContext commandHandlerContext;
//    @Mock
//    private JCommandParameterExtractor parameterExtractor;
//    @Mock
//    private ApplicationContext applicationContext;
//    @Mock
//    private JCommandWrapper command;
//    @Mock
//    private JCommandHandler handler;
////    @Mock
////    private Object controller;
//
//    private TestCommandParameters1 parameterBean;
//
//    private TestControllerWithHandlerMethods1 controller;
//
//    @Before
//    public void setUp() {
//        executor = new DefaultJCommandExecutor(commandHandlerContext, parameterExtractor, applicationContext);
//    }
//
//    @Test
//    public void testExecute() {
//        Method method = TestControllerWithHandlerMethods1.class.
//
//                when(command.getName()).thenReturn(controller.COMMAND_1);
//        when(commandHandlerContext.getHandler(controller.COMMAND_1)).thenReturn(handler);
//        when(handler.getController()).thenReturn(controller);
//        when(handler.getMethod()).thenReturn(controller.);
//        when(command.getParameterBean()).thenReturn(parameterBean);
//
//        executor.execute(command);
//    }
//}