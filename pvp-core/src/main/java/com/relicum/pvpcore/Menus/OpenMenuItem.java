package com.relicum.pvpcore.Menus;

import org.bukkit.Material;
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
        paramPlayer.sendMessage("Openmenu on click has fired");
        Menu menu = MenuAPI.get().createMenu(getText(), 1);

        MenuItem item = new MenuItem(getText(), new ItemStack(Material.TNT), 0) {

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
        };
        menu.addMenuItem(item, 0);
        menu.addMenuItem(new CloseMenuItem(), 8);
        menu.setExitOnClickOutside(true);

        getMenu().switchMenu(paramPlayer, menu);

    }
}
