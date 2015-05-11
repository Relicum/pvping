package com.relicum.duel.Menus;

import com.relicum.duel.Duel;
import com.relicum.locations.SpawnPoint;
import com.relicum.pvpcore.Arenas.PvPZone;
import com.relicum.pvpcore.Enums.ArenaState;
import com.relicum.pvpcore.FormatUtil;
import com.relicum.pvpcore.Menus.*;
import com.relicum.pvpcore.Menus.Handlers.CloseMenuHandler;
import com.relicum.utilities.Items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

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

    public ZoneEditMenu(PvPZone zone) {

        super(FormatUtil.colorize("&6&l" + zone.getNameId() + " Editor"), 3);
        this.zone = zone;
        setChanged(true);
        updateFields();

    }

    public ZoneEditMenu(PvPZone zone, int rows) {

        super(FormatUtil.colorize("&6&l" + zone.getNameId() + " Editor"), rows);
        this.zone = zone;
        setChanged(true);
        updateFields();

    }

    public ZoneEditMenu(PvPZone zone, ActionMenu parentMenu) {

        super(FormatUtil.colorize("&6&l" + zone.getNameId() + " Editor"), 3, parentMenu);
        this.zone = zone;
        setChanged(true);
        updateFields();

    }

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
     * <p>
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

    public AbstractItem getSpawnItem(Spawns1v1 spawn, int slot, boolean setState) {


        return null;
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

        AbstractItem lever = new ActionItem(new ItemBuilder(Material.LEVER).setDisplayName("&6&lSpawn Bar")
                .setItemLores(Arrays.asList(" ",
                        "&aClick to open spawn hotbar",
                        "&awhich allows you to set",
                        "&aall spawns at once.")).build(), Slot.ZERO.ordinal(), ClickAction.HOTBAR,
                new ActionHandler() {

                    @Override
                    public ActionHandler getExecutor() {

                        return this;
                    }

                    @Override
                    public ActionResponse perform(Player player, AbstractItem icon, InventoryClickEvent event) {
                        event.setCancelled(true);
                        icon.getMenu().closeMenu(player);
                        new BukkitRunnable() {

                            @Override
                            public void run() {

                                SpawnHotBar bar = new SpawnHotBar(zone, player, Duel.get());
                                bar.saveInventory();
                                player.getInventory().setContents(bar.getItems());
                                player.sendMessage(ChatColor.DARK_AQUA + "Opening Spawn Edit Hotbar");
                                player.updateInventory();

                            }
                        }.runTask(Duel.get());

                        ActionResponse response = new ActionResponse(icon);
                        response.setPlayer(player);
                        response.setDoNothing(true);

                        return response;
                    }
                });

        AbstractItem cm = new ActionItem(new ItemBuilder(Material.GLASS)
                .setDisplayName("&4Close Menu")
                .setItemLores(Arrays.asList(" ", "&bLeft Click to close")).build(), 1, ClickAction.CLOSE_MENU, new ActionHandler() {
            @Override
            public ActionHandler getExecutor() {
                return this;
            }

            @Override
            public ActionResponse perform(Player player, AbstractItem icon, InventoryClickEvent event) {
                event.setCancelled(true);
                player.playSound(player.getLocation(), Sound.CHEST_CLOSE, 10.0f, 1.0f);
                ActionResponse response = new ActionResponse(icon, player);
                response.setWillClose(true);
                return response;

            }
        });

        AbstractItem chg = new ActionItem(new ItemBuilder(Material.SIGN).setDisplayName("&6&lEnable Zone")
                .setItemLores(Arrays.asList(" ", "&aClick to toggle the", "&azone state to &6enable"))
                .build(), Slot.SIX.ordinal(), ClickAction.SWITCH_MENU, new CloseMenuHandler());

        AbstractItem sv = new ActionItem(new ItemBuilder(Material.REDSTONE_BLOCK).setDisplayName("&5&lSave Spawns Settings").setItemLores(Arrays.asList(" ", "&aRight Click to Save"))
                .build(), ((rows * 9) - 2), ClickAction.SAVE, new ActionHandler() {
            @Override
            public ActionHandler getExecutor() {
                return this;
            }

            @Override
            public ActionResponse perform(Player player, AbstractItem icon, InventoryClickEvent event) {
                event.setCancelled(true);
                Duel.get().getZoneManager().saveZone(zone);
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 10.0f, 1.0f);
                player.sendMessage(ChatColor.GOLD + "Zone Successfully saved");
                ActionResponse response = new ActionResponse(icon);
                response.setPlayer(player);
                response.setDoNothing(true);
                return response;
            }
        });

        AbstractItem ed = new ActionItem(new ItemBuilder(Material.INK_SACK)
                .setDisplayName("&6&lToggle Edit Mode")
                .setDurability((short) 10)
                .setItemLores(Arrays.asList(" ", "&aClick to toggle edit", "&amode off, all the time", "&ait is enabled the zone is", "&ausable by players", " ", "&5Toggling will save and", "&5close the menu"))
                .build(), Slot.SEVEN.ordinal(), ClickAction.CONFIG, new ActionHandler() {
            @Override
            public ActionHandler getExecutor() {
                return this;
            }

            @Override
            public ActionResponse perform(Player player, AbstractItem icon, InventoryClickEvent event) {


                if (zone.isEditing()) {
                    zone.setEditing(false);
                    player.sendMessage(ChatColor.GOLD + "Zone editing is now set to false");
                } else {
                    zone.setEditing(true);
                    player.sendMessage(ChatColor.GOLD + "Zone editing is now set to true");
                }
                event.setCancelled(true);
                Duel.get().getZoneManager().saveZone(zone);

                ActionResponse response = new ActionResponse(icon);
                response.setPlayer(player);
                response.setDoNothing(true);
                return response;

            }
        });


        addMenuItem(lever, Slot.ZERO.ordinal());
        setSpawnItem(Spawns1v1.PLAYER_ONE, Slot.ONE);
        setSpawnItem(Spawns1v1.PLAYER_TWO, Slot.TWO);
        setSpawnItem(Spawns1v1.SPECTATOR, Slot.THREE);
        setSpawnItem(Spawns1v1.END, Slot.FOUR);
        addMenuItem(chg, Slot.SIX.ordinal());

        addMenuItem(ed, Slot.SEVEN.ordinal());
        addMenuItem(sv, ((rows * 9) - 2));
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
     *                false.
     */
    public void setChanged(boolean changed) {

        this.changed = changed;
    }

    /**
     * Sets {@link SpawnPointItem} to the specified index and adds it to the
     * menu.
     * <p>
     * This will automatically set the lore and item color and tile to display
     * depending if the SpawnPoint is set or not.
     *
     * @param spawn the {@link Spawns1v1} type to set.
     * @param slot  the slot index {@link Slot}
     */
    public void setSpawnItem(Spawns1v1 spawn, Slot slot) {

        if (check(spawn))

            addMenuItem(new SpawnPointItem(getSetItem(spawn), slot.ordinal(), ClickAction.CONFIG, new SpawnSetHandler()), slot.ordinal());

        else

            addMenuItem(new SpawnPointItem(getUnSetItem(spawn), slot.ordinal(), ClickAction.CONFIG, new SpawnSetHandler()), slot.ordinal());
    }

    /**
     * Check to see if a {@link SpawnPoint} is already set.
     *
     * @param spawn the {@link Spawns1v1} to check for.
     * @return true and it has the spawn set, false if not.
     */
    public boolean check(Spawns1v1 spawn) {

        if (spawn.equals(Spawns1v1.END)) {
            return zone.endSpawnSet();
        } else if (spawn.equals(Spawns1v1.SPECTATOR))

            return zone.spectatorSet();
        else
            return zone.containsSpawn(spawn.getName());
    }

    public ItemStack getSetItem(Spawns1v1 spawn) {

        SpawnPoint p;

        if (spawn.equals(Spawns1v1.END)) {
            p = zone.getEndSpawn();
        } else if (spawn.equals(Spawns1v1.SPECTATOR))
            p = zone.getSpecSpawn();
        else
            p = zone.getSpawn(spawn.getName());

        return new ItemBuilder(Material.INK_SACK).setDisplayName("&6&l" + spawn.getTitle())
                .setDurability((short) 10)
                .setItemLores(Arrays.asList(" ",
                        "&aSpawn Set",
                        " ",
                        "&4Collection: &e" + zone.getName(),
                        "&4Zone: &a" + zone.getNameId(),
                        " ",
                        "&eWorld: &c" + p.getWorld(),
                        "&eX:    " + "  &c" + p.getX(),
                        "&eY:      &c" + p.getY(),
                        "&eZ:      &c" + p.getZ(),
                        "&eYaw:   &c" + p.getYaw(),
                        "&ePitch:  &c" + p.getPitch(),
                        " ",
                        "&bRight Click to Teleport",
                        "&bto this location")).build();

    }

    public ItemStack getUnSetItem(Spawns1v1 spawn) {

        return new ItemBuilder(Material.INK_SACK).setDisplayName("&4&l" + spawn.getTitle())
                .setDurability((short) 8)
                .setItemLores(Arrays.asList(" ", "&aSpawn Not Set ", " ", "&4Collection: &e" + zone.getName(), "&4Zone: &a"
                        + zone.getNameId(), " ", "&bLeft Click to set")).build();
    }

}
