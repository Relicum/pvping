package com.relicum.duel.Handlers;

import com.relicum.duel.Duel;
import com.relicum.duel.Objects.PvpPlayer;
import org.bukkit.entity.Player;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Name: GameQueueHandler.java Created: 28 April 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class GameQueueHandler {

    private final int TOTAL = 2;
    private Queue<PvpPlayer> playerQueue = new LinkedList<>();
    private Set<PvpPlayer> players = new HashSet<>();
    private Duel plugin;

    public GameQueueHandler(Duel plugin) {
        this.plugin = plugin;
    }

    public Duel getPlugin() {
        return plugin;
    }

    public void add(Player player) {
        PvpPlayer pvp = new PvpPlayer(player, getPlugin());
        playerQueue.add(pvp);
        if (isFull()) {
            List<PvpPlayer> lp = playerQueue.stream().limit(2).collect(Collectors.toList());
        }
    }

    public boolean isFull() {
        return TOTAL >= playerQueue.size();
    }

}
