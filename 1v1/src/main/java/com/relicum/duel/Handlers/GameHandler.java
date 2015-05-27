package com.relicum.duel.Handlers;

import com.massivecraft.massivecore.adapter.relicum.RankArmor;
import com.relicum.duel.Commands.DuelMsg;
import com.relicum.duel.Duel;
import com.relicum.duel.Objects.PvpGame;
import com.relicum.duel.Objects.PvpPlayer;
import com.relicum.locations.SpawnPoint;
import com.relicum.pvpcore.Enums.EndReason;
import com.relicum.pvpcore.Enums.PlayerState;
import com.relicum.pvpcore.Gamers.PvpResponse;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;

/**
 * GameHandler
 *
 * @author Relicum
 * @version 0.0.1
 */
public class GameHandler implements Listener {

    private final static int TOTAL = 2;
    private final SpawnPoint worldSpawn;
    private Queue<String> playerQueue = new LinkedList<>();
    private Map<String, PvpPlayer> players = new HashMap<>();
    private Duel plugin;
    private Map<UUID, PvpGame> games = new HashMap<>();
    private DuelMsg msg;
    private LobbyGameLink link;


    public GameHandler(Duel plugin) {

        this.plugin = plugin;
        worldSpawn = new SpawnPoint(plugin.getServer().getWorld("world").getSpawnLocation());
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        msg = DuelMsg.getInstance();
        openLink();
    }

    @Nullable
    public LobbyGameLink.Inner getAccess(LobbyHandler handler) {

        return link.requestAccess(handler);
    }


    public void openLink() {

        this.link = new LobbyGameLink(this) {

            @Override
            public PlayerState getPlayerState(String uuid) {
                return GameHandler.this.getPlayerState(uuid);
            }

            @Override
            public void setPlayerState(String uuid, PlayerState state) {

                GameHandler.this.registerPlayerState(uuid, state);
            }


        };
    }

    /**
     * Gets players current state {@link PlayerState}.
     *
     * @param uuid the uuid
     * @return the player state
     */
    public PlayerState getPlayerState(String uuid) {
        return getPvpPlayer(uuid).getState();
    }


    /**
     * Attempts to register the {@link PlayerState} for the player.
     * <p>It might not always to be possible to resgister the {@link PlayerState} under certain conditions.
     * <p>It is important to check the returned {@link PvpResponse} for the outcome.
     *
     * @param uuid  the uuid
     * @param state the {@link PlayerState} to set
     * @return {@link PvpResponse} which holds the outcome of attempting to set the players state.
     */
    public PvpResponse registerPlayerState(String uuid, PlayerState state) {

        return players.get(uuid).registerState(state);

    }


    public PvpPlayer getPvpPlayer(String uuid) {

        return players.get(uuid);
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
    @Nullable
    public PvpResponse add(Player player, RankArmor rank, SpawnPoint point) {

        if (isKnown(player.getUniqueId().toString())) {
            return new PvpResponse(PvpResponse.ResponseType.FAILURE, "Player: " + player.getName() + " is already in the players map");
        }


        PvpPlayer pvp = new PvpPlayer(player, getPlugin(), point, rank);

        players.put(uuidToString(player.getUniqueId()), pvp);

        pvp.sendMessage("Welcome to the 1v1 lobby");


        return new PvpResponse(PvpResponse.ResponseType.SUCCESS, null);

    }


    /**
     * Remove the {@link Player} {@link PvpPlayer} object and returns it.
     *
     * @param player the {@link Player}
     * @return the players {@link PvpPlayer} object. Returns null if no player
     * was found.
     */
    @Nullable
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
                plugin.getLobbyHandler().removeFromLobby(player.getUniqueId().toString());
                pvpPlayer.clearRef();

                System.out.println("Number of players now in queue is " + playerQueue.size());
                System.out.println("Number of players now in player store is " + players.size());
                return true;
            }

            plugin.getLobbyHandler().removeFromLobby(player.getUniqueId().toString());
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
     * Set player flag to offline.
     *
     * @param uuid the string uuid of the player
     */
    public void setPlayerOffline(String uuid) {

        players.get(uuid).setOnline(false);
    }

    /**
     * Set player flag to online.
     *
     * @param uuid the string uuid of the player
     */
    public void setPlayerOnline(String uuid) {

        players.get(uuid).setOnline(true);
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

        link.setActive(false);

        clearQueue();

        players.values().forEach(com.relicum.duel.Objects.PvpPlayer::clearRef);

        players.clear();

    }

    public Location getWorldSpawn() {

        return worldSpawn.toLocation();
    }


    public void onDamage(EntityDamageByEntityEvent event) {

        if (event.getDamager().getType().equals(EntityType.PLAYER) && event.getEntity().getType().equals(EntityType.PLAYER)) {
            Player damager = (Player) event.getDamager();
            Player defender = (Player) event.getEntity();
            if (players.containsKey(damager.getUniqueId().toString()) && players.containsKey(defender.getUniqueId().toString())) {

                if (players.get(damager.getUniqueId().toString()).inLobby() && players.get(defender.getUniqueId().toString()).inLobby()) {
                    msg.sendMessage(damager, "You have requested a player to 1v1 with " + defender.getName());
                    msg.sendMessage(defender, "Player " + damager.getName() + " has requested a 1v1 with you");
                    event.setCancelled(true);
                }
            }
        }
    }


    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        String uuid = event.getPlayer().getUniqueId().toString();

        if (isKnown(uuid)) {
            try {
                setPlayerOffline(uuid);
                removeAndDestroy(event.getPlayer(), EndReason.LOGGED);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
