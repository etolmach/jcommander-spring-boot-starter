package com.etolmach.spring.jcommander.impl;

import com.etolmach.spring.jcommander.JCommandHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;

/**
 * @author etolmach
 */

@Getter
@AllArgsConstructor
public class DefaultJCommandHandler implements JCommandHandler {

    private Object controller;

    private Method method;

    private String command;

}
