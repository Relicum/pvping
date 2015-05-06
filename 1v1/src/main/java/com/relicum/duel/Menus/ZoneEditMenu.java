package com.relicum.duel.Menus;

import com.relicum.pvpcore.Arenas.PvPZone;
import com.relicum.pvpcore.Enums.ArenaState;
import com.relicum.pvpcore.FormatUtil;
import com.relicum.pvpcore.Menus.*;
import com.relicum.pvpcore.Menus.Handlers.CloseMenuHandler;
import com.relicum.pvpcore.Menus.Handlers.TeleportHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Arrays;

/**
 * Name: ZoneEditMenu.java Created: 04 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class ZoneEditMenu extends ActionMenu {

    private PvPZone zone;
    private boolean changed = false;

    public ZoneEditMenu(String text, int rows) {

        super(text, rows);
    }

    public ZoneEditMenu(String text, int rows, ActionMenu parentMenu) {

        super(text, rows, parentMenu);
    }

    /**
     * Gets the current {@link PvPZone} being edited.
     *
     * @return the {@link PvPZone}
     */
    public PvPZone getZone() {

        return zone;
    }

    /**
     * Sets a new {@link PvPZone} to be edited, this will cuase all previous
     * menu setting to be cleared and updated to reflect the new {@link PvPZone}
     * 
     * <p>
     * You need to set the {@link PvPZone} that you want to edit, including the
     * first time this object is created.
     *
     * @param zone the {@link PvPZone} that you wish to edit.
     */
    public void setZone(PvPZone zone) {

        this.zone = zone;
        setChanged(true);
        updateFields();

    }

    public void updateFields() {

        this.title = FormatUtil.colorize("&6&l" + zone.getNameId() + " Editor");
        this.getInventory().clear();
        this.getMenuItems().clear();

        zone.setEditing(true);
        zone.setState(ArenaState.SETUP);
        updateIcons();
    }

    public void updateIcons() {
        ItemStack tp = new ItemStack(Material.SIGN, 1);
        ItemMeta meta = tp.getItemMeta();
        meta.setDisplayName(FormatUtil.colorize("&a&lTeleport to Spawn"));
        meta.setLore(Arrays.asList(" ", "Teleport back to the world s spawn"));
        tp.setItemMeta(meta);
        AbstractItem<TeleportHandler> ai = new ActionItem<>(tp, 1, ClickAction.TELEPORT, new TeleportHandler() {

            @Override
            public ActionResponse perform(Player player, AbstractItem<TeleportHandler> icon) {

                ActionResponse response = new ActionResponse(icon);
                response.setPlayer(player);
                response.setWillClose(true);
                player.playSound(player.getLocation(), Sound.FIREWORK_LARGE_BLAST2, 10.0f, 1.0f);
                player.teleport(getLocation());
                return response;
            }
        });

        AbstractItem<CloseMenuHandler> cm = new ActionItem<>(new ItemStack(Material.GLASS), 1, ClickAction.CLOSE_MENU, new CloseMenuHandler());

        ai.getActionHandler().getExecutor().setLocation(Bukkit.getWorld("world").getSpawnLocation());

        addMenuItem(ai, 0);
        addMenuItem(cm, ((rows * 9) - 1));

    }

    @Override
    public Inventory getInventory() {

        if (isChanged() || this.inventory == null) {

            this.inventory = Bukkit.createInventory(this, this.rows * 9, this.title);
            setChanged(false);
        }

        return this.inventory;
    }

    /**
     * Get if the {@link PvPZone} has changed, so requiring internal updates to
     * be done.
     * <p>
     * This is different from {@link #updateMenu()} which is used to update the
     * inventory the player is viewing.
     *
     * @return true and the {@link PvPZone} has changed, false and it has not.
     */
    public boolean isChanged() {

        return changed;
    }

    /**
     * Sets field changed if a new {@link PvPZone} has been set.
     *
     * @param changed set true if new {@link PvPZone} is set. Default is set to
     *        false.
     */
    public void setChanged(boolean changed) {

        this.changed = changed;
    }
}
