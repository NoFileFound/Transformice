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
        String commandName = split[0];

        // gets the command.
        CommandHandler handler = this.getHandler(commandName);
        if(handler == null) {
            // command is not found.
            return;
        }

        Command annotation = this.annotations.get(commandName);
        if(annotation == null || (annotation.isTabCommand() && !isTabCommand)) return;

        // check for permissions.
        if(!checkPermission(annotation.permission(), client)) return;

        // handle the command.
        handler.execute(client, new ArrayList<>(Arrays.asList(split).subList(1, split.length)));
    }

    /**
     * Checks if given player can execute the command.
     * @param perms Command permissions.
     * @param client The given player.
     * @return True if he can run the command or else false.
     */
    private boolean checkPermission(Command.CommandPermission[] perms, Client client) {
        for(Command.CommandPermission perm : perms) {
            if(client.getAccount() == null && perm != Command.CommandPermission.GUEST && perm != Command.CommandPermission.NONE) {
                return false;
            }

            return switch (perm) {
                case Command.CommandPermission.NONE -> true;
                case Command.CommandPermission.DEBUG_ONLY -> Application.getPropertiesInfo().is_debug;
                case Command.CommandPermission.GUEST -> client.isGuest();
                case Command.CommandPermission.PLAYER -> !client.isGuest();
                case Command.CommandPermission.VIP -> client.getAccount().getPrivLevel() >= 2;
                case Command.CommandPermission.SENTINEL -> client.getAccount().getPrivLevel() == 4 || client.getAccount().getStaffRoles().contains("Sentinel");
                case Command.CommandPermission.FUNCORP -> client.getAccount().getPrivLevel() == 5 || client.getAccount().getStaffRoles().contains("FunCorp");
                case Command.CommandPermission.LUADEV -> client.getAccount().getPrivLevel() == 6 || client.getAccount().getStaffRoles().contains("LuaDev");
                case Command.CommandPermission.FASHIONSQUAD -> client.getAccount().getPrivLevel() == 7 || client.getAccount().getStaffRoles().contains("FashionSquad");
                case Command.CommandPermission.MAPCREW -> client.getAccount().getPrivLevel() == 8 || client.getAccount().getStaffRoles().contains("MapCrew");
                case Command.CommandPermission.MODERATOR -> client.getAccount().getPrivLevel() >= 9;
                case Command.CommandPermission.ADMINISTRATOR -> client.getAccount().getPrivLevel() == 11;
            };
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
                        Application.getLogger().error(String.format("Failed to load the command %s", annotated.getSimpleName()));
                    }
                }
        );

        Application.getLogger().info(String.format("Registered total commands: %s", cnt));
    }
}