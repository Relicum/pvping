package com.relicum.duel.Handlers;

import com.relicum.duel.Commands.DuelMsg;
import com.relicum.duel.Duel;
import com.relicum.duel.Objects.PvpPlayer;
import com.relicum.locations.SpawnPoint;
import com.relicum.pvpcore.Enums.EndReason;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

/**
 * GameHandler
 *
 * @author Relicum
 * @version 0.0.1
 */
public class GameHandler implements Listener {

    private final static int TOTAL = 2;
    private Queue<String> playerQueue = new LinkedList<>();
    private Map<String, PvpPlayer> players = new HashMap<>();
    private Duel plugin;
    private DuelMsg msg;
    private final SpawnPoint worldSpawn;

    public GameHandler(Duel plugin) {

        this.plugin = plugin;
        worldSpawn = new SpawnPoint(plugin.getServer().getWorld("world").getSpawnLocation());
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }

    /**
     * Gets plugin.
     *
     * @return the plugin
     */
    public Duel getPlugin() {

        return plugin;

    }

    public DuelMsg getMsg() {

        return msg;
    }

    /**
     * Creates a {@link PvpPlayer} for the player and adds them to the central
     * store as well as the Queue if needed.
     *
     * @param player the player
     */
    public boolean add(Player player, SpawnPoint point) {

        if (isKnown(player.getUniqueId().toString())) {
            return false;
        }


        PvpPlayer pvp = new PvpPlayer(player, getPlugin(), point);

        players.put(uuidToString(player.getUniqueId()), pvp);

        if (isFull()) {
            pvp.sendMessage("You are in luck we have found a match get ready");

        }
        else {
            if (addToQueue(pvp)) {
                pvp.sendMessage("You have been added to the queue");
                pvp.sendActionMessage("&6&lYou have been added to the queue");
            }
            else {
                pvp.sendMessage("You are already in the queue");
            }
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

        if (isKnown(player.getUniqueId().toString())) {
            PvpPlayer pvpPlayer = players.remove(player.getUniqueId().toString());
            pvpPlayer.setQueueing(false);
            pvpPlayer.gameEnd(reason);
            return pvpPlayer;
        }
        return null;

    }


    /**
     * Remove and destroy the {@link Player} {@link PvpPlayer} object.
     * <p>This also saves the players stats at the same time and removes them from the stats manager.
     *
     * @param player the {@link Player}
     * @return true on success, false if the {@link Player} was not found or an
     * error occured.
     */
    public boolean removeAndDestroy(Player player, EndReason reason) {

        if (isKnown(player.getUniqueId().toString())) {
            PvpPlayer pvpPlayer = players.remove(player.getUniqueId().toString());
            if (reason.equals(EndReason.LOGGED)) {
                pvpPlayer.gameEnd(reason);
                removeFromQueue(player.getUniqueId().toString());

                pvpPlayer.clearRef();

                System.out.println("Number of players now in queue is " + playerQueue.size());
                System.out.println("Number of players now in player store is " + players.size());
                return true;
            }

            pvpPlayer.gameEnd(reason);
            // plugin.getStatsManager().removeAndSave(pvpPlayer.getStringUUID());
            removeFromQueue(pvpPlayer);

            try {

                pvpPlayer.clearRef();
                System.out.println("Player: " + player.getName() + " with UUID: " + player.getUniqueId().toString() + " has been removed from player store");
                return true;
            }
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        return false;
    }

    /**
     * If the player is in the queue remove them, this checks just to see if
     * they are in the queue before trying to remove them.
     *
     * @param pvpPlayer the {@link PvpPlayer} to remove from the queue.
     */
    public void removeFromQueue(PvpPlayer pvpPlayer) {

        if (isQueuing(pvpPlayer.getStringUUID())) {
            playerQueue.remove(pvpPlayer.getStringUUID());
            pvpPlayer.setQueueing(false);
        }

    }

    /**
     * If the player is in the queue remove them, this checks just to see if
     * they are in the queue before trying to remove them.
     *
     * @param uuid the string uuid of the {@link Player} to remove from the
     *             queue.
     */
    public void removeFromQueue(String uuid) {

        if (isQueuing(uuid)) {
            playerQueue.remove(uuid);
            if (isKnown(uuid)) {
                players.get(uuid).setQueueing(false);
            }
        }

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


    public boolean addToQueue(PvpPlayer pvpPlayer) {

        if (isQueuing(pvpPlayer.getUuid().toString())) {
            return false;
        }
        else {
            playerQueue.add(pvpPlayer.getUuid().toString());
            pvpPlayer.setQueueing(true);
            return true;
        }
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

        return playerQueue.size() >= TOTAL;

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

        for (PvpPlayer pvpPlayer : players.values()) {
            pvpPlayer.clearRef();
        }

        players.clear();

    }

    public Location getWorldSpawn() {

        return worldSpawn.toLocation();
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {

        if (event.getEntityType().equals(EntityType.PLAYER)) {
            if (players.containsKey(event.getEntity().getUniqueId().toString())) {

                if (players.get(event.getEntity().getUniqueId().toString()).isGod()) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onFoodDrop(FoodLevelChangeEvent event) {

        if (event.getEntityType().equals(EntityType.PLAYER)) {
            if (players.containsKey(event.getEntity().getUniqueId().toString())) {

                if (players.get(event.getEntity().getUniqueId().toString()).isGod()) {
                    event.setCancelled(true);
                }
            }
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        if (isKnown(event.getPlayer().getUniqueId().toString())) {
            try {
                removeAndDestroy(event.getPlayer(), EndReason.LOGGED);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
