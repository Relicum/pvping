package com.relicum.duel.Handlers;

import com.massivecraft.massivecore.adapter.relicum.RankArmor;
import com.relicum.duel.Commands.DuelMsg;
import com.relicum.duel.Configs.LobbyLoadOutLoader;
import com.relicum.duel.Configs.LobbyPlayerConfigs;
import com.relicum.duel.Duel;
import com.relicum.duel.Events.PlayerJoinLobbyEvent;
import com.relicum.duel.Objects.LobbyLoadOut;
import com.relicum.duel.Objects.PvpPlayer;
import com.relicum.locations.SpawnPoint;
import com.relicum.pvpcore.Enums.JoinCause;
import com.relicum.pvpcore.Enums.Symbols;
import com.relicum.pvpcore.Kits.LobbyHotBar;
import com.relicum.pvpcore.Tasks.TeleportTask;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Name: LobbyHandler.java Created: 12 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class LobbyHandler implements Listener {


    private transient Duel plugin;
    private DuelMsg msg;
    private transient GameHandler gameHandler;
    private Map<String, PvpPlayer> players = new HashMap<>();
    private boolean lobbyEnabled = false;
    private boolean adminMode = true;
    private boolean autoJoin = false;
    private LobbyLoadOut lobbyLoadOut;
    private LobbyLoadOutLoader loader;
    private Set<String> inLobby = new HashSet<>();
    private boolean accepting = true;
    private SpawnPoint lobbySpawn;


    public LobbyHandler(Duel plugin) {

        this.plugin = plugin;

        if (plugin.isFirstLoad()) {

            doFirstLoad();
        }

        else {

            loader = new LobbyLoadOutLoader(plugin.getDataFolder().toString() + File.separator, "lobbysettings");
            lobbyLoadOut = loader.load();
            lobbyLoadOut.initPotions();
        }

        this.msg = DuelMsg.getInstance();

        lobbyEnabled = plugin.getConfigs().isLobbyEnabled();
        adminMode = plugin.getConfigs().isAdminMode();
        autoJoin = plugin.getConfigs().isAutoJoin();
        lobbySpawn = plugin.getConfigs().getLobbySpawn();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public LobbyHandler(Duel plugin, GameHandler gameHandler) {

        this.plugin = plugin;
        this.msg = DuelMsg.getInstance();
        this.gameHandler = gameHandler;

        loader = new LobbyLoadOutLoader(plugin.getDataFolder().toString() + File.separator, "lobbysettings");
        lobbyLoadOut = loader.load();
        lobbyLoadOut.initPotions();

        lobbyEnabled = plugin.getConfigs().isLobbyEnabled();
        adminMode = plugin.getConfigs().isAdminMode();
        autoJoin = plugin.getConfigs().isAutoJoin();
        lobbySpawn = plugin.getConfigs().getLobbySpawn();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);


    }


    public void addPlayer(Player player, RankArmor rank, SpawnPoint backLocation, JoinCause cause) {

        if (isKnown(player.getUniqueId().toString())) {
            msg.sendErrorMessage(player, "Error: Unable to join 1v1 Zone as you are already in it");
            return;
        }

        //PvpPlayer pvp = new PvpPlayer(player, plugin);

        getGameHandler().add(player, backLocation);
    }

    public DuelMsg getMsg() {

        return msg;
    }

    public boolean islobbySpawn() {

        return lobbySpawn != null;
    }


    /**
     * Add player to list of player in the lobby.
     *
     * @param uuid the players string uuid
     * @return true if successfully added, false and they were already in the lobby or an error occurred.
     */
    public boolean addToLobby(String uuid) {

        return inLobby.add(uuid);
    }

    /**
     * Remove player from list of players in the lobby.
     *
     * @param uuid the players string uuid
     * @return the true if they were removed, false if the player was not in the list or an error occurred.
     */
    public boolean removeFromLobby(String uuid) {

        return inLobby.remove(uuid);
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
     * Checks if the player is in the lobby.
     *
     * @param uuid the players string uuid
     * @return true if they are in the lobby, false if not.
     */
    public boolean isInLobby(String uuid) {

        return inLobby.contains(uuid);
    }

    /**
     * Gets plugin.
     *
     * @return main plugin instance.
     */
    public Duel getPlugin() {

        return plugin;
    }

    /**
     * Gets lobby load out.
     *
     * @return the {@link LobbyLoadOut}
     */
    public LobbyLoadOut getLobbyLoadOut() {

        return lobbyLoadOut;
    }

    /**
     * Gets Gson loader.
     *
     * @return the loader {@link LobbyLoadOutLoader}
     */
    public LobbyLoadOutLoader getLoader() {

        return loader;
    }

    /**
     * Gets game handler {@link GameHandler}.
     *
     * @return the {@link GameHandler}
     */
    public GameHandler getGameHandler() {

        return gameHandler;
    }

    public void setGameHandler(GameHandler gameHandler) {

        this.gameHandler = gameHandler;
    }

    /**
     * Is the lobby currently accepting new player.
     *
     * @return true and players are being accepted false and standard players will not be able to join.
     */
    public boolean isAccepting() {

        return accepting;
    }

    public void setIasAccepting(boolean accept) {

        this.accepting = accept;
    }

    public void doFirstLoad() {

        lobbyLoadOut = new LobbyLoadOut();
        lobbyLoadOut.setContents(LobbyHotBar.create().getItems());
        lobbyLoadOut.setArmor(new LobbyArmor(true));
        lobbyLoadOut.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1000000, 1, false, false));
        PotionEffect p = new PotionEffect(PotionEffectType.FAST_DIGGING, 1000000, 1, false, false);
        lobbyLoadOut.addPotionEffect(p);
        lobbyLoadOut.setSettings(LobbyPlayerConfigs.create(true).getSettings());
        loader = new LobbyLoadOutLoader(plugin.getDataFolder().toString() + File.separator, "lobbysettings");
        loader.save(lobbyLoadOut);

    }


    @EventHandler(priority = EventPriority.HIGH)
    public void onLobby(PlayerJoinLobbyEvent event) {

        Player player = event.getPlayer();

        if (event.isCancelled()) {

            DuelMsg.getInstance().sendErrorMessage(player, "Error: Lobby Join event has been canceled by another plugin " + Symbols
                                                                                                                                    .EXCLAMATION_MARK.toChar()
            );

            return;
        }

        if (isKnown(player.getUniqueId().toString())) {
            msg.sendErrorMessage(player, "Error: Unable to join 1v1 Zone as you are already in it");
            event.setCancelled(true);
            return;
        }

        if (!player.isOp() && (!isAdminMode() || !isLobbyEnabled() || !isAccepting())) {

            getMsg().sendErrorMessage(player, "Sorry but you can not currently join 1v1 ");

            return;

        }

        new BukkitRunnable() {

            int c = 1;

            @Override
            public void run() {

                if (c == 1) {
                    TeleportTask.create(event.getPlayer(), getLobbySpawn(), plugin, 0);
                }

                if (c == 2) {
                    addPlayer(event.getPlayer(), RankArmor.EMERALD, event.getFrom(), event.getCause());
                }

                c++;
                if (c == 3) {
                    cancel();
                }
            }

        }.runTaskTimer(plugin, 2l, 2l);


        plugin.getStatsManager().load(event.getStringUUID());

        inLobby.add(event.getStringUUID());


    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onInter(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if (event.getItem() == null) {

            return;
        }

        if (!isInLobby(player.getUniqueId().toString())) {

            return;
        }

        int slot = player.getInventory().getHeldItemSlot();

        if (!event.getAction().equals(Action.RIGHT_CLICK_AIR) || !event.getAction().equals(Action.LEFT_CLICK_AIR)) {

            return;
        }


    }


    /**
     * Gets lobbyEnabled.
     *
     * @return Value of lobbyEnabled.
     */
    public boolean isLobbyEnabled() {

        return lobbyEnabled;
    }

    /**
     * Sets new lobbyEnabled.
     *
     * @param lobbyEnabled New value of lobbyEnabled.
     */
    public void setLobbyEnabled(boolean lobbyEnabled) {

        this.lobbyEnabled = lobbyEnabled;
    }

    /**
     * Gets adminMode, if its true only players with admin permissions can join the lobby.
     *
     * @return true if admin mode is on, false and it is open to all..
     */
    public boolean isAdminMode() {

        return adminMode;
    }

    /**
     * Sets if the lobby is in admin only mode adminMode.
     *
     * @param adminMode set true for admin only mode. Default is true so you need to set it to false before standard players can join.
     */
    public void setAdminMode(boolean adminMode) {

        this.adminMode = adminMode;
    }

    /**
     * Gets lobbySpawn.
     *
     * @return the lobby spawn as {@link Location}
     */
    public Location getLobbySpawn() {

        return lobbySpawn.toLocation().clone();
    }

    /**
     * Sets new lobbySpawn.
     *
     * @param lobbySpawn New value of {@link SpawnPoint}
     */
    public void setLobbySpawn(SpawnPoint lobbySpawn) {

        this.lobbySpawn = lobbySpawn;
    }

    /**
     * Gets autoJoin.
     *
     * @return Value of autoJoin.
     */
    public boolean isAutoJoin() {

        return autoJoin;
    }

    /**
     * Sets new autoJoin.
     *
     * @param autoJoin New value of autoJoin.
     */
    public void setAutoJoin(boolean autoJoin) {

        this.autoJoin = autoJoin;
    }
}
