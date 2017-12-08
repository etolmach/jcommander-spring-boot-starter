package com.etolmach.spring.jcommander.impl;

import com.etolmach.spring.jcommander.JCommandHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;

/**
 * @author etolmach
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultJCommandHandler implements JCommandHandler {

    private Object controller;

    private Method method;

    private String command;

}
