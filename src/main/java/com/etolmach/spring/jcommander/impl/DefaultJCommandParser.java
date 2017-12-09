package com.etolmach.spring.jcommander.impl;

import com.beust.jcommander.JCommander;
import com.etolmach.spring.jcommander.JCommandParameterBeanFactory;
import com.etolmach.spring.jcommander.JCommandParser;
import com.etolmach.spring.jcommander.JCommandWrapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
        Set<Object> uniqueParameterObjects = new HashSet<>(parameterObjects.values());

        JCommander parser = buildParser(uniqueParameterObjects);
        parser.parse(args);

        String commandName = parser.getParsedCommand();
        Object parameters = parameterObjects.get(commandName);

        return new DefaultJCommandWrapper(commandName, parameters);
    }

    /**
     * Build vanilla JCommander parser with unique parameter objects (thread safes)
     */
    private JCommander buildParser(Set<Object> parameterObjects) {
        JCommander.Builder builder = getBuilder();
        parameterObjects.forEach(builder::addCommand);
        return builder.build();
    }

    protected JCommander.Builder getBuilder() {
        return JCommander.newBuilder();
    }

}
