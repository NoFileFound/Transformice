package org.transformice.packets;

// Imports
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.transformice.Application;

@Getter
public final class PacketHandler {
    private final Map<Integer, RecvPacket> handlers;

    public PacketHandler(Class<? extends RecvPacket> handlerClass) {
        this.handlers = new HashMap<>();
        this.registerHandlers(handlerClass);
    }

    public void registerHandlers(Class<? extends RecvPacket> handlerClass) {
        var handlerClasses = Application.getReflector().getSubTypesOf(handlerClass);
        for (Class<? extends RecvPacket> recv : handlerClasses) {
            try {
                RecvPacket instance = recv.getDeclaredConstructor().newInstance();
                this.handlers.put(instance.getCode(), instance);
            } catch (Exception ignored) {

            }
        }

        Application.getLogger().info(String.format("Registered total packets: %d", this.handlers.size()));
    }
}