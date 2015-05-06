package com.relicum.duel.Objects;

import com.relicum.duel.Commands.DuelMsg;
import com.relicum.duel.Duel;
import com.relicum.locations.SpawnPoint;
import com.relicum.pvpcore.Enums.EndReason;
import com.relicum.pvpcore.Enums.PlayerState;
import com.relicum.pvpcore.FormatUtil;
import com.relicum.pvpcore.Game.PlayerStats;
import com.relicum.pvpcore.Gamers.InventoryStore;
import com.relicum.pvpcore.Gamers.PlayerSettings;
import com.relicum.pvpcore.Gamers.WeakGamer;
import com.relicum.pvpcore.Kits.Armor;
import com.relicum.pvpcore.Kits.LobbyHotBar;
import com.relicum.pvpcore.Tasks.TeleportTask;
import com.relicum.pvpcore.Tasks.UpdateInventory;
import com.relicum.titleapi.Exception.ReflectionException;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import java.util.Collection;

/**
 * PvpPlayer
 *
 * @author Relicum
 * @version 0.0.1
 */
@edu.umd.cs.findbugs.annotations.SuppressFBWarnings({ "DLS_DEAD_LOCAL_STORE" })
public class PvpPlayer extends WeakGamer<Duel> {

    private PlayerState state;
    private boolean saveInv = true;
    private boolean queueing = false;
    private String name = "";
    private InventoryStore store;
    private SpawnPoint backLocation;
    private Armor lobbyArmor;
    private ItemStack[] lobbyBar;
    private PlayerStats stats;

    public void destroy() {

        clearInventory();
        restorePlayerSettings();
        updateScoreboard(true);
        player.clear();
        System.out.println("PvpPlayer Object destroyed");
    }

    /**
     * Instantiates a new PvpPlayer.
     *
     * @param paramPlayer the param player
     * @param paramPlugin the param plugin
     */
    public PvpPlayer(final Player paramPlayer, final Duel paramPlugin) {

        super(paramPlayer, paramPlugin);
        this.backLocation = new SpawnPoint(paramPlayer.getLocation());
        // this.lobbyArmor = new Armor("LOBBY_DEFAULT", new
        // ItemStack(Material.LEATHER_HELMET), new
        // ItemStack(Material.LEATHER_CHESTPLATE),
        // new ItemStack(Material.LEATHER_LEGGINGS), new
        // ItemStack(Material.LEATHER_BOOTS));
        this.lobbyArmor = new Armor("LOBBY_AQUA", Color.GREEN);

        LobbyHotBar bar = new LobbyHotBar();
        lobbyBar = bar.getItems();

        stats = paramPlugin.getStatsManager().getAPlayersStats(paramPlayer.getUniqueId().toString());

        setName(paramPlayer.getName());
        teleport(getPlugin().getServer().getWorld("world").getSpawnLocation());
        try {
            plugin.getTitleMaker().sendTitlePacket(paramPlayer, "&aWelcome to 1v1", 40, 60, 20);
        } catch (ReflectionException e) {
            e.printStackTrace();
        }
        setState(PlayerState.LOBBY);
        createScoreboard();
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final void setState(PlayerState playerState) {

        this.state = playerState;

        if (state.equals(PlayerState.LOBBY))
            stateLobby();
        else if (state.equals(PlayerState.QUIT))
            stateGameEnd();
    }

    public PlayerStats getStats() {
        return stats;
    }

    public void gameEnd(EndReason reason) {

        switch (reason) {
            case LEAVE_CMD: {
                clearInventory();
                restorePlayerSettings();
                updateScoreboard(true);
                UpdateInventory.now(getPlayer(), plugin);
                stats.incrementLoss();
                plugin.getStatsManager().removeAndSave(getUuid().toString());
                teleport(backLocation.toLocation());

                break;
            }
            default: {
                setState(PlayerState.LOBBY);
                teleport(getPlugin().getServer().getWorld("world").getSpawnLocation());
                sendMessage("You are returning to the lobby spawn");
                break;
            }
        }

    }

    /**
     * Gets {@link PlayerState}
     *
     * @return the current {@link PlayerState}
     */
    public PlayerState getState() {

        return state;
    }

    public void stateLobby() {

        if (saveInv) {

            savePlayerSettings();
            clearInventory();
            applyLobbyInventory();
            UpdateInventory.now(getPlayer(), plugin);
            saveInv = false;
        } else {
            updateScoreboard();
            clearInventory();
            applyLobbyInventory();
            UpdateInventory.now(getPlayer(), plugin);
        }

    }

    public void stateGameEnd() {

        clearInventory();
        restorePlayerSettings();
    }

    public void createScoreboard() {

        Scoreboard board = getPlugin().getServer().getScoreboardManager().getNewScoreboard();
        Objective objective = board.registerNewObjective(getName(), "dummy");
        objective.setDisplayName(FormatUtil.format("&6&lNoxiousPVP &a&l1v1", new Object[0]));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        getPlayer().setScoreboard(board);
        setScoreboardInfo(board);
    }

    private void setScoreboardInfo(Scoreboard scoreboard) {

        Objective objective = scoreboard.getObjective(getName());
        objective.getScore(FormatUtil.format("&b&l{0}'s Stats", new Object[] { getName() })).setScore(13);
        objective.getScore("§r").setScore(12);
        objective.getScore(FormatUtil.format("&a&lWins:", new Object[0])).setScore(11);
        objective.getScore(String.valueOf(stats.getWins())).setScore(10);
        objective.getScore("§a").setScore(9);
        objective.getScore(FormatUtil.format("&c&lLosses:", new Object[0])).setScore(8);
        objective.getScore(FormatUtil.format("&f{0}", new Object[] { String.valueOf(stats.getLosses()) })).setScore(7);
        objective.getScore("§b").setScore(6);
        objective.getScore(FormatUtil.format("&d&lWinstreak:", new Object[0])).setScore(5);
        objective.getScore(FormatUtil.format("&r{0}", new Object[] { String.valueOf(stats.getStreak()) })).setScore(4);
        objective.getScore("§c").setScore(3);
        objective.getScore(FormatUtil.format("&6&lBest Streak:", new Object[0])).setScore(2);
        objective.getScore(FormatUtil.format("&f&f{0}", new Object[] { String.valueOf(stats.getHighestStreak()) })).setScore(1);

    }

    public void updateScoreboard(boolean remove) {

        if (remove) {
            getPlayer().setScoreboard(getPlugin().getServer().getScoreboardManager().getNewScoreboard());

            return;
        }

        createScoreboard();
    }

    public void updateScoreboard() {

        updateScoreboard(false);
    }

    public final void teleport(final Location location) {

        TeleportTask.create(getPlayer(), location, plugin, 20);
    }

    /**
     * Save player inventory,potions and settings.
     */
    public void savePlayerSettings() {

        Player pl = getPlayer();

        Collection<PotionEffect> tmpEffects = pl.getActivePotionEffects();

        for (PotionEffect effect : pl.getActivePotionEffects()) {
            pl.removePotionEffect(effect.getType());
        }

        this.store = new InventoryStore(pl.getInventory(), tmpEffects, PlayerSettings.save(pl));

    }

    /**
     * Clear inventory.
     */
    @SuppressFBWarnings({ "DLS_DEAD_LOCAL_STORE" })
    public void clearInventory() {

        PlayerInventory inv = getPlayer().getInventory();
        getPlayer().closeInventory();
        inv.setArmorContents(new ItemStack[4]);
        inv.clear();

    }

    public void updateInventory() {
        UpdateInventory.now(getPlayer(), getPlugin());
    }

    public void applyLobbyInventory() {

        getPlayer().getInventory().setArmorContents(lobbyArmor.getArmor());
        getPlayer().setGameMode(GameMode.ADVENTURE);
        getPlayer().getInventory().setContents(lobbyBar);

    }

    /**
     * Restore player inventory and null the storage object.
     */
    public void restorePlayerSettings() {

        Player pl = getPlayer();

        pl.getInventory().setArmorContents(store.getArmor().clone());
        pl.getInventory().setContents(store.getContents().clone());
        pl.addPotionEffects(store.getEffects());
        store.getSettings().restore(pl);
        store = null;
    }

    /**
     * Is the player queueing.
     *
     * @return true if the player is in the queue, false if not.
     */
    public final boolean isQueueing() {

        return queueing;
    }

    /**
     * Sets queueing players queuing status.
     *
     * @param queueing true to mark the player as in the queue, of false to
     *        remove them from the queue.
     */
    public final void setQueueing(boolean queueing) {

        this.queueing = queueing;
    }

    /**
     * Gets players name.
     *
     * @return the players name
     */
    public String getName() {

        return name;
    }

    /**
     * Send message.
     *
     * @param mess the message to display
     * @param objects the variables
     */
    public void sendMessage(String mess, Object[] objects) {

        DuelMsg.getInstance().sendMessage(getPlayer(), FormatUtil.formatNoColor(mess, objects));
    }

    /**
     * Send message.
     *
     * @param mess the message to display
     */
    public void sendMessage(String mess) {

        DuelMsg.getInstance().sendMessage(getPlayer(), mess);
    }

    /**
     * Send error message.
     *
     * @param mess the error message to display
     * @param objects the variables
     */
    public void sendErrorMessage(String mess, Object[] objects) {

        DuelMsg.getInstance().sendErrorMessage(getPlayer(), FormatUtil.formatNoColor(mess, objects));
    }

    /**
     * Send error message.
     *
     * @param mess the error message to display
     */
    public void sendErrorMessage(String mess) {

        DuelMsg.getInstance().sendErrorMessage(getPlayer(), mess);
    }
}
