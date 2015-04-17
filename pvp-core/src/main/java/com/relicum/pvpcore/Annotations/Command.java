package com.relicum.pvpcore.Annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Command used to define a commands settings.
 * <p>
 * aliases, description and permission are the only required values to set the
 * other are optional.
 *
 * @author Relicum
 * @version 0.0.1
 */
@Documented
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    /**
     * A list of aliases for the command. The first alias is the most important
     * -- it is the main name of the command.
     *
     * @return Aliases for a command
     */
    String[] aliases();

    /**
     * Set the permission for the command
     *
     * @return the permission string.
     */
    String perm();

    /**
     * Usage instruction.
     *
     * @return Usage instructions for a command
     */
    String usage() default "";

    /**
     * @return A short description for the command.
     */
    String desc();

    /**
     * The minimum number of arguments. This should be 0 or above.
     *
     * @return the minimum number of arguments
     */
    int min() default 0;

    /**
     * The maximum number of arguments. Use -1 for an unlimited number of
     * arguments.
     *
     * @return the maximum number of arguments
     */
    int max() default -1;

    /**
     * Is the command a sub command, default false
     *
     * @return the boolean
     */
    boolean isSub() default false;

    /**
     * Name of the parent command, used to prefix this command.
     *
     * @return the parent commands name
     */
    String parent() default "";

    /**
     * Set to true if Tab Complete is required
     *
     * @return set true to use tab complete, default is false not to use it.
     */
    boolean useTab() default false;
}
