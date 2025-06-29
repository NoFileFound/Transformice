package org.transformice.command;

// Imports
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    /**
     * The command name.
     */
    String name();

    /**
     * Command usage.
     */
    String usage() default "";

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
    CommandPermission[] permission() default CommandPermission.NONE;

    /**
     * If the command is invoked using / + Tab
     */
    boolean isTabCommand() default false;

    /**
     * Required arguments to run the command.
     */
    int requiredArgs() default 0;

    /**
     * Requires the command to execute in FunCorp mode.
     */
    boolean isFunCorpOnlyCommand() default false;

    enum CommandPermission {
        NONE,
        GUEST,
        PLAYER,
        ROOM_OWNER,
        STRM_OWNER,
        TRIBE,
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