package org.transformice.modules.impl;

// Imports
import org.transformice.GameServer;

public abstract class ModuleServer {
    protected final GameServer server;

    /**
     * Creates a new server module.
     * @param server The game's server.
     */
    protected ModuleServer(GameServer server) {
        this.server = server;
    }
}