package com.etolmach.spring.jcommander;

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

}