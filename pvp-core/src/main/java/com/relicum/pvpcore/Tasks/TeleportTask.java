package com.relicum.pvpcore.Tasks;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * TeleportTask
 *
 * @author Relicum
 * @version 0.0.1
 */
public class TeleportTask extends BukkitRunnable {

    private Player player;
    private Location location;

    private TeleportTask(Player player, Location location, Plugin paramPlugin, int delay) {

        this.player = player;

        this.location = location;
        this.runTaskLater(paramPlugin, delay);

        if (!location.getChunk().isLoaded()) {
            location.getChunk().load();
        }

        player.sendMessage(ChatColor.GREEN + "Teleporting in " + ChatColor.GOLD + (delay / 20) + ChatColor.GREEN + " seconds");
    }

    /**
     * Create a new TelePort Task the player will be TP'd after the specified
     * delay.
     *
     * @param player the player
     * @param location the location
     * @param paramPlugin the param plugin
     * @param delay the delay
     */
    public static void create(Player player, Location location, Plugin paramPlugin, int delay) {

        new TeleportTask(player, location, paramPlugin, delay);
    }

    @Override
    public void run() {

        player.playSound(location, Sound.PORTAL_TRAVEL, 10.0f, 1.0f);
        player.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
        cancel();

    }
}
