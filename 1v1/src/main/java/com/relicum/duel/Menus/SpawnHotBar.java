package com.relicum.duel.Menus;

import com.relicum.duel.Duel;
import com.relicum.locations.SpawnPoint;
import com.relicum.pvpcore.Arenas.PvPZone;
import com.relicum.pvpcore.Kits.InventoryRow;
import com.relicum.pvpcore.Menus.Slot;
import com.relicum.pvpcore.Menus.Spawns1v1;
import com.relicum.utilities.Items.ItemBuilder;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;


/**
 * SpawnHotBar
 *
 * @author Relicum
 * @version 0.0.1
 */
@SuppressFBWarnings({"NOISE_METHOD_CALL"})
public class SpawnHotBar implements InventoryRow, Listener {

    private ItemStack[] items = new ItemStack[9];
    private ItemStack[] contents = new ItemStack[36];
    private boolean modifiable = false;
    private boolean dropable = false;
    private PvPZone zone;
    private Player player;


    public SpawnHotBar(String name, String nameId, Player player, Duel plug) {

        Validate.notNull(name);
        Validate.notNull(nameId);
        zone = plug.getZoneManager().getPvpZone(name, nameId);
        if (!player.hasPermission("duel.admin.modify")) {
            player.sendMessage(ChatColor.RED + "Error: you do not have the permission");
            return;
        }
        this.player = player;

        plug.getServer().getPluginManager().registerEvents(this, plug);

        for (int i = 0; i < 9; i++) {
            items[i] = new ItemStack(Material.AIR, 1);
        }
        zone.setEditing(true);
        refresh();
        setCloseItem();

    }

    public void refresh() {

        setSpawn1();
        setSpawn2();
        setSpectator();
        setEnd();
        setGoback();
        setSaveItem();
    }

    public PvPZone getZone() {

        return zone;
    }

    public Player getPlayer() {

        return player;
    }

    public void saveInventory() {

        this.contents = this.player.getInventory().getContents().clone();

    }

    public void restoreInventory() {

        player.getInventory().setContents(contents);
        player.updateInventory();
    }

    public void setSpawn1() {

        if (zone.containsSpawn(Spawns1v1.PLAYER_ONE.getName())) {
            addItem(Slot.ZERO, getSetItem(Spawns1v1.PLAYER_ONE));
        }
        else {
            addItem(Slot.ZERO, getUnSetItem(Spawns1v1.PLAYER_ONE));
        }
    }

    public void setSpawn2() {

        if (zone.containsSpawn(Spawns1v1.PLAYER_TWO.getName())) {
            addItem(Slot.ONE, getSetItem(Spawns1v1.PLAYER_TWO));
        }
        else {
            addItem(Slot.ONE, getUnSetItem(Spawns1v1.PLAYER_TWO));
        }
    }

    public void setSpectator() {

        if (zone.spectatorSet()) {
            addItem(Slot.TWO, getSetItem(Spawns1v1.SPECTATOR));
        }
        else {
            addItem(Slot.TWO, getUnSetItem(Spawns1v1.SPECTATOR));
        }
    }

    public void setEnd() {

        if (zone.endSpawnSet()) {
            addItem(Slot.THREE, getSetItem(Spawns1v1.END));
        }
        else {
            addItem(Slot.THREE, getUnSetItem(Spawns1v1.END));
        }
    }

    public void setGoback() {
        addItem(Slot.SIX, new ItemBuilder(Material.ARROW)
                                  .setDisplayName("&6&lGo Back To Menu")
                                  .setItemLores(Arrays.asList(" ", "&aRight click to open menu"))
                                  .build());
    }

    public void goBackTo() {

        ZoneEditMenu tmpm = Duel.get().getMenuManager().getZoneEditMenu(zone);
        tmpm.setModifiable(true);
        tmpm.setEditing(true);
        unregisterListeners();
        restoreInventory();
        new BukkitRunnable() {
            @Override
            public void run() {

                tmpm.openMenu(player);

            }
        }.runTask(Duel.get());

    }

    public void setSaveItem() {

        addItem(Slot.SEVEN,
                new ItemBuilder(Material.REDSTONE_BLOCK).setDisplayName("&5&lSave Spawns Settings").setItemLores(Arrays.asList(" ", "&aRight Click to " +
                                                                                                                                            "Save"))
                        .build()
        );
    }

    public void setCloseItem() {

        addItem(Slot.EIGHT, new ItemBuilder(Material.GLASS).setDisplayName("&5&lClose Spawn Bar").setItemLores(Arrays.asList(" ", "&aRight Click to Close"))
                                    .build()
        );
    }

    public void save() {
        zone.setEditing(false);
        Duel.get().getZoneManager().saveZone(zone);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addItem(Slot slot, ItemStack item) {

        Validate.notNull(item);
        Validate.isTrue(slot.ordinal() > -1 && slot.ordinal() < 9, "Slot index must be between 0-9");

        items[slot.ordinal()] = item.clone();
    }

    public ItemStack getSetItem(Spawns1v1 spawn) {

        SpawnPoint p;
        if (spawn.equals(Spawns1v1.END)) {
            p = zone.getEndSpawn();
        }
        else if (spawn.equals(Spawns1v1.SPECTATOR)) {
            p = zone.getSpecSpawn();
        }
        else {
            p = zone.getSpawn(spawn.getName());
        }
        return new ItemBuilder(Material.INK_SACK)
                       .setDisplayName("&6&l" + spawn.getTitle())
                       .setDurability((short) 10)
                       .setItemLores(
                                            Arrays.asList(" ", "&aSpawn Set", " ", "&4Collection: &e" + zone.getName(), "&4Zone: &a" + zone.getNameId(), " ",
                                                          "&eWorld: &c" + p.getWorld(), "&eX:    " + "  &c" + p.getX(), "&eY:      &c" + p.getY(), "&eZ:      &c" +
                                                                                                                                                           p.getZ(),
                                                          "&eYaw:   &c" + p.getYaw(), "&ePitch:  &c" + p.getPitch(), " ", "&bSneak and Right Click", "&bto teleport" +
                                                                                                                                                             " to " +
                                                                                                                                                             "this",
                                                          "&blocation"
                                            )
                       ).build();

    }

    public ItemStack getUnSetItem(Spawns1v1 spawn) {

        return new ItemBuilder(Material.INK_SACK)
                       .setDisplayName("&4&l" + spawn.getTitle())
                       .setDurability((short) 8)
                       .setItemLores(
                                            Arrays.asList(" ", "&aSpawn Not Set ", " ", "&4Collection: &e" + zone.getName(), "&4Zone: &a" + zone.getNameId(),
                                                          " ",
                                                          "&bRight Click to set"
                                            )
                       ).build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemStack[] getItems() {

        return items.clone();
    }

    public ItemStack getItem(Slot slot) {

        return items[slot.ordinal()];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isModifiable() {

        return modifiable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setModifiable(boolean mod) {

        this.modifiable = mod;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDropable() {

        return dropable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDropable(boolean drop) {

        this.dropable = drop;
    }

    @EventHandler
    public void onInterAct(PlayerInteractEvent event) {

        if (!event.getPlayer().hasPermission("duel.admin.modify")) {
            player.sendMessage(ChatColor.RED + "Error: you do not have the permission");
            event.setCancelled(true);
            return;
        }

        if (event.getItem() == null) {
            return;
        }
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if (event.getPlayer().getUniqueId().equals(player.getUniqueId())) {
                event.setCancelled(true);
                int sl = event.getPlayer().getInventory().getHeldItemSlot();
                boolean shifting = event.getPlayer().isSneaking();
                if (sl > -1 && sl < 9) {
                    if (sl == 8) {
                        closeIt();
                    }
                    else {

                        if (sl == 0) {
                            if (shifting && event.getItem().getDurability() == (short) 10) {

                                teleport(Spawns1v1.PLAYER_ONE);

                            }
                            else if (!shifting) {
                                zone.addSpawn(Spawns1v1.PLAYER_ONE.getName(), new SpawnPoint(event.getPlayer().getLocation()));
                                setSpawn1();
                                event.getPlayer().getInventory().setItem(0, getItem(Slot.ZERO));
                                event.getPlayer().updateInventory();
                                event.getPlayer().sendMessage(ChatColor.GOLD + "Spawn set for Spawn1");
                            }
                        }
                        else if (sl == 1) {
                            if (shifting && event.getItem().getDurability() == (short) 10) {

                                teleport(Spawns1v1.PLAYER_TWO);

                            }
                            else if (!shifting) {
                                zone.addSpawn(Spawns1v1.PLAYER_TWO.getName(), new SpawnPoint(event.getPlayer().getLocation()));
                                setSpawn2();
                                event.getPlayer().getInventory().setItem(1, getItem(Slot.ONE));
                                event.getPlayer().updateInventory();
                                event.getPlayer().sendMessage(ChatColor.GOLD + "Spawn set for Spawn2");
                            }
                        }
                        else if (sl == 2) {
                            if (shifting && event.getItem().getDurability() == (short) 10) {

                                teleport(Spawns1v1.SPECTATOR);

                            }
                            else if (!shifting) {
                                zone.setSpecSpawn(new SpawnPoint(event.getPlayer().getLocation()));
                                setSpectator();
                                event.getPlayer().getInventory().setItem(2, getItem(Slot.TWO));
                                event.getPlayer().updateInventory();
                                event.getPlayer().sendMessage(ChatColor.GOLD + "Spawn set for Spectators");
                            }
                        }
                        else if (sl == 3) {
                            if (shifting && event.getItem().getDurability() == (short) 10) {

                                teleport(Spawns1v1.END);

                            }
                            else if (!shifting) {
                                zone.setEndSpawn(new SpawnPoint(event.getPlayer().getLocation()));
                                setEnd();
                                event.getPlayer().getInventory().setItem(3, getItem(Slot.THREE));
                                event.getPlayer().updateInventory();
                                event.getPlayer().sendMessage(ChatColor.GOLD + "Spawn set for end game");
                            }
                        }
                        else if (sl == 7) {
                            event.getPlayer().sendMessage(ChatColor.DARK_AQUA + "Saving Zone.....");
                            save();
                            closeIt();
                        }
                        else if (sl == 6) {
                            goBackTo();
                        }
                        else {
                            event.getPlayer().sendMessage(ChatColor.GREEN + "Slot was in the hotbar " + sl);
                            event.getPlayer().sendMessage(ChatColor.GREEN + "You used a " + event.getItem().getType().name());
                        }
                    }
                }

            }
        }
    }

    public void teleport(Spawns1v1 spawn) {

        player.closeInventory();
        player.playSound(player.getLocation(), Sound.NOTE_PIANO, 10.0f, 1.0f);
        player.sendMessage(ChatColor.GREEN + "Teleported to " + ChatColor.GOLD + zone.getNameId() + " " + ChatColor.YELLOW + spawn.getTitle());

        if (spawn == Spawns1v1.END) {

            player.teleport(zone.getEndSpawn().toLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);

        }
        else if (spawn == Spawns1v1.SPECTATOR) {

            player.teleport(zone.getSpecSpawn().toLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);

        }
        else {

            player.teleport(zone.getSpawn(spawn.getName()).toLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);

        }
    }


    @EventHandler
    public void noDrop(PlayerDropItemEvent event) {

        if (event.getPlayer().getUniqueId().equals(player.getUniqueId())) {
            if (!isDropable()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void noPick(PlayerPickupItemEvent event) {

        if (event.getPlayer().getUniqueId().equals(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    public void closeIt() {

        unregisterListeners();
        restoreInventory();
        items = null;
        contents = null;
        zone = null;
        player.sendMessage(ChatColor.GOLD + "Everything is closing doing");
        // player=null;
    }

    public void unregisterListeners() {

        PlayerInteractEvent.getHandlerList().unregister(this);
        PlayerDropItemEvent.getHandlerList().unregister(this);
        PlayerPickupItemEvent.getHandlerList().unregister(this);
    }
}
