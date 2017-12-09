package com.etolmach.spring.jcommander.impl;

import com.beust.jcommander.JCommander;
import com.etolmach.spring.jcommander.JCommandParameterBeanFactory;
import com.etolmach.spring.jcommander.JCommandWrapper;
import com.etolmach.spring.jcommander.TestCommandParameters1;
import com.etolmach.spring.jcommander.TestCommandParameters2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author etolmach
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultJCommandParserTest {

    private static final String[] ARGS = {TestCommandParameters1.COMMAND_NAME_1};

    private DefaultJCommandParser parser;

    @Mock
    private TestCommandParameters1 commandParametersBean1;
    @Mock
    private TestCommandParameters2 commandParametersBean2;
    @Mock
    private JCommandParameterBeanFactory parameterBeanFactory;
    @Mock
    private JCommander.Builder builder;
    @Mock
    private JCommander jcommander;

    @Before
    public void setUp() {
        parser = spy(new DefaultJCommandParser());
        parser.setParameterBeanFactory(parameterBeanFactory);
        doReturn(builder).when(parser).getBuilder();
    }

    @Test
    public void parse() {
        Map<String, Object> parameterBeans = new HashMap<>();
        parameterBeans.put(TestCommandParameters1.COMMAND_NAME_1, commandParametersBean1);
        parameterBeans.put(TestCommandParameters2.COMMAND_NAME_1, commandParametersBean2);

        when(parameterBeanFactory.createForAll()).thenReturn(parameterBeans);

        when(builder.build()).thenReturn(jcommander);
        when(jcommander.getParsedCommand()).thenReturn(TestCommandParameters1.COMMAND_NAME_1);

        JCommandWrapper commandWrapper = parser.parse(ARGS);

        verify(builder).addCommand(commandParametersBean1);
        verify(builder).addCommand(commandParametersBean2);

        assertEquals(TestCommandParameters1.COMMAND_NAME_1, commandWrapper.getName());
        assertEquals(commandParametersBean1, commandWrapper.getParameterBean());
    }
}