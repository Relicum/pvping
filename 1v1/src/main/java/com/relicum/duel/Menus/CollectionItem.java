package com.relicum.duel.Menus;

import com.relicum.duel.Duel;
import com.relicum.pvpcore.Menus.MenuItem;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Name: CollectionItem.java Created: 02 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class CollectionItem extends MenuItem {

    public CollectionItem(String text, ItemStack icon) {
        super(text, icon);
    }

    public CollectionItem(String text, ItemStack icon, int index) {
        super(text, icon, index);
    }

    @Override
    public void onClick(Player paramPlayer) {

        paramPlayer.sendMessage("Trying to open pvpzone: " + getText());
        paramPlayer.playSound(paramPlayer.getLocation(), Sound.LEVEL_UP, 10.0f, 1.0f);
        getMenu().switchMenu(paramPlayer, Duel.get().getMenuManager().createSelectMenu(ChatColor.stripColor(getText())));

    }
}
