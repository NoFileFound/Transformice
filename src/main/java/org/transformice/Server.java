package org.transformice;

// Imports
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import lombok.Getter;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.transformice.command.CommandHandler;
import org.transformice.command.CommandLoader;
import org.transformice.connection.*;
import org.transformice.database.DBUtils;
import org.transformice.database.collections.Loginlog;
import org.transformice.database.collections.Sanction;
import org.transformice.libraries.Pair;
import org.transformice.packets.PacketHandler;
import org.transformice.packets.RecvPacket;
import org.transformice.utils.Utils;

public class Server {
    private final List<Channel> channels = new ArrayList<>();
    @Getter private PacketHandler packetHandler;
    @Getter private CommandLoader commandHandler;
    @Getter private Map<String, Client> players;
    @Getter private SessionManager sessionManager;
    @Getter private List<String> tempBlackList;
    protected int lastClientSessionId;

    public Server() {
        this.packetHandler = new PacketHandler(RecvPacket.class);
        this.commandHandler = new CommandLoader();
        this.players = new HashMap<>();
        this.tempBlackList = new ArrayList<>();
    }

    /**
     * Gets the latest sanction of the given player.
     * @param playerName Player's name.
     * @param type Punishment type.
     * @return A pair of punishment duration and punishment reason.
     */
    public Pair<Long, String> getLatestSanctionStatus(String playerName, String type) {
        Sanction mySanction = DBUtils.findLatestSanction(playerName, type);
        if(mySanction != null) {
            if(mySanction.getIsPermanent()) return new Pair<>(-1L, mySanction.getReason());

            long time = mySanction.getExpirationDate();
            long currentTime = Utils.getUnixTime();
            if(time < currentTime) {
                mySanction.setState("Expired");
                mySanction.save();
                return new Pair<>(0L, "");
            }
            return new Pair<>((time - currentTime), mySanction.getReason());
        }
        return new Pair<>(0L, "");
    }

    /**
     * Checks if the given player is connected in the game.
     * @param playerName The given player's name.
     * @return True if he is connected or else false.
     */
    public boolean checkIsConnected(String playerName) {
        return this.players.get(playerName) != null;
    }

    /**
     * Initializes the server.
     */
    public void startServer() {
        /*Application.getLogger().info(String.format("Authorization Key: %s", Application.getSwfInfo().authorization_key));
        Application.getLogger().info(String.format("Connection Key: %s", Application.getSwfInfo().connection_key));
        Application.getLogger().info(String.format("Login Keys: %s", Application.getSwfInfo().login_keys));
        Application.getLogger().info(String.format("Packet Keys: %s", Application.getSwfInfo().packet_keys));
        Application.getLogger().info(String.format("Game Version: %d", Application.getSwfInfo().version));
        Application.getLogger().info(String.format("Game Ports: %s", Application.getSwfInfo().ports));
        if(!Application.getSwfInfo().swf_url.isEmpty()) {
            Application.getLogger().info(String.format("Game URL: %s", Application.getSwfInfo().swf_url));
        }*/

        if(!Application.getSwfInfo().ports.isEmpty()) {
            ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
            this.sessionManager = new SessionManager(this);
            ChannelPipeline pipeline = bootstrap.getPipeline();
            pipeline.addLast("encoder", new Encoder());
            pipeline.addLast("decoder", new Decoder());
            pipeline.addLast("handler", new ClientHandler(this));

            for(Integer port : Application.getSwfInfo().ports) {
                this.channels.add(bootstrap.bind(new InetSocketAddress(port)));
            }
        } else {
            Application.getLogger().error("Unable to start the server because there are no ports to start.");
        }
    }

    /**
     * Shutdowns the server.
     */
    public void closeServer() {
        for (Channel channel : this.channels) {
            channel.unbind();
        }
    }

    public void recordLoginLog(String playerName, String ipAddress, String ipCountry, String langue) {
        long currentTime = Utils.getUnixTime();
        Loginlog myInfo = DBUtils.findLatestLogInfo(playerName);
        if (myInfo == null || currentTime - myInfo.getDate() >= 86400) {
            new Loginlog(playerName, currentTime, ipAddress, ipCountry, langue);
        }
    }
}