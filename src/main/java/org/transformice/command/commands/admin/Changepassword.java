package org.transformice.command.commands.admin;

// Imports
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.List;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.database.collections.Account;
import org.transformice.database.collections.Stafflog;

@Command(
        name = "changepassword",
        usage = "[playerName] [password]",
        description = "Changes the password of specific player.",
        permission = Command.CommandPermission.ADMINISTRATOR,
        requiredArgs = 2
)
@SuppressWarnings("unused")
public final class Changepassword implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        Account playerAccount = server.getPlayerAccount(args.getFirst());
        String password = args.get(1);
        if (playerAccount == null) {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("invalidusername"));
            return;
        }

        try {
            byte[] salt = {(byte) 0xF7, (byte) 0x1A, (byte) 0xA6, (byte) 0xDE, (byte) 0x8F, (byte) 0x17, (byte) 0x76, (byte) 0xA8, (byte) 0x03, (byte) 0x9D, (byte) 0x32, (byte) 0xB8, (byte) 0xA1, (byte) 0x56, (byte) 0xB2, (byte) 0xA9, (byte) 0x3E, (byte) 0xDD, (byte) 0x43, (byte) 0x9D, (byte) 0xC5, (byte) 0xDD, (byte) 0xCE, (byte) 0x56, (byte) 0xD3, (byte) 0xB7, (byte) 0xA4, (byte) 0x05, (byte) 0x4A, (byte) 0x0D, (byte) 0x08, (byte) 0xB0};
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] firstHash = sha256.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : firstHash) {
                hexString.append(String.format("%02x", b));
            }
            byte[] firstHashHexBytes = hexString.toString().getBytes(StandardCharsets.UTF_8);
            byte[] combined = new byte[firstHashHexBytes.length + salt.length];
            System.arraycopy(firstHashHexBytes, 0, combined, 0, firstHashHexBytes.length);
            System.arraycopy(salt, 0, combined, firstHashHexBytes.length, salt.length);
            byte[] finalHash = sha256.digest(combined);
            playerAccount.setPassword(Base64.getEncoder().encodeToString(finalHash));
            playerAccount.save();
            new Stafflog(player.getPlayerName(), "changepassword", args);
            CommandHandler.sendServerMessage(player, "Done");
            if(server.checkIsConnected(args.getFirst())) {
                server.getPlayers().get(args.getFirst()).closeConnection();
            }
        } catch (Exception ignored) {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("changeuserpassfail"));
        }
    }
}