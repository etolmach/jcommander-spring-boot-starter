package com.etolmach.spring.jcommander.impl;

import com.etolmach.spring.jcommander.TestCommandParameters1;
import com.etolmach.spring.jcommander.TestCommandParameters2;
import com.etolmach.spring.jcommander.TestCommandParameters3;
import com.etolmach.spring.jcommander.exception.CannotInstantiateParameterObjectException;
import com.etolmach.spring.jcommander.exception.CommandParametersBeanNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Constructor;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @author etolmach
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultJCommandParameterBeanFactoryTest {

    private static final String UNDEFINED_COMMAND = "undefined-command";
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
    public void setUp() throws Exception {
        factory = new DefaultJCommandParameterBeanFactory(applicationContext);
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

        // TODO:
        assertEquals(4, allCommandParameterBeans.size());
//        assertEquals(parameterBean1, allCommandParameterBeans.get(parameterBean1.COMMAND_NAME_1));
//        assertEquals(parameterBean1, allCommandParameterBeans.get(parameterBean1.COMMAND_NAME_2));
//        assertEquals(parameterBean2, allCommandParameterBeans.get(parameterBean2.COMMAND_NAME_1));
//        assertEquals(parameterBean2, allCommandParameterBeans.get(parameterBean2.COMMAND_NAME_2));
    }


    @Test
    public void createFor() {
        when(applicationContext.getBeanDefinitionNames()).thenReturn(new String[]{BEAN_1, BEAN_2});

        when(applicationContext.getBean(BEAN_1)).thenReturn(parameterBean1);
        when(applicationContext.getBean(BEAN_2)).thenReturn(parameterBean2);

        factory.init();

        factory.createFor(parameterBean2.COMMAND_NAME_1);
    }

    @Test(expected = CannotInstantiateParameterObjectException.class)
    public void createFor_cannotInstantiate() {
        when(applicationContext.getBeanDefinitionNames()).thenReturn(new String[]{BEAN_1, BEAN_2, BEAN_3});

        when(applicationContext.getBean(BEAN_1)).thenReturn(parameterBean1);
        when(applicationContext.getBean(BEAN_2)).thenReturn(parameterBean2);
        when(applicationContext.getBean(BEAN_3)).thenReturn(brokenParameterBean);

        factory.init();

        for (Constructor<?> constructor : parameterBean2.getClass().getConstructors()) {
            constructor.setAccessible(false);
        }

        factory.createFor(brokenParameterBean.COMMAND_NAME);
    }

    @Test(expected = CommandParametersBeanNotFoundException.class)
    public void createFor_undefinedCommand() {
        factory.createFor(UNDEFINED_COMMAND);
    }
}
