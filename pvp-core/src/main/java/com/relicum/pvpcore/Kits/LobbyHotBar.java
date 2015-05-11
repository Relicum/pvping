package com.relicum.pvpcore.Kits;

import com.relicum.pvpcore.FormatUtil;
import com.relicum.pvpcore.Menus.Slot;
import com.relicum.utilities.Items.ItemBuilder;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.Arrays;

/**
 * Name: LobbyHotBar.java Created: 04 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class LobbyHotBar implements InventoryRow {

    private ItemStack[] items = new ItemStack[9];
    private boolean modifiable = false;
    private boolean dropable = false;

    public LobbyHotBar() {

        for (int i = 0; i < 9; i++) {
            items[i] = new ItemStack(Material.AIR, 1);
        }
        setAxe();
        setInvites();
        setQueue();
        setBook();
        setLeave();

    }


    private void setAxe() {

        ItemStack stacks = new ItemBuilder(Material.GOLD_AXE, 1)
                .setDisplayName("&3&l\u00BB &6&lChallenge a Player &3&l\u00AB")
                .setItemLores(Arrays.asList(" ", "&3Right click to challenge", "&3a player to a 1v1"))
                .setItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        addItem(Slot.ZERO, stacks.clone());

    }

    private void setInvites() {

        ItemStack stacks = new ItemBuilder(Material.PAPER, 1)
                .setDisplayName("&3&l\u00BB &e&lView Waiting Requests &3&l\u00AB")
                .setItemLores(Arrays.asList(" ", "&3Right Click to see all", "&3invites from other players"))
                .build();

        addItem(Slot.TWO, stacks.clone());

    }

    private void setQueue() {

        ItemStack stacks = new ItemBuilder(Material.NETHER_STAR, 1)
                .setDisplayName("&3&l\u00BB &a&lJoin Match Queue &3&l\u00AB")
                .setItemLores(Arrays.asList(" ", "&3Right Click to open queue", "&3menu where you can select", "&3from a range of game modes"))
                .build();

        addItem(Slot.FOUR, stacks.clone());
    }

    private void setBook() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);

        BookMeta bmeta = (BookMeta) book.getItemMeta();
        bmeta.setDisplayName(FormatUtil.colorize("&3&l\u00BB &b&lHelp and Rules &3&l\u00AB"));

        bmeta.setLore(Arrays.asList(" ", FormatUtil.colorize("&3Right Click to open Help"), FormatUtil.colorize("&3Guide which will explain"),
                FormatUtil.colorize("&3all of game modes and"), FormatUtil.colorize("&3and the rules for each.")));

        book.setItemMeta(bmeta);

        addItem(Slot.SIX, book.clone());
    }

    private void setLeave() {

        ItemStack stacks = new ItemBuilder(Material.WATCH, 1)
                .setDisplayName("&3&l\u00BB &a&lLeave 1v1 Lobby &3&l\u00AB")
                .setItemLores(Arrays.asList(" ", "&3Right Click to leave the", "&31v1 lobby and go back to", "&3where you came from"))
                .build();

        addItem(Slot.EIGHT, stacks.clone());
    }


    @Override
    public void addItem(Slot slot, ItemStack item) {

        Validate.isTrue(slot.ordinal() > -1 && slot.ordinal() < 9, "Slot index must be between 0-9");
        Validate.notNull(item);

        items[slot.ordinal()] = item;


    }

    @Override
    public ItemStack[] getItems() {

        return items.clone();
    }

    @Override
    public boolean isModifiable() {

        return modifiable;

    }

    @Override
    public void setModifiable(boolean mod) {

        modifiable = mod;

    }

    @Override
    public boolean isDropable() {
        return dropable;
    }

    @Override
    public void setDropable(boolean drop) {
        this.dropable = drop;
    }
}
