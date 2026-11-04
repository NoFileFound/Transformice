package org.transformice.modules.impl;

// Imports
import org.transformice.GameRoom;
import org.transformice.GameServer;

public abstract class ModuleRoom {
    protected final GameServer server;
    protected final GameRoom room;

    /**
     * Creates a new module.
     * @param server The game server.
     * @param room The game room.
     */
    protected ModuleRoom(GameServer server, GameRoom room) {
        this.server = server;
        this.room = room;
    }
}