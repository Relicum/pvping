package com.relicum.duel.Menus;

import com.relicum.pvpcore.Menus.AbstractItem;
import com.relicum.pvpcore.Menus.ClickAction;
import com.relicum.pvpcore.Menus.Handlers.CloseMenuHandler;
import com.relicum.utilities.Items.ItemBuilder;
import org.bukkit.Material;

/**
 * Name: CloseItem.java Created: 18 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class CloseItem extends AbstractItem {

    public CloseItem(int paramSlot) {
        super(new ItemBuilder(Material.GLASS).setDisplayName("&5&lClose Menu").build(), paramSlot, ClickAction.CLOSE_MENU, new CloseMenuHandler());
    }
}
