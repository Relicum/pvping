package com.relicum.pvpcore.Menus;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.List;

/**
 * Name: OpenMenuItem.java Created: 02 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class OpenMenuItem extends FixedMenuItem {

    private Menu toMenu;

    public OpenMenuItem(String text, ItemStack icon, int index, List<String> desc) {
        super(text, icon, index, desc);
    }

    @Override
    public void onClick(Player paramPlayer) {

    }

}
