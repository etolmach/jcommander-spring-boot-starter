package com.etolmach.spring.jcommander;

import java.util.Map;

/**
 * @author etolmach
 */
public interface JCommandHandlerContext {

    /**
     * Get handler for command
     *
     * @param command Command name
     * @return Command handler
     */
    JCommandHandler getHandler(String command);

    /**
     * Get all registered handlers
     *
     * @return Map of command handlers
     */
    Map<String, JCommandHandler> getHandlers();

}