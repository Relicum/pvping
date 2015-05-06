package com.relicum.duel.Menus;

import com.relicum.pvpcore.Menus.MenuItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.List;

/**
 * Name: StandardItem.java Created: 04 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class StandardItem extends MenuItem {

    public StandardItem(ItemStack icon) {
        super(icon);
    }

    public StandardItem(String text) {
        super(text);
    }

    public StandardItem(String text, ItemStack icon) {
        super(text, icon);
    }

    public StandardItem(String text, ItemStack icon, int index) {
        super(text, icon, index);
    }

    public StandardItem(String text, ItemStack icon, int index, List<String> desc) {
        super(text, icon, index, desc);
    }

    @Override
    public void onClick(Player paramPlayer) {

        getMenu().closeMenu(paramPlayer);

        paramPlayer.getWorld().createExplosion(paramPlayer.getLocation().getX(),
                paramPlayer.getLocation().getY(),
                paramPlayer.getLocation().getZ(),
                2.0f,
                false,
                false);

    }
}
