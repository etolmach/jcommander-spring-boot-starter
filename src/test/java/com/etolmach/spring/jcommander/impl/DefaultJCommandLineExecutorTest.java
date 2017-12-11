package com.etolmach.spring.jcommander.impl;

import com.etolmach.spring.jcommander.JCommandExecutor;
import com.etolmach.spring.jcommander.JCommandParser;
import com.etolmach.spring.jcommander.JCommandWrapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author etolmach
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultJCommandLineExecutorTest {

    private static final String[] ARGS = {"some", "-arg:values"};


    private DefaultJCommandLineExecutor commandLineExecutor;

    @Mock
    private JCommandParser parser;
    @Mock
    private JCommandExecutor commandExecutor;
    @Mock
    private JCommandWrapper commandWrapper;

    @Before
    public void setUp() throws Exception {
        commandLineExecutor = new DefaultJCommandLineExecutor(parser, commandExecutor);
    }

    @Test
    public void execute() {
        when(parser.parse(ARGS)).thenReturn(commandWrapper);

        commandLineExecutor.execute(ARGS);

        verify(parser).parse(ARGS);
        verify(commandExecutor).execute(commandWrapper);
    }
}