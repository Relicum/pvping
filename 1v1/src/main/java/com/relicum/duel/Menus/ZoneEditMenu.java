package com.relicum.duel.Menus;

import com.relicum.duel.Duel;
import com.relicum.locations.SpawnPoint;
import com.relicum.pvpcore.Arenas.PvPZone;
import com.relicum.pvpcore.Enums.ArenaState;
import com.relicum.pvpcore.FormatUtil;
import com.relicum.pvpcore.Menus.AbstractItem;
import com.relicum.pvpcore.Menus.ActionHandler;
import com.relicum.pvpcore.Menus.ActionItem;
import com.relicum.pvpcore.Menus.ActionMenu;
import com.relicum.pvpcore.Menus.ActionResponse;
import com.relicum.pvpcore.Menus.BStack;
import com.relicum.pvpcore.Menus.BooleanItem;
import com.relicum.pvpcore.Menus.ClickAction;
import com.relicum.pvpcore.Menus.MenuAPI;
import com.relicum.pvpcore.Menus.Slot;
import com.relicum.pvpcore.Menus.Spawns1v1;
import com.relicum.utilities.Items.ItemBuilder;
import com.relicum.utilities.Items.MLore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;
import java.util.Arrays;

/**
 * ZoneEditMenu
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



    public void updateFields() {

        this.title = FormatUtil.colorize("&6&l" + zone.getNameId() + " Editor");
        this.getInventory().clear();
        this.getMenuItems().clear();

        updateIcons();
    }

    public void updateIcons() {

        AbstractItem lever = new ActionItem(new ItemBuilder(Material.LEVER)
                                                    .setDisplayName("&6&lSpawn Bar")
                                                    .setItemLores(new MLore(" \n")
                                                                          .then("&aClick to open spawn hotbar")
                                                                          .append("which allows you to set")
                                                                          .append("all spawns at once")
                                                                          .toLore()).build(),
                                            Slot.ZERO.ordinal(), ClickAction.HOTBAR, new ActionHandler() {

            @Override
            public ActionHandler getExecutor() {

                return this;
            }

            @Override
            public ActionResponse perform(Player player, AbstractItem icon, InventoryClickEvent event) {

                if (!icon.getMenu().isModifiable()) {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "Menu is currently not modifiable");
                    ActionResponse response = new ActionResponse(icon, player);
                    response.setDoNothing(true);
                    return response;

                }

                event.setCancelled(true);
                Duel.get().getZoneManager().saveZone(zone);
                icon.getMenu().closeMenu(player);
                new BukkitRunnable() {

                    @Override
                    public void run() {

                        SpawnHotBar bar = new SpawnHotBar(zone.getName(), zone.getNameId(), player, Duel.get());
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
        }
        );

        AbstractItem cm = new ActionItem(new ItemBuilder(Material.GLASS)
                                                 .setDisplayName("&4Close Menu")
                                                 .setItemLores(Arrays.asList(" ", "&bLeft Click to close"))
                                                 .build(), 1, ClickAction.CLOSE_MENU, new ActionHandler() {
            @Override
            public ActionHandler getExecutor() {

                return this;
            }

            @Override
            public ActionResponse perform(Player player, AbstractItem icon, InventoryClickEvent event) {

                event.setCancelled(true);
                player.playSound(player.getLocation(), Sound.CHEST_CLOSE, 10.0f, 1.0f);
                ZoneEditMenu.this.getZone().setEditing(false);
                ZoneEditMenu.this.setModifiable(false);
                ActionResponse response = new ActionResponse(icon, player);
                response.setWillClose(true);
                return response;

            }
        }
        );

        ArenaState as = getZone().getState();
        String st = as.name();

        AbstractItem chg = new ActionItem(new ItemBuilder(Material.SIGN).setDisplayName("&6&lZone State &a&l" + ChatColor.stripColor(st))
                                                  .setItemLores(Arrays.asList(" ", "&aClick to toggle the", "&azone state to &6" + st))
                                                  .build(), Slot.SIX.ordinal(), ClickAction.SWITCH_MENU, new ActionHandler() {
            @Override
            public ActionHandler getExecutor() {
                return this;
            }

            @Override
            public ActionResponse perform(Player player, AbstractItem icon, InventoryClickEvent event) {

                if (!icon.getMenu().isModifiable()) {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "Unable to toggle arena state, menu is not modifiable");
                    ActionResponse response = new ActionResponse(icon, player);
                    response.setDoNothing(true);
                    return response;

                }
                event.setCancelled(true);
                ActionMenu menu = MenuAPI.get().createMenu(FormatUtil.colorize("&6&lSet Zone State"), 1);
                menu.addMenuItem(new CloseItem(8), 8);
                menu.setParentMenu(ZoneEditMenu.this);

                AbstractItem back = new ActionItem(new ItemBuilder(Material.ARROW).setDisplayName("&5&lGo back").build(), 7, ClickAction.SWITCH_MENU, new ActionHandler() {


                    @Override
                    public ActionHandler getExecutor() {
                        return this;
                    }

                    @Override
                    public ActionResponse perform(Player player, AbstractItem icon, InventoryClickEvent event) {

                        event.setCancelled(true);
                        ActionMenu tm = icon.getMenu().getParent();
                        tm.setModifiable(true);
                        tm.setEditing(true);
                        icon.getMenu().switchMenu(player, tm);
                        ActionResponse response = new ActionResponse(icon, player);
                        response.setDoNothing(true);

                        return response;


                    }
                });
                int c = 0;
                for (ArenaState state : ArenaState.values()) {

                    AbstractItem ab = new ActionItem(new ItemBuilder(Material.WOOL).setDisplayName("&b&l" + state.name()).setDurability((short) c).build(),
                                                     c, ClickAction.CONFIG, new ActionHandler() {

                        @Override
                        public ActionHandler getExecutor() {
                            return this;
                        }

                        @Override
                        public ActionResponse perform(Player player, AbstractItem icon, InventoryClickEvent event) {
                            event.setCancelled(true);
                            player.sendMessage("State is " + FormatUtil.colorize(icon.getText()));
                            ItemStack stack = new ItemBuilder(Material.SIGN)
                                                      .setDisplayName("&6&lZone State &a&l" + ChatColor.stripColor(icon.getText()))
                                                      .setItemLores(Arrays.asList(" ", "&aClick to toggle the", "&azone state"))
                                                      .build();


                            AbstractItem item = ZoneEditMenu.this.items.get(6);
                            item.setIcon(stack.clone());
                            ZoneEditMenu.this.removeMenuItem(6);
                            ZoneEditMenu.this.addMenuItem(item, 6);
                            ZoneEditMenu.this.getInventory().setItem(6, item.getItem());
                            ZoneEditMenu.this.getZone().setState(ArenaState.valueOf(ChatColor.stripColor(icon.getText())));
                            player.sendMessage(ChatColor.GREEN + "Zone State set to " + icon.getText());
                            Duel.get().getZoneManager().saveZone(ZoneEditMenu.this.getZone());

                            ActionResponse response = new ActionResponse(icon, player);
                            response.setDoNothing(true);

                            return response;

                        }
                    });
                    menu.addMenuItem(ab, c);
                    c++;
                }

                menu.addMenuItem(back, 7);
                ActionResponse response = new ActionResponse(icon, player);
                response.setDoNothing(true);

                icon.getMenu().closeMenu(player);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        menu.setEditing(true);
                        menu.openMenuForEditing(player);

                    }
                }.runTask(Duel.get());
                return response;

            }
        }
        );

        AbstractItem sv = new ActionItem(new ItemBuilder(Material.REDSTONE_BLOCK).setDisplayName("&5&lSave Spawns Settings")
                                                 .setItemLores(Arrays.asList(" ", "&aRight Click to Save"))
                                                 .build(), ((rows * 9) - 2), ClickAction.SAVE, new ActionHandler() {
            @Override
            public ActionHandler getExecutor() {

                return this;
            }

            @Override
            public ActionResponse perform(Player player, AbstractItem icon, InventoryClickEvent event) {

                if (!icon.getMenu().isModifiable()) {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "Unable to save, menu is not modifiable");
                    ActionResponse response = new ActionResponse(icon, player);
                    response.setDoNothing(true);
                    return response;

                }

                event.setCancelled(true);
                ZoneEditMenu.this.setModifiable(false);
                Duel.get().getZoneManager().saveZone(zone);
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 10.0f, 1.0f);
                player.sendMessage(ChatColor.GOLD + "Zone Successfully saved");
                ActionResponse response = new ActionResponse(icon);
                response.setPlayer(player);
                response.setDoNothing(true);
                return response;
            }
        }
        );


        MLore fdis;
        MLore fen;
        fdis = new MLore(" \n")
                       .then("&aClick to enable this zone")
                       .blankLine()
                       .then("&bLeft click enable");
        fen = new MLore(" \n")
                      .then("&aClick to disable this zone")
                      .blankLine()
                      .then("&bLeft click to disable");

        BooleanItem enab = new BooleanItem(new BStack("&a&lZone Enabled", fen.toLore()),
                                           new BStack("&4&lZone Disabled", fdis.toLore()),
                                           Slot.SEVEN.ordinal(), getZone().isEnabled(), ClickAction.CONFIG, new ActionHandler() {
            @Override
            public ActionHandler getExecutor() {
                return this;
            }

            @Override
            public ActionResponse perform(Player player, AbstractItem icon, InventoryClickEvent event) {

                if (!icon.getMenu().isModifiable()) {
                    event.setCancelled(true);
                    if (zone.isEnabled()) {
                        player.sendMessage(ChatColor.RED + "Unable to disable menu, menu is not modifiable");
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "Unable to enable menu, menu is not modifiable");
                    }
                    ActionResponse response = new ActionResponse(icon, player);
                    response.setDoNothing(true);
                    return response;

                }

                BooleanItem b = (BooleanItem) icon;

                event.setCancelled(true);

                if (zone.isEnabled()) {
                    zone.setEnable(false);
                    b.setActive(false);
                    icon.setIcon(b.updateIcon());
                    removeMenuItem(icon.getIndex());
                    addMenuItem(icon, icon.getIndex());
                    getInventory().setItem(icon.getIndex(), icon.getItem());
                    player.sendMessage(ChatColor.GOLD + "You have now disabled Zone " + icon.getText());
                }
                else {
                    if (zone.canBeEnabled()) {
                        zone.setEnable(true);
                        b.setActive(true);
                        icon.setIcon(b.updateIcon());
                        removeMenuItem(icon.getIndex());
                        addMenuItem(icon, icon.getIndex());
                        getInventory().setItem(icon.getIndex(), icon.getItem());
                        player.sendMessage(ChatColor.GOLD + "You have now enabled Zone " + icon.getText());
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "Unable to enable this zone all spawns need to be set first");
                        ActionResponse response = new ActionResponse(icon, player);
                        response.setDoNothing(true);
                        return response;
                    }
                }

                Duel.get().getZoneManager().saveZone(zone);
                ActionResponse response = new ActionResponse(icon, player);
                response.setWillUpdate(true);
                return response;
            }
        });

        MLore foff;
        MLore fon;
        foff = new MLore(" \n")
                       .then("&aClick to toggle this menu into edit mode until you do that you will not be able to edit the menu")
                       .blankLine()
                       .then("&bLeft click to toggle on");
        fon = new MLore(" \n")
                      .then("&aClick to toggle edit mode to off")
                      .blankLine()
                      .then("&bLeft click to toggle off");


        BooleanItem bl = new BooleanItem(new BStack("&a&lEdit Mode Enabled", fon.toLore()),
                                         new BStack("&4&lEdit Mode Disabled", foff.toLore()),
                                         Slot.EIGHT.ordinal(), getZone().isEditing(), ClickAction.CONFIG, new ActionHandler() {
            @Override
            public ActionHandler getExecutor() {
                return this;
            }

            @Override
            public ActionResponse perform(Player player, AbstractItem icon, InventoryClickEvent event) {

                BooleanItem b = (BooleanItem) icon;

                event.setCancelled(true);
                if (zone.isEditing()) {
                    b.setActive(false);
                    icon.setIcon(b.updateIcon());
                    removeMenuItem(icon.getIndex());
                    addMenuItem(icon, icon.getIndex());
                    getInventory().setItem(icon.getIndex(), icon.getItem());
                    zone.setEditing(false);
                    ZoneEditMenu.this.setModifiable(false);
                    player.sendMessage(ChatColor.GOLD + "You have now disabled Zone editing.");
                }
                else {
                    b.setActive(true);
                    icon.setIcon(b.updateIcon());
                    removeMenuItem(icon.getIndex());
                    addMenuItem(icon, icon.getIndex());
                    getInventory().setItem(icon.getIndex(), icon.getItem());
                    zone.setEditing(true);
                    ZoneEditMenu.this.setModifiable(true);
                    player.sendMessage(ChatColor.GOLD + "You have enabled Zone editing");
                }

                Duel.get().getZoneManager().saveZone(zone);
                ActionResponse response = new ActionResponse(icon);
                response.setPlayer(player);
                response.setWillUpdate(true);
                return response;

            }

        });


        addMenuItem(lever, Slot.ZERO.ordinal());
        setSpawnItem(Spawns1v1.PLAYER_ONE, Slot.ONE);
        setSpawnItem(Spawns1v1.PLAYER_TWO, Slot.TWO);
        setSpawnItem(Spawns1v1.SPECTATOR, Slot.THREE);
        setSpawnItem(Spawns1v1.END, Slot.FOUR);

        addMenuItem(chg, Slot.SIX.ordinal());
        addMenuItem(enab, Slot.SEVEN.ordinal());
        addMenuItem(bl, Slot.EIGHT.ordinal());
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

        {
            addMenuItem(new SpawnPointItem(getSetItem(spawn), slot.ordinal(), ClickAction.CONFIG, new SpawnSetHandler()), slot.ordinal());
        }

        else

        {
            addMenuItem(new SpawnPointItem(getUnSetItem(spawn), slot.ordinal(), ClickAction.CONFIG, new SpawnSetHandler()), slot.ordinal());
        }
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
        }
        else if (spawn.equals(Spawns1v1.SPECTATOR))

        {
            return zone.spectatorSet();
        }
        else {
            return zone.containsSpawn(spawn.getName());
        }
    }

    public ItemStack getSetItem(@Nullable Spawns1v1 spawn) {

        SpawnPoint p;

        if (spawn == Spawns1v1.END) {
            p = zone.getEndSpawn();
        }
        else if (spawn == Spawns1v1.SPECTATOR) {
            p = zone.getSpecSpawn();
        }
        else {
            p = zone.getSpawn(spawn != null ? spawn.getName() : null);
        }


        return new ItemBuilder(Material.INK_SACK)
                       .setDisplayName("&6&l" + (spawn != null ? spawn.getTitle() : null))
                       .setDurability((short) 10)
                       .setItemLores(Arrays.asList(" ", "&aSpawn Set", " ", "&4Collection: &e" + zone.getName(), "&4Zone: &a" + zone.getNameId(), " ",
                                                   "&eWorld: &c" + p.getWorld(), "&eX:      &c" + p.getX(), "&eY:      &c" + p.getY(), "&eZ:      &c" + p.getZ(),
                                                   "&eYaw:   &c" + p.getYaw(), "&ePitch:  &c" + p.getPitch(), " ", "&bRight Click to Teleport", "&bto this location"))
                       .build();

    }

    public ItemStack getUnSetItem(Spawns1v1 spawn) {

        return new ItemBuilder(Material.INK_SACK)
                       .setDisplayName("&4&l" + spawn.getTitle())
                       .setDurability((short) 8)
                       .setItemLores(Arrays.asList(" ", "&aSpawn Not Set ", " ",
                                                   "&4Collection: &e" + zone.getName(),
                                                   "&4Zone: &a" + zone.getNameId(), " ",
                                                   "&bLeft " + "Click to set")).build();
    }

}
