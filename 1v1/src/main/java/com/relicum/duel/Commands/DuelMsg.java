package com.relicum.duel.Commands;

import com.relicum.commands.Interfaces.Msg;
import static org.bukkit.ChatColor.*;

/**
 * DuelMsg
 *
 * @author Relicum
 * @version 0.0.1
 */
public final class DuelMsg implements Msg {

    private static DuelMsg instance = null;

    protected DuelMsg() {

    }

    public static DuelMsg getInstance() {

        if (instance == null) {
            instance = new DuelMsg();
        }
        return instance;
    }

    @Override
    public String getInfoChar() {

        return "6";
    }

    @Override
    public String getPrefix() {

        return String.valueOf(DARK_RED) + BOLD + "|-" + GOLD + BOLD + "1V1" + DARK_RED + BOLD + "-| " + RESET;
    }

}
