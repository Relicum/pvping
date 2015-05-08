package com.relicum.duel.Menus;

import com.relicum.pvpcore.Menus.AbstractItem;
import com.relicum.pvpcore.Menus.ActionHandler;
import com.relicum.pvpcore.Menus.ActionResponse;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * Name: CollectionItem.java Created: 02 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class CollectionItem implements ActionHandler {

    private String zoneName;

    public CollectionItem(String text) {

        this.zoneName = text;
    }

    public String getZoneName() {

        return zoneName;
    }

    @Override
    public CollectionItem getExecutor() {

        return this;
    }

    @Override
    public ActionResponse perform(Player player, AbstractItem icon) {

        player.sendMessage("Open Zone Selector has fired");
        ActionResponse response = new ActionResponse(icon);

        response.setPlayer(player);
        response.setDoNothing(true);

        player.playSound(player.getLocation(), Sound.LEVEL_UP, 10.0f, 1.0f);
        icon.getMenu().switchMenu(player, MenuManager.getInstance().createSelectMenu(getZoneName()));
        return response;
    }
}
