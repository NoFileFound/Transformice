package org.transformice.packets;

// Imports
import lombok.Getter;
import java.util.HashMap;
import java.util.Map;
import org.transformice.Application;

@Getter
public final class PacketHandler {
    private final Map<PacketStruct, RecvPacket> handlers;

    public PacketHandler(Class<? extends RecvPacket> handlerClass) {
        this.handlers = new HashMap<>();
        this.registerHandlers(handlerClass);
    }

    public void registerHandlers(Class<? extends RecvPacket> handlerClass) {
        var handlerClasses = Application.getReflector().getSubTypesOf(handlerClass);
        for (Class<? extends RecvPacket> recv : handlerClasses) {
            try {
                RecvPacket instance = recv.getDeclaredConstructor().newInstance();
                this.handlers.put(new PacketStruct(instance.getCode(), instance.isLegacyPacket()), instance);
            } catch (Exception ignored) {
            }
        }

        Application.getLogger().info(Application.getTranslationManager().get("totalpackets", this.handlers.size()));
    }
}