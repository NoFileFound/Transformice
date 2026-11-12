package org.transformice.command;

// Imports
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.data.objects.RoomType;
import org.transformice.data.objects.StaffRoleType;
import org.transformice.database.data.CommandLogEntry;
import org.transformice.database.data.TribeEntry;

public final class CommandLoader {
    private final GameServer server;
    private final Map<String, CommandHandler> commands;
    private final Map<String, CommandHandler> aliases;
    private final Map<String, Command> annotations;

    /**
     * Creates a new command manager.
     * @param server The game server.
     */
    public CommandLoader(GameServer server) {
        this.server = server;
        this.commands = new TreeMap<>();
        this.aliases = new TreeMap<>();
        this.annotations = new TreeMap<>();
        this.loadCommands();
    }

    /**
     * Handles the given command in the game.
     * @param client The player who is going to run the command.
     * @param rawMessage The raw command without / and its arguments.
     * @param isTabCommand Is the command sent with /+Tab or just /.
     */
    public void invokeGameCommand(GamePlayer client, String rawMessage, boolean isTabCommand) {
        if (rawMessage == null || rawMessage.isBlank())
            return;

        String[] split = rawMessage.trim().split("\\s+");
        String commandName = split[0].toLowerCase();
        List<String> args = new ArrayList<>(Arrays.asList(split).subList(1, split.length));
        CommandHandler handler = this.getHandler(commandName);
        if (handler == null)
            return;

        Command annotation = this.annotations.get(commandName);
        if (annotation == null)
            return;

        if (!this.checkPermission(annotation.permission(), client))
            return;

        if (isTabCommand) {
            String message = String.format("/%s %s - %s", annotation.name(), annotation.usage(), annotation.description());
            if (annotation.aliases().length > 0)
                message += String.format(" (Aliases: /%s)", String.join(", /", annotation.aliases()));

            CommandHandler.sendServerMessage(client, message);
            return;
        }

        if (annotation.isFunCorpOnlyCommand() && !client.getPlayerRoom().isFunCorp) {
            CommandHandler.sendServerMessage(client, "FunCorp commands only work when the room is in FunCorp mode.");
            return;
        }

        if (args.size() < annotation.requiredArgs()) {
            CommandHandler.sendServerMessage(client, "You need more arguments to use this command.");
            return;
        }

        this.server.getDatabase().saveCommandLog(new CommandLogEntry(client.getPlayerName(), client.getIpAddress(), client.getPlayerCommunity(), rawMessage));
        handler.execute(client, this.server, args);
    }

    /**
     * Checks if given player can execute the command.
     * @param perms Command permissions.
     * @param client The given player.
     * @return True if he can run the command or else false.
     */
    private boolean checkPermission(Command.CommandPermission[] perms, GamePlayer client) {
        boolean hasPerm = false;
        for (Command.CommandPermission perm : perms) {
            switch (perm) {
                case Command.CommandPermission.NONE -> hasPerm = true;
                case Command.CommandPermission.DEBUG_ONLY -> hasPerm = this.server.getConfig().getIsDebug();
                case Command.CommandPermission.GUEST -> hasPerm = client.getIsGuest();
                case Command.CommandPermission.PLAYER -> hasPerm = !client.getIsGuest();
                case Command.CommandPermission.FUNCORP -> hasPerm = client.hasStaffPermission(StaffRoleType.FUNCORP, false);
                case Command.CommandPermission.LUADEV -> hasPerm = client.hasStaffPermission(StaffRoleType.LUADEV, false);
                case Command.CommandPermission.FASHIONSQUAD -> hasPerm = client.hasStaffPermission(StaffRoleType.FASHIONSQUAD, false);
                case Command.CommandPermission.MAPCREW -> hasPerm = client.hasStaffPermission(StaffRoleType.MAPCREW, false);
                case Command.CommandPermission.ARBITRE -> hasPerm = client.hasStaffPermission(StaffRoleType.ARBITRE, false);
                case Command.CommandPermission.TRIALMODO -> hasPerm = client.hasStaffPermission(StaffRoleType.TRIAL_MODO, false);
                case Command.CommandPermission.MODERATOR -> hasPerm = client.hasStaffPermission(StaffRoleType.PUBLIC_MODO, false) || client.hasStaffPermission(StaffRoleType.PRIVATE_MODO, false);
                case Command.CommandPermission.ADMINISTRATOR -> hasPerm = client.hasStaffPermission(StaffRoleType.ADMIN, false);
                case Command.CommandPermission.ROOM_OWNER -> hasPerm = client.getPlayerRoom().getRoomAuthor().equals(client.getPlayerName());
                case Command.CommandPermission.STRM_OWNER -> hasPerm = client.getPlayerRoom().getRoomNameSuffix().startsWith(String.format("*strm_%s", client.getPlayerName()));
                case Command.CommandPermission.TRIBE -> hasPerm = client.getPlayerRoom().getRoomType() == RoomType.TRIBE_HOUSE && (!client.getPlayerData().tribeName.isEmpty() && client.getPlayerData().tribeRank.hasPerm(TribeEntry.TribeRank.TribePerms.INVITE_MEMBERS));
            }

            if (hasPerm)
                return true;
        }

        return false;
    }

    /**
     * Returns a handler by label/alias.
     *
     * @param commandName The command label.
     * @return The command handler.
     */
    private CommandHandler getHandler(String commandName) {
        CommandHandler handler = this.commands.get(commandName);
        return handler == null ? this.aliases.get(commandName) : handler;
    }

    /**
     * Registers all commands.
     */
    private void loadCommands() {
        try (ScanResult scan = new ClassGraph().enableClassInfo().enableAnnotationInfo().acceptPackages("org.transformice.command.commands").scan()) {
            for (var classInfo : scan.getClassesWithAnnotation(Command.class.getName())) {
                try {
                    Class<?> commandClass = classInfo.loadClass();
                    if (!CommandHandler.class.isAssignableFrom(commandClass) || commandClass.isInterface() || Modifier.isAbstract(commandClass.getModifiers()))
                        continue;

                    Command annotation = commandClass.getAnnotation(Command.class);
                    if (annotation == null)
                        continue;

                    CommandHandler handler = (CommandHandler) commandClass.getDeclaredConstructor().newInstance();
                    String name = annotation.name().toLowerCase();
                    if (this.commands.containsKey(name) || this.aliases.containsKey(name))
                        throw new IllegalArgumentException("Duplicate command: " + name);

                    this.commands.put(name, handler);
                    this.annotations.put(name, annotation);
                    for (String alias : annotation.aliases()) {
                        alias = alias.toLowerCase();
                        if (this.commands.containsKey(alias) || this.aliases.containsKey(alias))
                            throw new IllegalArgumentException("Duplicate alias: " + alias);

                        this.aliases.put(alias, handler);
                        this.annotations.put(alias, annotation);
                    }

                } catch (Exception ex) {
                    this.server.getLogger().error("[CommandHandler] ", ex);
                }
            }
        }

        this.server.getLogger().info("[CommandHandler] Loaded the server commands.");
    }
}