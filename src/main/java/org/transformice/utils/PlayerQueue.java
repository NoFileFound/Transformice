package org.transformice.utils;

// Imports
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;

public final class PlayerQueue {
    private final int maxPlayers;
    private final Set<NetSession> players = new HashSet<>();
    private final Queue<NetSession> queue = new ArrayDeque<>();
    private final Map<NetSession, LoginRequest> loginRequests = new HashMap<>();

    /**
     * Creates a new player queue.
     * @param maxPlayers The maximum players the queue can hold.
     */
    public PlayerQueue(int maxPlayers) {
        if (maxPlayers < 1)
            throw new IllegalArgumentException("maxPlayers must be positive");

        this.maxPlayers = maxPlayers;
    }

    /**
     * Joins a player in the queue.
     * @param player The player to join.
     * @param header The player's packet header.
     * @param payload The player's packet payload.
     * @return True if the player can be added in the queue.
     */
    public synchronized boolean join(NetSession player, NetByteArray header, NetByteArray payload) {
        if (this.players.contains(player))
            return true;

        if (this.queue.contains(player))
            return false;

        if (this.players.size() >= this.maxPlayers) {
            this.queue.offer(player);
            this.loginRequests.put(player, new LoginRequest(header, payload));
            return false;
        }

        this.players.add(player);
        return true;
    }

    /**
     * Removes a player from the queue.
     * @return The next player to admit, or null if nobody is waiting.
     */
    public synchronized NetSession leave(NetSession player) {
        if (!this.players.remove(player)) {
            this.queue.remove(player);
            this.loginRequests.remove(player);
            return null;
        }

        NetSession nextPlayer = this.queue.poll();
        if (nextPlayer != null)
            this.players.add(nextPlayer);

        return nextPlayer;
    }

    /**
     * Gets the player's position in the queue.
     * @param player The player.
     * @return The position in the queue.
     */
    public synchronized int getQueuePosition(NetSession player) {
        int position = 1;
        for (NetSession queuedPlayer : this.queue) {
            if (queuedPlayer.equals(player))
                return position;

            position++;
        }

        return 0;
    }

    /**
     * Gets the login request from the next player.
     * @param player The player.
     * @return The login request.
     */
    public synchronized LoginRequest removeLoginRequest(NetSession player) {
        return this.loginRequests.get(player);
    }

    /**
     * The login request class.
     */
    public static final class LoginRequest {
        private final NetByteArray header;
        private final NetByteArray data;

        /**
         * Creates a new login request.
         * @param header The header.
         * @param data The payload.
         */
        public LoginRequest(NetByteArray header, NetByteArray data) {
            this.header = header;
            this.data = data;
        }

        /**
         * Gets the header.
         * @return The header.
         */
        public NetByteArray getHeader() {
            return this.header;
        }

        /**
         * Gets the payload.
         * @return The payload.
         */
        public NetByteArray getData() {
            return this.data;
        }
    }
}