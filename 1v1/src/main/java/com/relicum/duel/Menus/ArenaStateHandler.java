package com.relicum.duel.Menus;

import com.relicum.pvpcore.Menus.AbstractItem;
import com.relicum.pvpcore.Menus.ActionHandler;
import com.relicum.pvpcore.Menus.ActionResponse;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Name: ArenaStateHandler.java Created: 18 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class ArenaStateHandler implements ActionHandler {


    @Override
    public ArenaStateHandler getExecutor() {
        return this;
    }

    @Override
    public ActionResponse perform(Player player, AbstractItem icon) {
        player.sendMessage("From 2");
        ActionResponse response = new ActionResponse(icon, player);
        response.setDoNothing(true);
        return response;
    }

    @Override
    public ActionResponse perform(Player player, AbstractItem icon, InventoryClickEvent event) {
        event.setCancelled(true);
        player.sendMessage("From all 3");
        ActionResponse response = new ActionResponse(icon, player);
        response.setDoNothing(true);
        return response;
    }
}
