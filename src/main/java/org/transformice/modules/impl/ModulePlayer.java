package org.transformice.modules.impl;

// Imports
import org.transformice.GamePlayer;
import org.transformice.GameServer;

public abstract class ModulePlayer {
    protected final GameServer server;
    protected final GamePlayer player;

    /**
     * Creates a new module.
     * @param server The game server.
     * @param player The game player.
     */
    protected ModulePlayer(GameServer server, GamePlayer player) {
        this.server = server;
        this.player = player;
    }
}