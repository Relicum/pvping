package com.relicum.pvpcore.Gamers;

import org.bukkit.entity.Player;

/**
 * Name: IPlayerSettings.java Created: 03 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public interface IPlayerSettings {

    /**
     * Restore the players settings.
     *
     * @param player the player
     */
    void restore(Player player);

}
