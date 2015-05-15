package com.relicum.pvpcore.Menus;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Name: ActionHandler.java Created: 14 April 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public interface ActionHandler {

    ActionHandler getExecutor();

    default ActionResponse perform(Player player, AbstractItem icon) {

        return new ActionResponse(icon);
    }

    default ActionResponse perform(Player player, AbstractItem icon, InventoryClickEvent event) {

        return new ActionResponse(icon);
    }
}
