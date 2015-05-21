package com.relicum.duel.Events;

import com.massivecraft.massivecore.adapter.relicum.RankArmor;
import com.relicum.locations.SpawnPoint;
import com.relicum.pvpcore.Enums.JoinCause;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * PlayerJoinLobbyEvent
 *
 * @author Relicum
 * @version 0.0.1
 */
public class PlayerJoinLobbyEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled = false;
    private Player player;
    private SpawnPoint from;
    private JoinCause cause;
    private RankArmor rank;

    public PlayerJoinLobbyEvent(Player player, SpawnPoint point, RankArmor rankArmor, JoinCause cause) {

        this.player = player;
        this.rank = rankArmor;
        this.from = point;
        this.cause = cause;

    }

    public PlayerJoinLobbyEvent(Player player, RankArmor rankArmor, JoinCause cause) {

        this.player = player;
        this.rank = rankArmor;
        this.cause = cause;

    }

    public static HandlerList getHandlerList() {

        return handlers;

    }

    public RankArmor getRank() {
        return rank;
    }

    public Player getPlayer() {

        return player;
    }

    public boolean hasLastLocation() {

        return from != null;
    }

    public SpawnPoint getFrom() {

        return from;
    }

    public String getStringUUID() {

        return player.getUniqueId().toString();
    }

    public JoinCause getCause() {

        return cause;
    }

    @Override
    public HandlerList getHandlers() {

        return handlers;

    }

    @Override
    public boolean isCancelled() {

        return cancelled;

    }

    @Override
    public void setCancelled(boolean cancelled) {

        this.cancelled = cancelled;

    }
}
