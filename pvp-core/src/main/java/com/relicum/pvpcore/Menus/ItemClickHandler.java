package com.relicum.pvpcore.Menus;

import org.bukkit.entity.Player;

/**
 * ItemClickHandler defines the actions when an item is clicked.
 *
 * @author Relicum
 * @version 0.0.1
 */
public interface ItemClickHandler {

    /**
     * Defines that actions to take when either an item in an inventory or
     * another item that can open and inventory is clicked.
     *
     * @param paramPlayer the player that clicked the menu
     */
    void onClick(Player paramPlayer);
}
