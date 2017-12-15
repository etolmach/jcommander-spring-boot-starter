package com.etolmach.spring.jcommander.impl;

import com.etolmach.spring.jcommander.JCommandExecutor;
import com.etolmach.spring.jcommander.JCommandLineExecutor;
import com.etolmach.spring.jcommander.JCommandParser;
import com.etolmach.spring.jcommander.JCommandWrapper;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Component;

/**
 * @author etolmach
 */

@Component
@ConditionalOnSingleCandidate(JCommandLineExecutor.class)
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DefaultJCommandLineExecutor implements JCommandLineExecutor {

    @NonNull
    @Setter(onMethod = @__(@Autowired))
    private JCommandParser parser;

    @NonNull
    @Setter(onMethod = @__(@Autowired))
    private JCommandExecutor commandExecutor;

    @Override
    public void execute(String[] args) {
        JCommandWrapper command = parser.parse(args);
        commandExecutor.execute(command);
    }
}
