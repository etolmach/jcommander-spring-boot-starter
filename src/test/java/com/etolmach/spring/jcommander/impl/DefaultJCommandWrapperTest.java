package com.etolmach.spring.jcommander.impl;

import com.etolmach.spring.jcommander.JCommandWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

/**
 * @author etolmach
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultJCommandWrapperTest {

    private static final String NAME = "mock name";

    @Mock
    private Object parameterBean;

    @Test
    public void getName() {
        JCommandWrapper wrapper = new DefaultJCommandWrapper(NAME, parameterBean);

        assertEquals(NAME, wrapper.getName());
    }

    @Test
    public void getParameterBean() {
        JCommandWrapper wrapper = new DefaultJCommandWrapper(NAME, parameterBean);

        assertEquals(parameterBean, wrapper.getParameterBean());
    }
}