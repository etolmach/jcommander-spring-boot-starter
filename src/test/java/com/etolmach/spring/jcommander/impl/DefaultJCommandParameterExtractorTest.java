package com.etolmach.spring.jcommander.impl;

import com.beust.jcommander.ParameterException;
import com.etolmach.spring.jcommander.TestCommandParameters1;
import com.etolmach.spring.jcommander.exception.ParameterNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

/**
 * @author etolmach
 */
public class DefaultJCommandParameterExtractorTest {

    private static final String INVALID_PAREMETER = "-invalid-parameter";

    private DefaultJCommandParameterExtractor extractor;
    private TestCommandParameters1 parameterBean;

    @Before
    public void setUp() {
        extractor = spy(new DefaultJCommandParameterExtractor());
        parameterBean = new TestCommandParameters1();
    }

    @Test
    public void extract() {
        assertEquals(parameterBean.getParam1(), extractor.extract(TestCommandParameters1.PARAM_1, parameterBean));
        assertEquals(parameterBean.getParam2(), extractor.extract(TestCommandParameters1.PARAM_2_NAME_1, parameterBean));
        assertEquals(parameterBean.getParam2(), extractor.extract(TestCommandParameters1.PARAM_2_NAME_2, parameterBean));
    }

    @Test(expected = ParameterNotFoundException.class)
    public void extract_invalidParameter() {
        extractor.extract(INVALID_PAREMETER, parameterBean);
    }

    @Test(expected = ParameterException.class)
    public void extract_illegalAccess() throws NoSuchFieldException {
        Field privateField = parameterBean.getClass().getDeclaredField(TestCommandParameters1.PRIVATE_FIELD);
        doReturn(privateField).when(extractor).getField(INVALID_PAREMETER, TestCommandParameters1.class);

        extractor.extract(INVALID_PAREMETER, parameterBean);
    }
}