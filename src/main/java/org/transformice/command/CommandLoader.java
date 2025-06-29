package org.transformice.command;

// Imports
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.reflections.Reflections;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.database.embeds.TribeRank;

public class CommandLoader {
    private final Map<String, CommandHandler> commands;
    private final Map<String, CommandHandler> aliases;
    private final Map<String, Command> annotations;

    public CommandLoader() {
        this.commands = new TreeMap<>();
        this.aliases = new TreeMap<>();
        this.annotations = new TreeMap<>();

        // registers all commands.
        this.loadCommands();
    }

    /**
     * Handles the given command.
     * @param client The player who is going to run the command.
     * @param rawMessage The raw command without / and its arguments.
     * @param isTabCommand Is the command sent with /+Tab or just /.
     */
    public void invokeCommand(Client client, String rawMessage, boolean isTabCommand) {
        String[] split = rawMessage.split(" ");
        String commandName = split[0].toLowerCase();
        ArrayList<String> args = new ArrayList<>(Arrays.asList(split).subList(1, split.length));

        // gets the command.
        CommandHandler handler = this.getHandler(commandName);
        if(handler == null) {
            // command is not found.
            if(Application.getPropertiesInfo().is_debug) {
                CommandHandler.sendServerMessage(client, Application.getTranslationManager().get("commandnotfound", commandName));
            }
            return;
        }

        Command annotation = this.annotations.get(commandName);
        if(annotation == null || (annotation.isTabCommand() && !isTabCommand)) {
            return;
        }

        // check for permissions.
        if(!this.checkPermission(annotation.permission(), client)) {
            return;
        }

        // check for funcorp.
        if(annotation.isFunCorpOnlyCommand() && !client.getRoom().isFunCorp && (client.getAccount().getPrivLevel() < 7)) {
            CommandHandler.sendServerMessage(client, Application.getTranslationManager().get("funcorponlycommand"));
            return;
        }

        // check for command arguments.
        if(args.size() < annotation.requiredArgs()) {
            CommandHandler.sendServerMessage(client, Application.getTranslationManager().get("commandargsnotmet"));
            return;
        }

        // handle the command.
        handler.execute(client, client.getServer(), args);
    }

    /**
     * Checks if given player can execute the command.
     * @param perms Command permissions.
     * @param client The given player.
     * @return True if he can run the command or else false.
     */
    private boolean checkPermission(Command.CommandPermission[] perms, Client client) {
        boolean hasPerm = false;
        for(Command.CommandPermission perm : perms) {
            switch (perm) {
                case Command.CommandPermission.NONE -> hasPerm = true;
                case Command.CommandPermission.DEBUG_ONLY -> hasPerm = Application.getPropertiesInfo().is_debug;
                case Command.CommandPermission.GUEST -> hasPerm = client.isGuest();
                case Command.CommandPermission.PLAYER -> hasPerm = !client.isGuest();
                case Command.CommandPermission.VIP -> hasPerm = client.isVip() || client.getAccount().getPrivLevel() >= 4;
                case Command.CommandPermission.SENTINEL -> hasPerm = client.hasStaffPermission("Sentinelle", "Commands");
                case Command.CommandPermission.FUNCORP -> hasPerm = client.hasStaffPermission("FunCorp", "Commands");
                case Command.CommandPermission.LUADEV -> hasPerm = client.hasStaffPermission("LuaDev", "Commands");
                case Command.CommandPermission.FASHIONSQUAD -> hasPerm = client.hasStaffPermission("FashionSquad", "Commands");
                case Command.CommandPermission.MAPCREW -> hasPerm = client.hasStaffPermission("MapCrew", "Commands");
                case Command.CommandPermission.MODERATOR -> hasPerm = client.hasStaffPermission("Modo", "Commands");
                case Command.CommandPermission.ADMINISTRATOR -> hasPerm = client.hasStaffPermission("Admin", "Commands");
                case Command.CommandPermission.ROOM_OWNER -> hasPerm = client.getRoom().getRoomCreator().equals(client.getPlayerName());
                case Command.CommandPermission.STRM_OWNER -> hasPerm = client.getRoom().getRoomName().startsWith(String.format("*strm_%s", client.getPlayerName()));
                case Command.CommandPermission.TRIBE -> hasPerm = client.getRoom().isTribeHouse() && (!client.getAccount().getTribeName().isEmpty() && client.getAccount().getTribeRank().hasPerm(TribeRank.TribePerms.INVITE_MEMBERS));
            }
            if(hasPerm) return true;
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
        Reflections reflector = Application.getReflector();
        Set<Class<?>> classes = reflector.getTypesAnnotatedWith(Command.class);
        AtomicInteger cnt = new AtomicInteger();

        classes.forEach(
                annotated -> {
                    try {
                        Command cmdData = annotated.getAnnotation(Command.class);
                        Object object = annotated.getDeclaredConstructor().newInstance();
                        if (object instanceof CommandHandler) {
                            cnt.getAndIncrement();
                            Command annotation = object.getClass().getAnnotation(Command.class);
                            this.annotations.put(cmdData.name().toLowerCase(), annotation);
                            this.commands.put(cmdData.name().toLowerCase(), (CommandHandler)object);
                            for (String alias : annotation.aliases()) {
                                this.aliases.put(alias, (CommandHandler)object);
                                this.annotations.put(alias, annotation);
                            }
                        }
                    } catch (Exception ignored) {
                        Application.getLogger().error(Application.getTranslationManager().get("commandfailedtoreg", annotated.getSimpleName()));
                    }
                }
        );
        Application.getLogger().info(Application.getTranslationManager().get("totalcommands", cnt));
    }
}