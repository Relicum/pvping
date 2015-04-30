package com.relicum.duel.Commands;

import com.relicum.commands.AbstractCommand;
import com.relicum.duel.Duel;


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

}
