package com.etolmach.spring.jcommander.runner;

import com.etolmach.spring.jcommander.JCommandLineExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import static com.etolmach.spring.jcommander.property.JCommanderProperties.COMMANDLINE_RUNNER_ENABLED;
import static com.etolmach.spring.jcommander.property.JCommanderProperties.PREFIX;

/**
 * @author etolmach
 */

@Component
@ConditionalOnProperty(prefix = PREFIX, name = COMMANDLINE_RUNNER_ENABLED, matchIfMissing = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JCommandLineRunner implements CommandLineRunner {

    private final JCommandLineExecutor commandLineExecutor;

    @Override
    public void run(String... args) {
        commandLineExecutor.execute(args);
    }
}