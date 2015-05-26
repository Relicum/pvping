package com.relicum.pvpcore.Tasks;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.metadata.FixedMetadataValue;
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


    private TeleportTask(Player player, Location location, Plugin paramPlugin, int delay, int meta) {

        if (meta != 1 && meta != 2 && meta != -1) {
            throw new IllegalArgumentException("MetaData value must either be 1 or 2 or -1");
        }

        if (meta != -1) {
            player.setMetadata("DUEL-META", new FixedMetadataValue(paramPlugin, meta));
        }


        this.player = player;

        this.location = location;

        if (delay == 0) {
            this.runTask(paramPlugin);
        }
        else {
            this.runTaskLater(paramPlugin, delay);
        }

        if (!location.getChunk().isLoaded()) {

            location.getChunk().load();
        }

        player.sendMessage(ChatColor.GREEN + "Teleporting....");
    }

    /**
     * Create a new TelePort Task the player will be TP'd after the specified
     * delay.
     *
     * @param player      the player
     * @param location    the location
     * @param paramPlugin the param plugin
     * @param delay       the delay
     */
    public static void create(Player player, Location location, Plugin paramPlugin, int delay) {

        new TeleportTask(player, location, paramPlugin, delay, -1);
    }

    /**
     * Create a new TelePort Task the player will be TP'd after the specified
     * delay.
     *
     * @param player      the player
     * @param location    the location
     * @param paramPlugin the param plugin
     * @param delay       the delay
     * @param meta        name of meta data if you want to attach it to the player
     */
    public static void create(Player player, Location location, Plugin paramPlugin, int delay, int meta) {

        new TeleportTask(player, location, paramPlugin, delay, meta);
    }

    @Override
    public void run() {

        player.playSound(location, Sound.ZOMBIE_HURT, 10.0f, 1.0f);
        player.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
        cancel();

    }
}
