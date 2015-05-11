package com.relicum.duel.Commands;

import com.relicum.commands.AbstractCommand;
import com.relicum.duel.Duel;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * DuelCmd
 *
 * @author Relicum
 * @version 0.0.1
 */
public abstract class DuelCmd extends AbstractCommand<Duel> {

    /**
     * Instantiates a new AbstractCommand
     *
     * @param plugin the plugin
     */
    public DuelCmd(Duel plugin) {

        super(plugin, DuelMsg.getInstance());

    }

    @Override
    public abstract List<String> tabComp(int i, String[] args);

    @Override
    public abstract boolean onCommand(CommandSender sender, String label, String[] args);
}
