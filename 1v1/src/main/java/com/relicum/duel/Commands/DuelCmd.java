package com.relicum.duel.Commands;

import com.relicum.duel.Duel;
import com.relicum.pvpcore.Commands.AbstractCommand;

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
