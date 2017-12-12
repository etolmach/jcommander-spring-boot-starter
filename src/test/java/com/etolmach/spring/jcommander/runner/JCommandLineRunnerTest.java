package com.etolmach.spring.jcommander.runner;

import com.etolmach.spring.jcommander.JCommandLineExecutor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

/**
 * @author etolmach
 */
@RunWith(MockitoJUnitRunner.class)
public class JCommandLineRunnerTest {

    private static final String[] ARGS = {"some", "-foo:bar", "-args"};

    private JCommandLineRunner runner;

    @Mock
    private JCommandLineExecutor executor;

    @Before
    public void setUp() {
        runner = new JCommandLineRunner(executor);
    }

    @Test
    public void run() {
        runner.run(ARGS);

        verify(executor).execute(ARGS);
    }
}