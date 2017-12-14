package com.etolmach.spring.jcommander.impl;

import com.beust.jcommander.JCommander;
import com.etolmach.spring.jcommander.JCommandParameterBeanFactory;
import com.etolmach.spring.jcommander.JCommandParser;
import com.etolmach.spring.jcommander.JCommandWrapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author etolmach
 */

@Component
public class DefaultJCommandParser implements JCommandParser {

    @Setter(onMethod = @__(@Autowired))
    private JCommandParameterBeanFactory parameterBeanFactory;

    @Override
    public JCommandWrapper parse(String[] args) {
        Map<String, Object> parameterObjects = parameterBeanFactory.createForAll();
        JCommander parser = buildParser(parameterObjects);
        parser.parse(args);

        String commandName = parser.getParsedCommand();
        Object parameters = parameterObjects.get(commandName);

        return new DefaultJCommandWrapper(commandName, parameters);
    }

    /**
     * Build vanilla JCommander parser with unique parameter objects (thread safes)
     */
    private JCommander buildParser(Map<String, Object> parameterObjects) {
        JCommander.Builder builder = getBuilder();
        parameterObjects
                .values()
                .forEach(builder::addCommand);
        return builder.build();
    }

    protected JCommander.Builder getBuilder() {
        return JCommander.newBuilder();
    }

}
