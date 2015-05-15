package com.relicum.duel;

import com.relicum.duel.Commands.DuelMsg;
import com.relicum.duel.Events.PlayerJoinLobbyEvent;
import com.relicum.locations.SpawnPoint;
import com.relicum.pvpcore.Enums.JoinCause;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.function.Predicate;

/**
 * Name: ConditionalTeleport.java Created: 13 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class ConditionalTeleport extends BukkitRunnable {

    private Player player;
    private JoinCause joinCause;
    private Location location;

    private boolean result;

    public ConditionalTeleport(Player player, JoinCause cause, Location location, Plugin paramPlugin, int delay, Predicate<PlayerJoinLobbyEvent> condition) {

        this.player = player;
        this.joinCause = cause;
        this.location = location;

        if (!location.getChunk().isLoaded()) {
            location.getChunk().load();
        }

        PlayerJoinLobbyEvent event = new PlayerJoinLobbyEvent(player, new SpawnPoint(player.getLocation()), this.joinCause);


        if (call(event, condition)) {
            System.out.println("Event is canceled");
            return;
        }

        DuelMsg.getInstance().sendMessage(player, "Teleporting to lobby");
        this.runTaskLater(paramPlugin, delay);

    }


    private boolean call(PlayerJoinLobbyEvent event, Predicate<PlayerJoinLobbyEvent> tester) {

        player.getServer().getPluginManager().callEvent(event);

        result = tester.test(event);

        return result;

    }

    public Player getPlayer() {

        return player;
    }

    public Location getLocation() {

        return location;
    }

    public JoinCause getJoinCause() {

        return joinCause;
    }

    public boolean isResult() {

        return result;
    }

    @Override
    public void run() {

        player.playSound(location, Sound.NOTE_PLING, 10.0f, 1.0f);
        player.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
        cancel();
    }
}
