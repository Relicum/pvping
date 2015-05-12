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
 * Holds the interactive items players use when in the lobby.
 */
public class LobbyHotBar {

    /**
     * The Items that make up the hot bar.
     */
    private ItemStack[] items = new ItemStack[9];


    private LobbyHotBar() {

    }

    private LobbyHotBar(boolean doInit) {

        if (doInit)
            init();

    }


    private void init() {
        for (int i = 0; i < 9; i++) {
            items[i] = new ItemStack(Material.AIR, 1);
        }
        setAxe();
        setInvites();
        setQueue();
        setBook();
        setLeave();
    }

    /**
     * Create and return new instance of {@link LobbyHotBar}.
     *
     * @return the {@link LobbyHotBar}
     */
    public static LobbyHotBar create() {
        return new LobbyHotBar(true);
    }


    /**
     * Sets axe.
     */
    public void setAxe() {

        ItemStack stacks = new ItemBuilder(Material.GOLD_AXE, 1)
                .setDisplayName("&3&l\u00BB &6&lChallenge a Player &3&l\u00AB")
                .setItemLores(Arrays.asList(" ", "&3Right click to challenge", "&3a player to a 1v1"))
                .setItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        addItem(Slot.ZERO, stacks.clone());

    }

    /**
     * Sets invites.
     */
    public void setInvites() {

        ItemStack stacks = new ItemBuilder(Material.PAPER, 1)
                .setDisplayName("&3&l\u00BB &e&lView Waiting Requests &3&l\u00AB")
                .setItemLores(Arrays.asList(" ", "&3Right Click to see all", "&3invites from other players"))
                .build();

        addItem(Slot.TWO, stacks.clone());

    }

    /**
     * Sets queue.
     */
    public void setQueue() {

        ItemStack stacks = new ItemBuilder(Material.NETHER_STAR, 1)
                .setDisplayName("&3&l\u00BB &a&lJoin Match Queue &3&l\u00AB")
                .setItemLores(Arrays.asList(" ", "&3Right Click to open queue", "&3menu where you can select", "&3from a range of game modes"))
                .build();

        addItem(Slot.FOUR, stacks.clone());
    }

    /**
     * Sets book.
     */
    public void setBook() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);

        BookMeta bmeta = (BookMeta) book.getItemMeta();
        bmeta.setDisplayName(FormatUtil.colorize("&3&l\u00BB &b&lHelp and Rules &3&l\u00AB"));

        bmeta.setLore(Arrays.asList(" ", FormatUtil.colorize("&3Right Click to open Help"), FormatUtil.colorize("&3Guide which will explain"),
                FormatUtil.colorize("&3all of game modes and"), FormatUtil.colorize("&3and the rules for each.")));

        book.setItemMeta(bmeta);

        addItem(Slot.SIX, book.clone());
    }

    /**
     * Sets leave.
     */
    public void setLeave() {

        ItemStack stacks = new ItemBuilder(Material.WATCH, 1)
                .setDisplayName("&3&l\u00BB &a&lLeave 1v1 Lobby &3&l\u00AB")
                .setItemLores(Arrays.asList(" ", "&3Right Click to leave the", "&31v1 lobby and go back to", "&3where you came from"))
                .build();

        addItem(Slot.EIGHT, stacks.clone());
    }


    /**
     * Add an {@link ItemStack} to the {@link LobbyHotBar}
     *
     * @param slot the {@link Slot} the position of the item on the {@link LobbyHotBar}
     * @param item the {@link ItemStack} to add to the {@link LobbyHotBar}
     */
    public void addItem(Slot slot, ItemStack item) {

        Validate.isTrue(slot.ordinal() > -1 && slot.ordinal() < 9, "Slot index must be between 0-9");
        Validate.notNull(item);

        items[slot.ordinal()] = item;


    }

    /**
     * Ge an array of {@link ItemStack} which is added to the {@link org.bukkit.entity.Player} hotbar.
     * <p>This will always have a length of 9 any empty slots will be filled with air.
     *
     * @return the array of {@link ItemStack[]} to add to the hot bar.
     */
    public ItemStack[] getItems() {

        return items.clone();
    }

}
