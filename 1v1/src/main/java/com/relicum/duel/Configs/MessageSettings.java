package com.relicum.duel.Configs;

import com.google.common.base.Objects;
import com.relicum.commands.Interfaces.ConsoleColors;

/**
 * Name: MessageSettings.java Created: 23 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class MessageSettings {

    private String prefix = "§4§l|-§6§l1V1§4§l-| ";
    private String logPrefix = ConsoleColors.CON_WHITE + "[" + ConsoleColors.CON_BOLDGREEN + "DUEL" + ConsoleColors.CON_WHITE + "] " + ConsoleColors.CON_RESET;
    private String messageColor = "a";
    private String errorColor = "c";
    private String adminColor = "3";

    public MessageSettings() {
    }

    public String getAdminColor() {
        return adminColor;
    }

    public String getErrorColor() {
        return errorColor;
    }

    public String getLogPrefix() {
        return logPrefix;
    }

    public String getMessageColor() {
        return messageColor;
    }

    public String getPrefix() {
        return prefix;
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                       .add("adminColor", adminColor)
                       .add("prefix", prefix)
                       .add("logPrefix", logPrefix)
                       .add("messageColor", messageColor)
                       .add("errorColor", errorColor)
                       .toString();
    }
}
