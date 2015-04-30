package com.relicum.pvpcore;

import org.bukkit.ChatColor;
import java.text.MessageFormat;

/**
 * Name: FormatUtil.java Created: 28 April 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class FormatUtil {

    public static String format(String format, Object[] objects) {
        String ret = MessageFormat.format(format, objects);
        return ChatColor.translateAlternateColorCodes('&', ret);
    }

    public static String format(String format) {
        return ChatColor.translateAlternateColorCodes('&', format);

    }

    public static String formatNoColor(String format, Object[] objects) {
        return MessageFormat.format(format, objects);

    }
}
