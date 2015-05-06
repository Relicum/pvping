package com.relicum.pvpcore.Kits;

import com.relicum.pvpcore.FormatUtil;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Name: LobbyHotBar.java Created: 04 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class LobbyHotBar implements InventoryRow {

    private HashMap<Integer, ItemStack> items = new HashMap<>();
    private boolean modifiable = false;

    public LobbyHotBar() {

        setAxe();
        setPaper();
        others();

    }

    private void setAxe() {

        ItemStack stack = new ItemStack(Material.GOLD_AXE, 1);

        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(FormatUtil.colorize("&3&l\u00BB &6&lChallenge a Player &3&l\u00AB"));

        meta.setLore(Arrays.asList(" ", FormatUtil.colorize("&3Right click to challenge"), FormatUtil.colorize("&3a player to a 1v1")));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        stack.setItemMeta(meta);

        addItem(0, stack.clone());

    }

    private void setPaper() {

        ItemStack stack = new ItemStack(Material.PAPER, 1);

        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(FormatUtil.colorize("&3&l\u00BB &e&lView Waiting Requests &3&l\u00AB"));

        meta.setLore(Arrays.asList(" ", FormatUtil.colorize("&3Right Click to see all"), FormatUtil.colorize("&3invites from other players")));
        stack.setItemMeta(meta);

        addItem(2, stack.clone());

    }

    private void others() {

        ItemStack stack = new ItemStack(Material.NETHER_STAR, 1);

        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(FormatUtil.colorize("&3&l\u00BB &a&lJoin Match Queue &3&l\u00AB"));

        meta.setLore(Arrays.asList(" ",
                FormatUtil.colorize("&3Right Click to open queue"),
                FormatUtil.colorize("&3menu where you can select"),
                FormatUtil.colorize("&3from a range of game modes")));
        stack.setItemMeta(meta);

        addItem(4, stack.clone());

        ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);

        BookMeta bmeta = (BookMeta) book.getItemMeta();
        bmeta.setDisplayName(FormatUtil.colorize("&3&l\u00BB &b&lHelp and Rules &3&l\u00AB"));

        bmeta.setLore(Arrays.asList(" ",
                FormatUtil.colorize("&3Right Click to open Help"),
                FormatUtil.colorize("&3Guide which will explain"),
                FormatUtil.colorize("&3all of game modes and"),
                FormatUtil.colorize("&3and the rules for each.")));

        book.setItemMeta(bmeta);

        addItem(6, book.clone());

        ItemStack watch = new ItemStack(Material.NETHER_STAR, 1);

        ItemMeta wmeta = watch.getItemMeta();
        wmeta.setDisplayName(FormatUtil.colorize("&3&l\u00BB &a&lLeave 1v1 Lobby &3&l\u00AB"));

        wmeta.setLore(Arrays.asList(" ",
                FormatUtil.colorize("&3Right Click to leave the"),
                FormatUtil.colorize("&31v1 lobby and go back to"),
                FormatUtil.colorize("&3where you came from")));
        watch.setItemMeta(wmeta);

        addItem(8, watch.clone());

    }

    @Override
    public void addItem(int slot, ItemStack item) {

        Validate.isTrue(slot > -1 && slot < 9, "Slot index must be between 0-9");
        Validate.notNull(item);

        items.put(slot, item);

    }

    @Override
    public ItemStack[] getItems() {

        ItemStack[] tmp = new ItemStack[9];

        for (int i = 0; i < 9; i++) {
            if (!items.containsKey(i))
                tmp[i] = new ItemStack(Material.AIR);
            else
                tmp[i] = items.get(i);
        }

        return tmp.clone();

    }

    @Override
    public boolean isModifiable() {

        return modifiable;

    }

    @Override
    public void setModifiable(boolean mod) {

        modifiable = mod;

    }
}
