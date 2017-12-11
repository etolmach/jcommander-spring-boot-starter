package com.etolmach.spring.jcommander.impl;

import com.etolmach.spring.jcommander.TestCommandParameters1;
import com.etolmach.spring.jcommander.exception.ParameterNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * @author etolmach
 */
public class DefaultJCommandParameterExtractorTest {

    private static final String INVALID_PAREMETER = "-invalid-parameter";

    private DefaultJCommandParameterExtractor extractor;

    @Before
    public void setUp() throws Exception {
        extractor = spy(new DefaultJCommandParameterExtractor());
    }

    @Test
    public void extract() {
        TestCommandParameters1 parameterBean = new TestCommandParameters1();

        assertEquals(parameterBean.getParam1(), extractor.extract(parameterBean.PARAM_1, parameterBean));
        assertEquals(parameterBean.getParam2(), extractor.extract(parameterBean.PARAM_2_NAME_1, parameterBean));
        assertEquals(parameterBean.getParam2(), extractor.extract(parameterBean.PARAM_2_NAME_2, parameterBean));
    }

    @Test(expected = ParameterNotFoundException.class)
    public void extract_invalidParameter() {
        TestCommandParameters1 parameterBean = new TestCommandParameters1();

        extractor.extract(INVALID_PAREMETER, parameterBean);
    }

    @Test(expected = ParameterNotFoundException.class)
    public void extract_illegalAccess() throws NoSuchFieldException {
        Field privateField = TestCommandParameters1.class.getDeclaredField(TestCommandParameters1.PRIVATE_FIELD);
        when(extractor.getField(INVALID_PAREMETER, TestCommandParameters1.class)).thenReturn(privateField);

        TestCommandParameters1 parameterBean = new TestCommandParameters1();

        extractor.extract(INVALID_PAREMETER, parameterBean);
    }
}