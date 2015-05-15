package com.relicum.duel.Events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Name: PostTeleportEvent.java Created: 14 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class PostTeleportEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private Location from;
    private Location to;


    public PostTeleportEvent(Player player, Location from, Location to) {

        this.player = player;
        this.from = from;
        this.to = to;

    }

    /**
     * Gets player.
     *
     * @return the {@link Player}
     */
    public Player getPlayer() {

        return player;
    }

    /**
     * Gets where they teleported from.
     *
     * @return the {@link Location} they teleported from.
     */
    public Location getFrom() {

        return from;
    }

    /**
     * Gets where the player teleported to
     *
     * @return the {@link Location} they teleported to.
     */
    public Location getTo() {

        return to;
    }

    @Override
    public HandlerList getHandlers() {

        return handlers;

    }


    public static HandlerList getHandlerList() {

        return handlers;

    }

}
