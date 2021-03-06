package com.etolmach.spring.jcommander.impl;

import com.etolmach.spring.jcommander.TestCommandParameters1;
import com.etolmach.spring.jcommander.TestCommandParameters2;
import com.etolmach.spring.jcommander.TestCommandParameters3;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @author etolmach
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultJCommandParameterBeanFactoryTest {

    private static final String BEAN_1 = "bean-1";
    private static final String BEAN_2 = "bean-2";
    private static final String BEAN_3 = "bean-3";
    private static final String BEAN_4 = "bean-4";

    private DefaultJCommandParameterBeanFactory factory;

    @Mock
    private ApplicationContext applicationContext;
    @Mock
    private Object nonParameterBean1;
    @Mock
    private Object nonParameterBean2;
    @Mock
    private TestCommandParameters1 parameterBean1;
    @Mock
    private TestCommandParameters2 parameterBean2;
    @Mock
    private TestCommandParameters3 brokenParameterBean;


    @Before
    public void setUp() {
        factory = new DefaultJCommandParameterBeanFactory();
        factory.setApplicationContext(applicationContext);
    }

    @Test
    public void init_noBeans() {
        when(applicationContext.getBeanDefinitionNames()).thenReturn(new String[0]);

        factory.init();

        assertTrue(factory.createForAll().isEmpty());
    }

    @Test
    public void init_noParameterBeans() {
        when(applicationContext.getBeanDefinitionNames()).thenReturn(new String[]{BEAN_1, BEAN_2});

        when(applicationContext.getBean(BEAN_1)).thenReturn(nonParameterBean1);
        when(applicationContext.getBean(BEAN_2)).thenReturn(nonParameterBean2);

        factory.init();

        assertTrue(factory.createForAll().isEmpty());
    }

    @Test
    public void init() {
        when(applicationContext.getBeanDefinitionNames()).thenReturn(new String[]{BEAN_1, BEAN_2, BEAN_3, BEAN_4});

        when(applicationContext.getBean(BEAN_1)).thenReturn(nonParameterBean1);
        when(applicationContext.getBean(BEAN_2)).thenReturn(nonParameterBean2);
        when(applicationContext.getBean(BEAN_3)).thenReturn(parameterBean1);
        when(applicationContext.getBean(BEAN_4)).thenReturn(parameterBean2);

        factory.init();

        Map<String, Object> allCommandParameterBeans = factory.createForAll();

        assertEquals(5, allCommandParameterBeans.size());

        Object parameterBeanCommand1Name1 = allCommandParameterBeans.get(TestCommandParameters1.COMMAND_NAME_1);
        Object parameterBeanCommand1Name2 = allCommandParameterBeans.get(TestCommandParameters1.COMMAND_NAME_2);

        assertTrue(parameterBeanCommand1Name1 instanceof TestCommandParameters1);
        assertTrue(parameterBeanCommand1Name2 instanceof TestCommandParameters1);
        assertEquals(parameterBeanCommand1Name1, parameterBeanCommand1Name2);

        Object parameterBeanCommand2Name1 = allCommandParameterBeans.get(TestCommandParameters2.COMMAND_NAME_1);
        Object parameterBeanCommand2Name2 = allCommandParameterBeans.get(TestCommandParameters2.COMMAND_NAME_2);
        Object parameterBeanCommand2Name3 = allCommandParameterBeans.get(TestCommandParameters2.COMMAND_NAME_3);

        assertTrue(parameterBeanCommand2Name1 instanceof TestCommandParameters2);
        assertTrue(parameterBeanCommand2Name2 instanceof TestCommandParameters2);
        assertTrue(parameterBeanCommand2Name3 instanceof TestCommandParameters2);
        assertEquals(parameterBeanCommand2Name1, parameterBeanCommand2Name2);
        assertEquals(parameterBeanCommand2Name1, parameterBeanCommand2Name3);
    }
}
