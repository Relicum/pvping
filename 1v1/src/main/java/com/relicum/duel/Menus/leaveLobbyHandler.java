package com.relicum.duel.Menus;

import com.relicum.pvpcore.Menus.AbstractItem;
import com.relicum.pvpcore.Menus.ActionHandler;
import com.relicum.pvpcore.Menus.ActionResponse;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Name: LeaveLobbyHandler.java Created: 24 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class LeaveLobbyHandler implements ActionHandler {


    @Override
    public ActionHandler getExecutor() {
        return this;
    }

    @Override
    public ActionResponse perform(Player player, AbstractItem icon, InventoryClickEvent event) {
        event.setCancelled(true);
        if (icon.getIndex() < 4) {

            player.performCommand("1v1 leave");
            ActionResponse response = new ActionResponse(icon, player);
            response.setWillClose(true);
            return response;
        }

        ActionResponse response = new ActionResponse(icon, player);
        response.setWillClose(true);
        return response;

    }
}
