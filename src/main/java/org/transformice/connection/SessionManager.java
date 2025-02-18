package org.transformice.connection;

// Imports
import java.util.HashMap;
import java.util.Map;
import org.jboss.netty.channel.Channel;
import org.transformice.Client;
import org.transformice.Server;

public final class SessionManager {
    private final Map<Integer, Client> sessions = new HashMap<>();
    private final Server server;

    public SessionManager(Server server) {
        this.server = server;
    }

    public void addSession(Channel channel) {
        Client client = new Client(this.server, channel);
        this.sessions.put(channel.getId(), client);
        channel.setAttachment(client);
    }

    public void removeSession(Channel channel) {
        this.sessions.remove(channel.getId());
    }
}