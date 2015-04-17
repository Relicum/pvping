package com.relicum.pvpcore;

import com.relicum.pvpcore.Commands.ConsoleColors;
import org.apache.commons.lang.text.StrBuilder;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.bukkit.ChatColor.*;

/**
 * MessageUtil
 *
 * @author Relicum
 */
public class MessageUtil implements ConsoleColors {

    private static final char COLOR_CHAR = '\u00A7';
    private static final char SAFE_CHAR = '&';
    private static final String prefix = DARK_PURPLE + "[" + AQUA + "PvpCore" + DARK_PURPLE + "] ";
    private static final String debugPrefix = DARK_RED + "[PvpCore-DEBUG] " + YELLOW;
    private static final ChatColor infoColor = GREEN;
    private static final ChatColor errorColor = RED;
    private static final ChatColor adminColor = LIGHT_PURPLE;
    private static final String logPrefix = CON_WHITE + "[" + CON_BOLDGREEN + "PvpCore" + CON_WHITE + "] " + CON_RESET;
    private static final String logDebugPrefix = CON_WHITE + "[" + CON_BOLDRED + "PvpCore-DEBUG" + CON_WHITE + "] " + CON_RESET;
    private static final String logInfoColor = CON_BOLDYELLOW;
    private static final String logSevereColor = CON_BOLDRED;
    private static final String logWarningColor = CON_BOLDMAGENTA;
    private static final String prefixNoColor = "PvpCore ";

    /**
     * Send command raw message.
     *
     * @param sender the @{@link org.bukkit.command.CommandSender} sender
     * @param message the {@link String} message
     */
    public static void sendRawMessage(CommandSender sender, String message) {

        sender.sendMessage(message);
    }

    /**
     * Send raw message. No formatting is done to the messages
     *
     * @param sender the sender
     * @param messages the messages
     */
    public static void sendRawMessage(CommandSender sender, String[] messages) {

        sender.sendMessage(messages);
    }

    /**
     * Send String list of messages.
     *
     * @param sender the sender
     * @param messages the messages
     */
    public static void sendMessage(CommandSender sender, String[] messages) {

        for (String s : messages)
        {
            sender.sendMessage(format(s));
        }

    }

    /**
     * Send command message.
     *
     * @param sender the @{@link org.bukkit.command.CommandSender} sender
     * @param message the {@link String} message
     */
    public static void sendMessage(CommandSender sender, String message) {

        sender.sendMessage(format(message));
    }

    /**
     * Send command error message.
     *
     * @param sender the @{@link org.bukkit.command.CommandSender} sender
     * @param message the {@link String} message
     */
    public static void sendErrorMessage(CommandSender sender, String message) {

        sender.sendMessage(colorize(prefix + errorColor + message));
    }

    /**
     * Send command admin message.
     *
     * @param sender the @{@link org.bukkit.command.CommandSender} sender
     * @param message the {@link String} message
     */
    public static void sendAdminMessage(CommandSender sender, String message) {

        sender.sendMessage(colorize(prefix + adminColor + message));
    }

    /**
     * Format string.
     *
     * @param message the message
     * @return the string formatted with prefix, message color and converts any
     * other color codes in message
     */
    public static String format(String message) {

        return colorize(prefix + infoColor + message);
    }

    /**
     * Log to console
     *
     * @param message the {@link Object} message
     */
    public static void log(Object message) {

        System.out.println(message);
    }

    /**
     * Log to Minecraft Logger
     *
     * @param level the {@link java.util.logging.Level}
     * @param message the {@link Object} message
     */
    public static void log(Level level, Object message) {

        Logger.getLogger("MineCraft").log(level, prefixNoColor + message.toString() + CON_RESET);
    }

    /**
     * Log - Level INFO with color formatted.
     *
     * @param message the {@link Object} message
     */
    public static void logInfoFormatted(Object message) {

        System.out.println(logPrefix + logInfoColor + stripColor(message.toString()) + CON_RESET);
    }

    /**
     * Log - Level SEVERE with color formatted.
     *
     * @param message the {@link Object} message
     */
    public static void logServereFormatted(Object message) {

        System.out.println(logPrefix + logSevereColor + stripColor(message.toString()) + CON_RESET);
    }

    /**
     * Log - Level WARNING with color formatted.
     *
     * @param message the {@link Object} message
     */
    public static void logWarningFormatted(Object message) {

        System.out.println(logPrefix + logWarningColor + stripColor(message.toString()) + CON_RESET);
    }

    /**
     * Strip color from the string when the character <strong>&#xa7;</strong> is
     * found followed by a minecraft color code.
     * <p>
     * This will remove both the <strong>&#xa7;</strong> and the minecraft color
     * code.
     * <p>
     * This will strip color from all valid matches in the entire string passed
     * to it.
     *
     * @param string the string to remove color from
     * @return the string stripped of all color.
     */
    public static String stripColor(String string) {

        return stripColor(string, COLOR_CHAR);
    }

    /**
     * Strip color from the string using a defined character when it is found
     * followed by a minecraft color code.
     * <p>
     * This will remove both the defined character and the minecraft color code.
     * <p>
     * Useful if the string contains color defined using the
     * <strong>&amp;</strong> character.
     * <p>
     * This will strip color from all valid matches in the entire string passed
     * to it.
     *
     * @param string the string to remove color from
     * @param find the character to search for.
     * @return the string stripped of all color.
     */
    public static String stripColor(String string, char find) {

        return string.replaceAll("(" + find + "([a-fklmnor0-9]))", "");
    }

    /**
     * Colorize the string replacing <strong>&amp;</strong> with
     * <strong>&#xa7;</strong> ONLY if it is followed by a minecraft color code.
     * <p>
     * If the <strong>&amp;</strong> is not followed by a minecraft color code
     * it will not be replaced.
     * <p>
     * This will replace all valid matches in the entire string passed to it.
     *
     * @param string the string to be colorize
     * @return the colorized string
     */
    public static String colorize(String string) {

        return colorize(string, SAFE_CHAR, COLOR_CHAR);
    }

    /**
     * Colorize the string using a defined character, replacing it with
     * <strong>&#xa7;</strong>, ONLY if it is followed by a minecraft color
     * code.
     * <p>
     * If the defined character is not followed by a minecraft color code it
     * will not be replaced.
     * <p>
     * This will replace all valid matches in the entire string passed to it.
     *
     * @param string the string to be colorize.
     * @param find the character to search for.
     * @return the colorized string
     */
    public static String colorize(String string, char find) {

        return colorize(string, find, COLOR_CHAR);
    }

    /**
     * Colorize the string using a defined character, replacing it with a
     * defined character, ONLY if it is followed by a minecraft color code.
     * <p>
     * If the defined character is not followed by a minecraft color code it
     * will not be replaced.
     * <p>
     * This will replace all valid matches in the entire string passed to it.
     * <p>
     * This method is useful if you wish to reverse the process, eg convert
     * <strong>&#xa7;6</strong> to <strong>&amp;6</strong> again only valid
     * matches will be replaced.
     *
     * @param string the string to be colorize.
     * @param find the character to search for.
     * @param replace the replacement character.
     * @return the colorized string
     */
    public static String colorize(String string, char find, char replace) {

        return string.replaceAll("(" + find + "([a-fklmnor0-9]))", replace + "$2");
    }

    public static void clearChat(Player player) {

        for (int i = 0; i < 100; i++)
        {
            player.sendMessage("");
        }

    }

    public static void clearChat(Player player, int lines) {

        for (int i = 0; i < lines; i++)
        {
            player.sendMessage("");
        }

    }

    public static String fullline(final ChatColor color, final ChatColor color2, final ChatColor style, char character) {

        StrBuilder sb = new StrBuilder();
        boolean t = true;
        for (int i = 0; i < 53; i++)
        {
            if (t)
            {
                sb.append(style).append(color).append(character);
                t = false;
            }
            else
            {
                sb.append(style).append(color2).append(character);
                t = true;
            }
        }
        return sb.toString();
    }

    public static String centeredHeading(ChatColor color, ChatColor style, String heading) {

        StrBuilder sb = new StrBuilder(58);
        sb.append(" ").appendPadding(20, ' ').append(style).append("").append(color).append(heading).appendPadding(8, ' ');
        return sb.toString();
    }

}
