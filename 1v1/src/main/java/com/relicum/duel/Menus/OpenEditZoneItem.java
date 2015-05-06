package com.relicum.duel.Menus;

import com.relicum.pvpcore.Arenas.PvPZone;
import com.relicum.pvpcore.Menus.FixedMenuItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.List;

/**
 * Name: OpenEditZoneItem.java Created: 06 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class OpenEditZoneItem extends FixedMenuItem {

    private PvPZone zone;

    public OpenEditZoneItem(String text, ItemStack icon, int index, List<String> desc, PvPZone zone) {
        super(text, icon, index, desc);
        this.zone = zone;
    }

    public PvPZone getZone() {
        return zone;
    }

    @Override
    public void onClick(Player paramPlayer) {
        paramPlayer.sendMessage("Openmenu on click has fired");

        getMenu().switchMenu(paramPlayer, MenuManager.getInstance().getZoneEditMenu(getZone()));

    }
}
