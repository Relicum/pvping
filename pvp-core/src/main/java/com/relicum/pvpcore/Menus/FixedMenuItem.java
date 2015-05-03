package com.relicum.pvpcore.Menus;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.List;

/**
 * Name: FixedMenuItem.java Created: 02 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class FixedMenuItem extends MenuItem {

    public FixedMenuItem(String text, ItemStack icon, int index, List<String> desc) {
        super(text, icon, index, desc);
    }

    public void onClick(Player paramPlayer) {

    }
}
