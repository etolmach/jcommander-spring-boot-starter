package com.etolmach.spring.jcommander.impl;

import com.etolmach.spring.jcommander.JCommandWrapper;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author etolmach
 */

@Getter
@AllArgsConstructor
public class DefaultJCommandWrapper implements JCommandWrapper {

    private String name;

    private Object parameterBean;

}
