package com.relicum.duel.Handlers;

import com.massivecraft.massivecore.adapter.relicum.RankArmor;
import com.relicum.duel.Commands.DuelMsg;
import com.relicum.duel.Configs.LobbyLoadOutLoader;
import com.relicum.duel.Configs.LobbyPlayerConfigs;
import com.relicum.duel.Duel;
import com.relicum.duel.Events.PlayerJoinLobbyEvent;
import com.relicum.duel.Menus.LeaveLobbyHandler;
import com.relicum.duel.Objects.GameInvite;
import com.relicum.duel.Objects.LobbyLoadOut;
import com.relicum.duel.Objects.PvpPlayer;
import com.relicum.locations.SpawnPoint;
import com.relicum.pvpcore.Enums.JoinCause;
import com.relicum.pvpcore.Enums.Symbols;
import com.relicum.pvpcore.Gamers.PvpResponse;
import com.relicum.pvpcore.Kits.LobbyHotBar;
import com.relicum.pvpcore.Tasks.TeleportTask;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;
import java.io.File;
import java.util.HashSet;
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
    private boolean lobbyEnabled = false;
    private boolean adminMode = true;
    private boolean autoJoin = false;
    private LobbyLoadOut lobbyLoadOut;
    private LobbyLoadOutLoader loader;
    private Set<String> inLobby = new HashSet<>();
    private boolean accepting = true;
    private SpawnPoint lobbySpawn;

    private LobbyGameLink.Inner gameLink;


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

        if (!isLobbySpawn()) {
            msg.logInfoFormatted("1v1 Lobby Spawn is not set, you need to set this before you do anything else");

            setLobbyEnabled(false);
            setIasAccepting(false);

            plugin.getConfigs().setLobbyEnabled(false);
        }

        this.gameLink = getGameHandler().getAccess(this);


        plugin.getServer().getPluginManager().registerEvents(this, plugin);


    }


    @Nullable
    public PvpResponse addPlayer(Player player, RankArmor rank, SpawnPoint backLocation, JoinCause cause) {

        if (cause == JoinCause.END_GAME) {

            //Might want to do some checking to make sure everything completed ok.
        }

        return getGameHandler().add(player, rank, backLocation);

    }

    public DuelMsg getMsg() {

        return msg;
    }


    /**
     * Is lobby spawn been set.
     *
     * @return true if it has, false if not.
     */
    public boolean isLobbySpawn() {

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
     * @return true if they were removed, false if the player was not in the list or an error occurred.
     */
    public boolean removeFromLobby(String uuid) {

        return inLobby.remove(uuid);
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

    public void refreshSpawn() {

        lobbySpawn = plugin.getConfigs().getLobbySpawn();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onLobby(PlayerJoinLobbyEvent event) {

        Player player = event.getPlayer();

        if (event.isCancelled()) {

            DuelMsg.getInstance().sendErrorMessage(player, "Error: Lobby Join event has been canceled by another plugin " + Symbols.EXCLAMATION_MARK.toChar());

            return;
        }

        if (isInLobby(player.getUniqueId().toString())) {
            msg.sendErrorMessage(player, "Error: Unable to join 1v1 Zone as you are already in it");
            event.setCancelled(true);
            return;
        }

        if (!player.isOp() && (isAdminMode() || !isLobbyEnabled() || !isAccepting() || !isLobbySpawn())) {

            getMsg().sendErrorMessage(player, "Sorry but you can not currently join 1v1 ");

            return;

        }

        new BukkitRunnable() {

            int c = 1;

            @Override
            public void run() {

                if (c == 1) {
                    TeleportTask.create(event.getPlayer(), getLobbySpawn(), plugin, 1);
                }

                if (c == 2) {

                    PvpResponse response = addPlayer(event.getPlayer(), event.getRank(), event.getFrom(), event.getCause());

                    assert response != null;
                    if (!response.response()) {

                        event.getPlayer().sendMessage(ChatColor.RED + response.getMessage());

                        event.setCancelled(true);

                        cancel();

                    }
                }

                c++;
                if (c == 3) {
                    cancel();
                }
            }

        }.runTaskTimer(plugin, 2l, 2l);


        plugin.getStatsManager().load(event.getStringUUID());
        addToLobby(event.getStringUUID());

    }

    public void removeAndReturn(Player player) {

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
        Action action = event.getAction();
        if (!action.equals(Action.RIGHT_CLICK_AIR) && !action.equals(Action.LEFT_CLICK_AIR)) {

            return;
        }

        if (slot == 0) {
            if (event.getItem().getType().equals(Material.GOLD_AXE) && action.equals(Action.LEFT_CLICK_AIR)) {
                player.sendMessage("Getting this far");

                event.setCancelled(true);
                msg.sendMessage(player, "You have left clicked the air");
                return;
            }
        }
        if (slot == 8) {
            if (event.getItem().getType().equals(Material.WATCH)) {

                if (!action.equals(Action.RIGHT_CLICK_AIR)) {

                    msg.sendErrorMessage(player, "Invalid click need to right click air");
                    return;
                }

                ItemStack watch = event.getItem();

                if (!watch.hasItemMeta()) {

                    msg.sendErrorMessage(player, "Invalid item no meta found");
                    return;
                }

                ItemMeta meta = watch.getItemMeta();

                if (!meta.hasDisplayName() && !meta.hasLore()) {

                    msg.sendErrorMessage(player, "Invalid item no display name or lore found");
                    return;

                }

                if (!meta.getDisplayName().equalsIgnoreCase("§3§l» §a§lLeave 1v1 Lobby §3§l«")) {

                    msg.sendErrorMessage(player, "Invalid display");
                    return;

                }
                event.setCancelled(true);
                msg.sendMessage(player, "Click confirm to leave");
                plugin.getMenuManager().getConfirmMenu(new LeaveLobbyHandler()).openMenuForEditing(player);
                return;
            }

        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {


        if (isInLobby(event.getPlayer().getUniqueId().toString())) {
            plugin.getLogger().info("Player has quit but is still in the Set of players in Lobby handler");

            if (inLobby.remove(event.getPlayer().getUniqueId().toString())) {
                plugin.getLogger().info("Successfully removed the player from the map");
            }

        }
    }

    @SuppressFBWarnings({"NOISE_OPERATION"})
    @EventHandler
    public void onInterEnt(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Player)) {
            return;
        }
        Player p = event.getPlayer();

        if (p.getInventory().getHeldItemSlot() != 0 && !p.getItemInHand().getType().equals(Material.GOLD_AXE)) {
            return;
        }

        if (isInLobby(p.getUniqueId().toString()) && isInLobby(event.getRightClicked().getUniqueId().toString())) {

            ItemMeta meta = p.getItemInHand().getItemMeta();
            if (meta.hasDisplayName() && meta.getDisplayName().equalsIgnoreCase("§3§l» §6§lChallenge a Player §3§l«")) {
                event.setCancelled(true);
                Player p2 = (Player) event.getRightClicked();
                PvpPlayer pv1 = plugin.getGameHandler().getPvpPlayer(p.getUniqueId().toString());
                PvpPlayer pv2 = plugin.getGameHandler().getPvpPlayer(p2.getUniqueId().toString());
                GameInvite invite = new GameInvite(plugin, pv1, pv2, plugin.getKitHandler().getKit("diamond"));
                plugin.getInviteCache().addInvite(invite);
                msg.sendMessage(p, "You have requested a 1v1 with &6 " + p2.getName());
                msg.sendMessage(p2, "Player &6" + p.getName() + " &ahas requested a 1v1 with you");

            }

            return;
        }

    }

    @EventHandler
    public void onDam(EntityDamageEvent event) {

        if (event.getEntity() instanceof Player) {

            if (isInLobby(event.getEntity().getUniqueId().toString())) {

                event.setCancelled(true);
                ((Player) event.getEntity()).setHealth(20.0d);
            }
        }
    }

    @EventHandler
    public void onFoodDrop(FoodLevelChangeEvent event) {

        if (event.getEntity() instanceof Player) {

            if (isInLobby(event.getEntity().getUniqueId().toString())) {

                event.setCancelled(true);

                ((Player) event.getEntity()).setFoodLevel(20);
                ((Player) event.getEntity()).setSaturation(20.0f);
                ((Player) event.getEntity()).setExhaustion(0.2f);

                event.getEntity().sendMessage(ChatColor.GREEN + "Food event was called");

            }

        }
    }

    @EventHandler
    public void noDrops(PlayerDropItemEvent event) {

        Player player = event.getPlayer();

        if (isInLobby(player.getUniqueId().toString())) {

            event.setCancelled(true);
            player.sendMessage(ChatColor.GREEN + "Drops have been blocked while in lobby");
        }
    }

    @EventHandler
    public void noPickUps(PlayerPickupItemEvent event) {

        Player player = event.getPlayer();

        if (isInLobby(player.getUniqueId().toString())) {

            event.setCancelled(true);
            player.sendMessage(ChatColor.GREEN + "Pick ups have been blocked while in lobby");
        }
    }

    @EventHandler
    public void noClicking(InventoryClickEvent event) {

        if (event.getWhoClicked() instanceof Player) {

            Player player = (Player) event.getWhoClicked();

            if (isInLobby(player.getUniqueId().toString())) {

                event.setCancelled(true);
                player.sendMessage(ChatColor.GREEN + "No Clicking in player inventories have been blocked while in lobby");
            }
        }
    }

    @EventHandler
    public void noMoveItem(InventoryDragEvent event) {

        if (event.getWhoClicked() instanceof Player) {

            Player player = (Player) event.getWhoClicked();

            if (isInLobby(player.getUniqueId().toString())) {

                event.setCancelled(true);
                player.sendMessage(ChatColor.GREEN + "No dragging items in player inventories has been blocked while in lobby");
            }
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
}
