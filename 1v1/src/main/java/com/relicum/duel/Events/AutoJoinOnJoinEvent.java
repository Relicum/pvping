package com.relicum.duel.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Name: AutoJoinOnJoinEvent.java Created: 21 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class AutoJoinOnJoinEvent extends PlayerJoinEvent {
    public AutoJoinOnJoinEvent(Player playerJoined, String joinMessage) {
        super(playerJoined, joinMessage);

    }
}
