package com.relicum.pvpcore.Tasks;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Name: UpdateInventory.java Created: 02 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class UpdateInventory extends BukkitRunnable {

    private Player player;

    private UpdateInventory(Player paramPlayer, Plugin paramPlugin) {

        this.player = paramPlayer;
        this.runTask(paramPlugin);
    }

    public static UpdateInventory now(Player paramPlayer, Plugin paramPlugin) {

        return new UpdateInventory(paramPlayer, paramPlugin);
    }

    @Override
    public void run() {

        player.updateInventory();
        cancel();
    }
}
