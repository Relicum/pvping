package com.relicum.pvpcore.Menus.Handlers;

import com.relicum.pvpcore.Menus.AbstractItem;
import com.relicum.pvpcore.Menus.ActionHandler;
import com.relicum.pvpcore.Menus.ActionResponse;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * Name: CloseMenuHandler.java Created: 05 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class CloseMenuHandler implements ActionHandler {

    @Override
    public CloseMenuHandler getExecutor() {

        return this;
    }

    @Override
    public ActionResponse perform(Player player, AbstractItem icon) {

        player.playSound(player.getLocation(), Sound.CHEST_CLOSE, 10.0f, 1.0f);
        ActionResponse response = new ActionResponse(icon);
        response.setPlayer(player);
        response.setWillClose(true);

        return response;

    }


}
