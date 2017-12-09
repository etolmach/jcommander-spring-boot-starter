package com.etolmach.spring.jcommander.config;

import com.etolmach.spring.jcommander.JCommandWrapper;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static com.etolmach.spring.jcommander.property.JCommanderProperties.ENABLED;
import static com.etolmach.spring.jcommander.property.JCommanderProperties.PREFIX;

/**
 * @author etolmach
 */

@Configuration
@AutoConfigureAfter
@ConditionalOnProperty(prefix = PREFIX, name = ENABLED, matchIfMissing = true)
@ComponentScan(basePackageClasses = JCommandWrapper.class)
public class JCommanderAutoConfiguration {
    
}
