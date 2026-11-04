package org.transformice.command.commands.admin;

// Imports
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;
import org.psframework.utils.PSBase64;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.database.data.PlayerEntity;

@Command(name = "changepassword", aliases = {"changepass", "changepw"}, usage = "[playerName] [password]", description = "Changes the player's password.", permission = Command.CommandPermission.ADMINISTRATOR, requiredArgs = 2)
@SuppressWarnings("unused")
public final class Changepassword implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        PlayerEntity playerAccount = server.getDatabase().loadPlayerByName(args.getFirst());
        String password = args.get(1);
        if (playerAccount == null) {
            CommandHandler.sendServerMessage(player, "The supplied argument isn't a valid nickname.");
            return;
        }

        try {
            byte[] salt = {(byte) 0xF7, (byte) 0x1A, (byte) 0xA6, (byte) 0xDE, (byte) 0x8F, (byte) 0x17, (byte) 0x76, (byte) 0xA8, (byte) 0x03, (byte) 0x9D, (byte) 0x32, (byte) 0xB8, (byte) 0xA1, (byte) 0x56, (byte) 0xB2, (byte) 0xA9, (byte) 0x3E, (byte) 0xDD, (byte) 0x43, (byte) 0x9D, (byte) 0xC5, (byte) 0xDD, (byte) 0xCE, (byte) 0x56, (byte) 0xD3, (byte) 0xB7, (byte) 0xA4, (byte) 0x05, (byte) 0x4A, (byte) 0x0D, (byte) 0x08, (byte) 0xB0};
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] firstHash = sha256.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : firstHash)
                hexString.append(String.format("%02x", b));

            byte[] firstHashHexBytes = hexString.toString().getBytes(StandardCharsets.UTF_8);
            byte[] combined = new byte[firstHashHexBytes.length + salt.length];
            System.arraycopy(firstHashHexBytes, 0, combined, 0, firstHashHexBytes.length);
            System.arraycopy(salt, 0, combined, firstHashHexBytes.length, salt.length);
            byte[] finalHash = sha256.digest(combined);
            playerAccount.password = Arrays.toString(PSBase64.encode(finalHash));
            server.getDatabase().savePlayer(playerAccount);
            CommandHandler.sendServerMessage(player, "Done");
            if(server.getPlayers().get(args.getFirst()) != null)
                server.getPlayers().get(args.getFirst()).disconnect(true);

            CommandHandler.sendServerMessage(player, String.format("Successfully changed the password of the player %s.", playerAccount.getUsername()));
        } catch (Exception ignored) {
            CommandHandler.sendServerMessage(player, String.format("Unable to changed the password of the player %s.", playerAccount.getUsername()));
        }
    }
}