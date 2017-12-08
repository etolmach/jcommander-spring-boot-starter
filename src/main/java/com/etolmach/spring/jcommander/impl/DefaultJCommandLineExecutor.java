package com.etolmach.spring.jcommander.impl;

import com.etolmach.spring.jcommander.JCommandExecutor;
import com.etolmach.spring.jcommander.JCommandLineExecutor;
import com.etolmach.spring.jcommander.JCommandParser;
import com.etolmach.spring.jcommander.JCommandWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Component;

/**
 * @author etolmach
 */

@Component
@ConditionalOnSingleCandidate(JCommandLineExecutor.class)
public class DefaultJCommandLineExecutor implements JCommandLineExecutor {

    @Autowired
    private JCommandParser parser;

    @Autowired
    private JCommandExecutor commandExecutor;

    @Override
    public void execute(String[] args) {
        JCommandWrapper command = parser.parse(args);
        commandExecutor.execute(command);
    }
}
