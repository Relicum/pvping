package com.relicum.pvpcore.Menus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.Arrays;

/**
 * CloseMenuItem a fixed item used for menu close icon.
 *
 * @author Relicum
 * @version 0.0.1
 */
public class CloseMenuItem extends FixedMenuItem {

    public CloseMenuItem() {
        super(ChatColor.DARK_RED + "Close Menu", new ItemStack(Material.GLASS, 1), 8, Arrays.asList(" ", "&bClick to close the menu"));

    }

    @Override
    public void onClick(Player paramPlayer) {

        paramPlayer.playSound(paramPlayer.getLocation(), Sound.CHEST_CLOSE, 10.0f, 1.0f);
        getMenu().closeMenu(paramPlayer);
    }
}
