package com.relicum.pvpcore.Commands;

import org.bukkit.command.CommandSender;

import static org.bukkit.ChatColor.AQUA;
import static org.bukkit.ChatColor.DARK_PURPLE;

/**
 * Msg
 *
 * @author Relicum
 * @version 0.0.1
 */
public interface Msg {

    char COLOR_CHAR = '\u00A7';
    char SAFE_CHAR = '&';

    /**
     * Gets the command prefix.
     * <p>
     * Override this method to set your own prefix for your plugin
     *
     * @return the prefix
     */
    default String getPrefix() {

        return DARK_PURPLE + "[" + AQUA + "PVP-PARENT" + DARK_PURPLE + "] ";
    }

    default char getColorChar() {

        return COLOR_CHAR;
    }

    /**
     * Get the minecraft color code to use for standard command messages.
     * <p>
     * Override this method to change the standard command text color.
     *
     * @return the color code
     */
    default String getInfoChar() {

        return "a";
    }

    /**
     * Get the minecraft color code to use for error command messages
     * <p>
     * Override this method to change the standard command error text color.
     * 
     * @return the color code
     */
    default String getErrorChar() {

        return "c";
    }

    /**
     * Get the minecraft color code to use for admin command messages
     * <p>
     * Override this method to change the standard command admin text color.
     * 
     * @return the color code
     */
    default String getAdminChar() {

        return "3";
    }

    default String getInfoColor() {

        return COLOR_CHAR + getInfoChar();
    }

    default String getErrorColor() {

        return COLOR_CHAR + getErrorChar();
    }

    default String getAdminColor() {

        return COLOR_CHAR + getAdminChar();
    }

    /**
     * Send command message.
     *
     * @param sender the @{@link org.bukkit.command.CommandSender} sender
     * @param message the {@link String} message
     */
    default void sendMessage(CommandSender sender, String message) {

        sender.sendMessage(colorize(getPrefix() + getInfoColor() + message));
    }

    /**
     * Send command error message.
     *
     * @param sender the @{@link org.bukkit.command.CommandSender} sender
     * @param message the {@link String} message
     */
    default void sendErrorMessage(CommandSender sender, String message) {

        sender.sendMessage(colorize(getPrefix() + getErrorColor() + message));
    }

    /**
     * Send command admin message.
     *
     * @param sender the @{@link org.bukkit.command.CommandSender} sender
     * @param message the {@link String} message
     */
    default void sendAdminMessage(CommandSender sender, String message) {

        sender.sendMessage(colorize(getPrefix() + getAdminColor() + message));
    }

    default String colorize(String string) {

        return string.replaceAll("(" + SAFE_CHAR + "([a-fklmnor0-9]))", COLOR_CHAR + "$2");
    }
}
