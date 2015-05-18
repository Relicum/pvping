package com.relicum.duel.Menus;

import com.relicum.duel.Duel;
import com.relicum.locations.SpawnPoint;
import com.relicum.pvpcore.Arenas.PvPZone;
import com.relicum.pvpcore.Enums.ArenaType;
import com.relicum.pvpcore.Menus.AbstractItem;
import com.relicum.pvpcore.Menus.ActionHandler;
import com.relicum.pvpcore.Menus.ActionResponse;
import com.relicum.pvpcore.Menus.Spawns1v1;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Name: SpawnSetHandler.java Created: 06 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class SpawnSetHandler implements ActionHandler {

    @Override
    public SpawnSetHandler getExecutor() {

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
        boolean rightClick = event.isRightClick();
        event.setCancelled(true);
        ZoneEditMenu am = (ZoneEditMenu) icon.getMenu();


        PvPZone zone = am.getZone();
        Spawns1v1 sp = Spawns1v1.fromTitle(ChatColor.stripColor(icon.getText()));


        if (zone.getArenaType().equals(ArenaType.ARENA1v1)) {
            sp = Spawns1v1.fromTitle(ChatColor.stripColor(icon.getText()));

            if (rightClick) {
                if (sp == (Spawns1v1.END)) {
                    if (zone.endSpawnSet()) {

                        closeAndReOpen(player, am);
                        player.teleport(zone.getEndSpawn().toLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);

                    }
                    else {

                        player.sendMessage(ChatColor.RED + "Unable to teleport, " + sp.getTitle() + " not set");

                    }
                }
                else if (sp == Spawns1v1.SPECTATOR) {
                    if (zone.spectatorSet()) {

                        closeAndReOpen(player, am);
                        player.teleport(zone.getSpecSpawn().toLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);

                    }
                    else {

                        player.sendMessage(ChatColor.RED + "Unable to teleport, " + sp.getTitle() + " not set");

                    }
                }
                else {

                    if (zone.containsSpawn(sp.getName())) {

                        closeAndReOpen(player, am);
                        player.teleport(zone.getSpawn(sp.getName()).toLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);

                    }
                    else {

                        player.sendMessage(ChatColor.RED + "Unable to teleport, " + sp.getTitle() + " not set");

                    }
                }

                zone.setEditing(false);
                ActionResponse response = new ActionResponse(icon, player);
                response.setWillClose(true);

                return response;
            }


            if (sp == (Spawns1v1.PLAYER_ONE)) {

                am.getZone().addSpawn(Spawns1v1.PLAYER_ONE.getName(), new SpawnPoint(player.getLocation()));
            }
            else if (sp == Spawns1v1.PLAYER_TWO) {
                am.getZone().addSpawn(Spawns1v1.PLAYER_TWO.getName(), new SpawnPoint(player.getLocation()));
            }
            else if (sp == Spawns1v1.SPECTATOR) {
                zone.setSpecSpawn(new SpawnPoint(player.getLocation()));
            }
            else if (sp == Spawns1v1.END) {
                zone.setEndSpawn(new SpawnPoint(player.getLocation()));
            }
        }

        SpawnPointItem item = (SpawnPointItem) icon;

        if (!item.isState()) {

            am.removeMenuItem(icon.getIndex());
            am.addMenuItem(item, item.getIndex());

            am.getInventory().setItem(item.getIndex(), am.getSetItem(sp));
            item.setState(true);

            player.sendMessage("You have successfully set " + item.getText());
        }
        else {
            player.sendMessage("You have successfully updated " + item.getText());
        }

        Duel.get().getZoneManager().saveZone(am.getZone());

        ActionResponse response = new ActionResponse(item);
        response.setPlayer(player);
        response.setWillUpdate(true);

        return response;
    }

    public void closeAndReOpen(Player player, ZoneEditMenu menu) {

        menu.closeMenu(player);
        player.playSound(player.getLocation(), Sound.NOTE_PIANO, 10.0f, 1.0f);

        new BukkitRunnable() {
            @Override
            public void run() {

                menu.setEditing(true);
                menu.setModifiable(true);
                menu.openMenu(player);

            }
        }.runTaskLater(Duel.get(), 1l);

    }
}
