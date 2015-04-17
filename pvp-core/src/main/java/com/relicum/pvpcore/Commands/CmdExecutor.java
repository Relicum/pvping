package com.relicum.pvpcore.Commands;

import org.bukkit.command.CommandSender;

/**
 * CmdExecutor
 *
 * @author Relicum
 * @version 0.0.1
 */
public abstract interface CmdExecutor {

    /**
     * Runs the command
     *
     * @param sender the sender
     * @param label the label
     * @param args the args
     * @return the boolean
     */
    public abstract boolean onCommand(CommandSender sender, String label, String[] args);
}
