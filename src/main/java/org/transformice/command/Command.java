package org.transformice.command;

// Imports
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    /**
     * The command name.
     */
    String name();

    /**
     * The command description.
     */
    String[] description();

    /**
     * The command aliases.
     */
    String[] aliases() default {};

    /**
     * Required permission to run the command.
     */
    CommandPermission[] permission();

    /**
     * If the command is invoked using / + Tab
     */
    boolean isTabCommand() default false;

    enum CommandPermission {
        NONE,
        GUEST,
        PLAYER,
        VIP,
        SENTINEL,
        FUNCORP,
        LUADEV,
        FASHIONSQUAD,
        MAPCREW,
        MODERATOR,
        ADMINISTRATOR,
        DEBUG_ONLY
    }
}