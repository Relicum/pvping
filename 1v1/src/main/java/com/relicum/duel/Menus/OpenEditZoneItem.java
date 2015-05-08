package com.relicum.duel.Menus;

import com.relicum.pvpcore.Arenas.PvPZone;
import com.relicum.pvpcore.Menus.AbstractItem;
import com.relicum.pvpcore.Menus.ActionHandler;
import com.relicum.pvpcore.Menus.ActionResponse;
import org.bukkit.entity.Player;

/**
 * Name: OpenEditZoneItem.java Created: 06 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class OpenEditZoneItem implements ActionHandler {

    private PvPZone zone;

    public OpenEditZoneItem(PvPZone zone) {

        this.zone = zone;
    }

    public PvPZone getZone() {

        return zone;
    }

    @Override
    public OpenEditZoneItem getExecutor() {

        return this;
    }

    @Override
    public ActionResponse perform(Player player, AbstractItem icon) {

        player.sendMessage("Open Zone Editor has fired");
        ActionResponse response = new ActionResponse(icon);

        response.setPlayer(player);
        response.setDoNothing(true);

        icon.getMenu().switchMenu(player, MenuManager.getInstance().getZoneEditMenu(getZone()));
        return response;
    }
}
