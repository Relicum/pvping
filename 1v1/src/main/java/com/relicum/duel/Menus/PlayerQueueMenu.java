package com.relicum.duel.Menus;

import com.relicum.pvpcore.FormatUtil;
import com.relicum.pvpcore.Menus.ActionMenu;
import com.relicum.utilities.Items.MLore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * Name: PlayerQueueMenu.java Created: 26 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class PlayerQueueMenu extends ActionMenu {


    public PlayerQueueMenu(String title, int rows) {
        super(title, rows);
    }

    public PlayerQueueMenu(String title, int rows, ActionMenu parentMenu) {
        super(title, rows, parentMenu);
    }

    public SkullMeta getSkullMeta(String name, String kit) {

        MLore mLore = new MLore("  \n")
                              .then("&61V1 Invite")
                              .blankLine()
                              .then("&aFrom: &e" + name)
                              .newLine()
                              .then("&aKit: &e" + kit)
                              .blankLine()
                              .then("&bRight Click to accept the 1v1")
                              .newLine()
                              .then("&3Left Click to decline the 1v1");

        String display = "&a&l" + name;

        SkullMeta m1 = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
        m1.setOwner(name);
        m1.setDisplayName(FormatUtil.colorize(display));
        m1.setLore(mLore.toLore());

        return m1;
    }
}
