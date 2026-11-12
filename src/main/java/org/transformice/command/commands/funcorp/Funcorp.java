package org.transformice.command.commands.funcorp;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameRoom;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.data.objects.StaffRoleType;

@Command(name = "funcorp", usage = "(help)", description = "Enable/disable the funcorp mode, or show the list of funcorp-related commands.", permission = {Command.CommandPermission.STRM_OWNER, Command.CommandPermission.FUNCORP, Command.CommandPermission.ADMINISTRATOR})
@SuppressWarnings("unused")
public final class Funcorp implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        if(!args.isEmpty() && args.getFirst().equals("help")) {
            boolean isStaff = player.hasStaffPermission(StaffRoleType.FUNCORP, false);
            String message = "FunCorp Commands:<br><br>";
            if (isStaff)
                message += "<J>/changenick</J> <V>[playerName] [newNickname|off]</V> : <BL>Temporarily changes a player's nickname.</BL><br>";

            message += """
                <J>/changesize</J> <V>[playerNames|*] [size|off]</V> : <BL>Temporarily changes players' size (0.1x - 5x).</BL><br>
                <J>/colormouse</J> <V>[playerNames|*] [color|off]</V> : <BL>Temporarily changes players' fur color.</BL><br>
                <J>/colornick</J> <V>[playerNames|*] [color|off]</V> : <BL>Temporarily changes players' nickname color.</BL><br>
                <J>/funcorp</J> <G>[help]</G> : <BL>Enables or disables FunCorp mode, or displays the available commands.</BL><br>
                <J>/linkmice</J> <V>[playerNames|*]</V> <G>[off]</G> : <BL>Temporarily links players together.</BL><br>
                <J>/lsfc</J> : <BL>Lists all online FunCorps.</BL><br>
                <J>/meep</J> <V>[playerNames|*]</V> <G>[off]</G> : <BL>Grants players the ability to use meep.</BL><br>
                <J>/profil</J> <V>[playerPartName]</V> : <BL>Displays a player's profile. (Aliases: /profile, /perfil, /profiel)</BL><br>
            """;

            if (isStaff) {
                message += """
                    <J>/room*</J> <V>[roomName]</V> : <BL>Joins any specified room. (Aliases: /salon*, /sala*)</BL><br>
                    <J>/roomevent</J> <G>[on|off]</G> : <BL>Highlights the current room in the room list.</BL><br>
                """;
            }

            message += """
                <J>/transformation</J> <V>[playerNames|*]</V> <G>[off]</G> : <BL>Temporarily grants players the ability to transform.</BL><br>
                <J>/tropplein</J> <V>[maxPlayers]</V> : <BL>Sets the maximum number of players allowed in a room.</BL><br>
                """;

            CommandHandler.sendLogMessage(player, 0, message);
            return;
        }

        GameRoom room = player.getPlayerRoom();
        if (!room.isFunCorp) {
            room.sendEnableFunCorp();
        } else {
            room.sendDisableFunCorp();
        }
    }
}