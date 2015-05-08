package com.relicum.duel.Handlers;

import com.relicum.duel.Duel;
import com.relicum.duel.Objects.PvpPlayer;
import com.relicum.pvpcore.Enums.EndReason;
import org.bukkit.entity.Player;
import java.util.*;

/**
 * GameQueueHandler
 *
 * @author Relicum
 * @version 0.0.1
 */
public class GameQueueHandler {

    private final static int TOTAL = 2;
    private Queue<String> playerQueue = new LinkedList<>();
    private Map<String, PvpPlayer> players = new HashMap<>();
    private Duel plugin;

    public GameQueueHandler(Duel plugin) {

        this.plugin = plugin;

    }

    /**
     * Gets plugin.
     *
     * @return the plugin
     */
    public Duel getPlugin() {

        return plugin;

    }

    /**
     * Creates a {@link PvpPlayer} for the player and adds them to the central
     * store as well as the Queue.
     *
     * @param player the player
     */
    public boolean add(Player player) {

        if (isKnown(player.getUniqueId().toString()))
            return false;

        PvpPlayer pvp = new PvpPlayer(player, getPlugin());

        players.put(uuidToString(player.getUniqueId()), pvp);

        if (isFull())
        {
            pvp.sendMessage("You are in luck we have found a match get ready");
        }
        else
        {
            playerQueue.add(pvp.getUuid().toString());
            pvp.setQueueing(true);
            pvp.sendMessage("You have been added to the queue");
        }

        return true;

    }

    /**
     * Remove the {@link Player} {@link PvpPlayer} object and returns it.
     *
     * @param player the {@link Player}
     * @return the players {@link PvpPlayer} object. Returns null if no player
     * was found.
     */
    public PvpPlayer remove(Player player, EndReason reason) {

        if (isKnown(player.getUniqueId().toString()))
        {
            PvpPlayer pvpPlayer = players.remove(player.getUniqueId().toString());
            pvpPlayer.gameEnd(reason);
            return pvpPlayer;
        }
        return null;

    }

    /**
     * Remove and destroy the {@link Player} {@link PvpPlayer} object.
     *
     * @param player the {@link Player}
     * @return true on success, false if the {@link Player} was not found or an
     * error occured.
     */
    public boolean removeAndDestroy(Player player, EndReason reason) {

        try
        {
            removeFromQueue(player.getUniqueId().toString());
            remove(player, reason).clearRef();
            System.out.println("Player: " + player.getName() + " with UUID: " + player.getUniqueId().toString() + " has been removed from player store");
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * If the player is in the queue remove them, this checks just to see if
     * they are in the queue before trying to remove them.
     *
     * @param uuid the string uuid of the {@link Player} to remove from the
     * queue.
     */
    public void removeFromQueue(String uuid) {

        if (isQueuing(uuid))
            playerQueue.remove(uuid);

    }

    /**
     * Is the player being handled.
     *
     * @param uuid the string uuid of the player to check for
     * @return true if the player is found, false if not.
     */
    public boolean isKnown(String uuid) {

        return players.containsKey(uuid);
    }

    /**
     * Is a player in the queue.
     *
     * @param uuid the string uuid of the {@link Player}
     * @return true and they are in the queue, false and they are ot.
     */
    public boolean isQueuing(String uuid) {

        return playerQueue.contains(uuid);

    }

    /**
     * Is the queue full.
     *
     * @return true if it is full, else false.
     */
    public boolean isFull() {

        return TOTAL >= playerQueue.size();

    }

    /**
     * Uuid to string, tries to remove the reference from the player object.
     *
     * @param uuid the uuid
     * @return the uuid string
     */
    public String uuidToString(UUID uuid) {

        String tmp = "";
        tmp += uuid.toString();
        return tmp;

    }

    public void clearQueue() {

        playerQueue.clear();
    }

    public void clearAllPlayers() {

        clearQueue();

        for (PvpPlayer pvpPlayer : players.values())
        {
            pvpPlayer.clearRef();
        }

        players.clear();

    }

}
