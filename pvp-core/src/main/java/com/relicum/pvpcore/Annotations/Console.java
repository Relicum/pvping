package com.relicum.pvpcore.Annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Console annotation indicates a command can be run from the console.
 * <p>
 * Simple annotate the class and the command will become accessible from the
 * console.
 *
 * @author Relicum
 * @version 0.0.1
 */
@Documented
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Console {

}
