package com.relicum.duel.Objects;

import com.massivecraft.massivecore.adapter.relicum.RankArmor;
import com.relicum.duel.Commands.DuelMsg;
import com.relicum.duel.Duel;
import com.relicum.duel.Tasks.ActionTimer;
import com.relicum.locations.SpawnPoint;
import com.relicum.pvpcore.Enums.EndReason;
import com.relicum.pvpcore.Enums.PlayerState;
import com.relicum.pvpcore.FormatUtil;
import com.relicum.pvpcore.Game.PlayerStats;
import com.relicum.pvpcore.Gamers.InventoryStore;
import com.relicum.pvpcore.Gamers.PlayerSettings;
import com.relicum.pvpcore.Gamers.WeakGamer;
import com.relicum.pvpcore.Tasks.TeleportTask;
import com.relicum.pvpcore.Tasks.UpdateInventory;
import com.relicum.titleapi.Exception.ReflectionException;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * PvpPlayer
 *
 * @author Relicum
 * @version 0.0.1
 */
@edu.umd.cs.findbugs.annotations.SuppressFBWarnings({"DLS_DEAD_LOCAL_STORE"})
public class PvpPlayer extends WeakGamer<Duel> {

    Scoreboard board;
    private PlayerState state;
    private boolean saveInv = true;
    private boolean queueing = false;
    private String name = "";
    private InventoryStore store;
    private SpawnPoint backLocation;
    private ItemStack[] lobbyArmor;
    private ItemStack[] lobbyBar;
    private List<PotionEffect> lobbyEffects;
    private PlayerStats stats;
    private Objective objective;
    private RankArmor rank;
    private int start = 10;

    /**
     * Instantiates a new PvpPlayer.
     *
     * @param paramPlayer the param player
     * @param paramPlugin the param plugin
     */
    public PvpPlayer(final Player paramPlayer, final Duel paramPlugin, SpawnPoint backLocation, RankArmor rank) {

        super(paramPlayer, paramPlugin);
        this.rank = rank;


        this.backLocation = backLocation;

        lobbyArmor = plugin.getLobbyHandler().getLobbyLoadOut().getArmor(rank);

        lobbyBar = plugin.getLobbyHandler().getLobbyLoadOut().getContents();

        lobbyEffects = new ArrayList<>(plugin.getLobbyHandler().getLobbyLoadOut().getEffects());

        stats = paramPlugin.getStatsManager().getAPlayersStats(paramPlayer.getUniqueId().toString());

        setName(paramPlayer.getName());
        // teleport(getPlugin().getServer().getWorld("world").getSpawnLocation());


        setState(PlayerState.LOBBY);

        createScoreboard();

        List<String> messages = new ArrayList<>();
        messages.add(FormatUtil.colorize("&a&lWelcome to 1v1 Zone"));
        messages.add(FormatUtil.colorize("&f&oBrought To You By"));
        messages.add(FormatUtil.colorize("&6&lRELICUM"));
        messages.add(FormatUtil.colorize("&f&lAlong with &a&lU&b&lH&c&lC &6&lZone"));


        new ActionTimer(Arrays.asList(paramPlayer), 10, Duel.get().getTitleMaker()).runTaskTimer(paramPlugin, 20l, 20l);


//        new BukkitRunnable() {
//            int c = 0;
//
//            @Override
//            public void run() {
//
//                if (c == 0) {
//                    try {
//
//                        plugin.getTitleMaker().sendResetPacket(paramPlayer);
//                        plugin.getTitleMaker().sendTimesPacket(paramPlayer, 0, 70, 0);
//                        plugin.getTitleMaker().sendTitlePacket(paramPlayer, messages.get(0));
//                        plugin.getTitleMaker().sendSubTitlePacket(paramPlayer, messages.get(1));
//
//                    }
//                    catch (ReflectionException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if (c > 0 && c < 4) {
//                    try {
//                        plugin.getTitleMaker().sendTitlePacket(paramPlayer, messages.get(0));
//                        plugin.getTitleMaker().sendSubTitlePacket(paramPlayer, messages.get(c));
//                    }
//                    catch (ReflectionException e) {
//                        e.printStackTrace();
//                    }
//                }
//                c++;
//                if (c == 4) {
//                    sendMessage("Titles ended");
//                    cancel();
//                }
//            }
//        }.runTaskTimer(plugin, 40l, 60l);


//        new BukkitRunnable() {
//            @Override
//            public void run() {
//
//
//                if (start == 10) {
//                    board.resetScores(FormatUtil.colorize("&5&lGame Starts: " + start));
//                    start--;
//                    objective.getScore(FormatUtil.colorize("&5&lGame Starts: 0" + start)).setScore(15);
//                }
//                else if (start > 0 && start < 10) {
//                    board.resetScores(FormatUtil.colorize("&5&lGame Starts: 0" + start));
//                    start--;
//                    objective.getScore(FormatUtil.colorize("&5&lGame Starts: 0" + start)).setScore(15);
//                }
//                if (start == 0) {
//                    sendMessage("Countdown ended");
//                    cancel();
//                }
//
//            }
//        }.runTaskTimer(plugin, 200l, 20l);
    }

    public void destroy() {

        clearInventory();
        restorePlayerSettings();
        updateScoreboard(true);
        player.clear();
        System.out.println("PvpPlayer Object destroyed");
    }

    /**
     * Gets players rank.
     *
     * @return the rank
     */
    public String getRank() {

        return rank.name();
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
                stats.incrementWin();
                plugin.getStatsManager().removeAndSave(getUuid().toString());
                teleport(backLocation.toLocation());

                break;
            }
            case LOGGED: {
                try {

                    Player p = plugin.getServer().getPlayer(getUuid());
                    p.teleport(backLocation.toLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                    p.getInventory().setArmorContents(store.getArmor());
                    p.getInventory().setContents(store.getContents());
                    for (PotionEffect effect : p.getActivePotionEffects()) {
                        p.removePotionEffect(effect.getType());
                    }
                    p.addPotionEffects(store.getEffects());
                    store.getSettings().restore(p);
                    p.setScoreboard(plugin.getServer().getScoreboardManager().getNewScoreboard());
                    stats.incrementLoss();
                    plugin.getStatsManager().removeAndSave(p.getUniqueId().toString());
                    store = null;

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Tried to reset player settings for a player who quit or logged will part of 1v1");
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

    public final void setState(PlayerState playerState) {

        this.state = playerState;

        if (state.equals(PlayerState.LOBBY)) {
            stateLobby();
        }
        else if (state.equals(PlayerState.QUIT)) {
            stateGameEnd();
        }
    }

    public void stateLobby() {

        if (saveInv) {

            savePlayerSettings();
            clearInventory();
            applyLobbyInventory();
            UpdateInventory.now(getPlayer(), plugin);
            saveInv = false;
        }
        else {
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

        board = getPlugin().getServer().getScoreboardManager().getNewScoreboard();
        objective = board.registerNewObjective(getName(), "dummy");
        objective.setDisplayName(FormatUtil.format("&6&lNoxiousPVP &a&l1v1", new Object[0]));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        getPlayer().setScoreboard(board);
        setScoreboardInfo(board);
    }

    private void setScoreboardInfo(Scoreboard scoreboard) {

        //objective = scoreboard.getObjective(getName());
        objective.getScore(FormatUtil.colorize("&5&lGame Starts: " + start)).setScore(15);
        //objective.getScore(FormatUtil.colorize("&a&l"+start)).setScore(14);
        objective.getScore("§r").setScore(14);
        objective.getScore(FormatUtil.format("&b&l{0}'s Stats", new Object[]{getName()})).setScore(13);
        objective.getScore("§r").setScore(12);
        objective.getScore(FormatUtil.format("&a&lWins:", new Object[0])).setScore(11);
        objective.getScore(String.valueOf(stats.getWins())).setScore(10);
        objective.getScore("§a").setScore(9);
        objective.getScore(FormatUtil.format("&c&lLosses:", new Object[0])).setScore(8);
        objective.getScore(FormatUtil.format("&f{0}", new Object[]{String.valueOf(stats.getLosses())})).setScore(7);
        objective.getScore("§b").setScore(6);
        objective.getScore(FormatUtil.format("&d&lWinstreak:", new Object[0])).setScore(5);
        objective.getScore(FormatUtil.format("&r{0}", new Object[]{String.valueOf(stats.getStreak())})).setScore(4);
        objective.getScore("§c").setScore(3);
        objective.getScore(FormatUtil.format("&6&lBest Streak:", new Object[0])).setScore(2);
        objective.getScore(FormatUtil.format("&f&f{0}", new Object[]{String.valueOf(stats.getHighestStreak())})).setScore(1);

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


        this.store = new InventoryStore(pl.getInventory(), pl.getActivePotionEffects(), PlayerSettings.save(pl));


        for (PotionEffect effect : pl.getActivePotionEffects()) {
            pl.removePotionEffect(effect.getType());
        }


    }

    /**
     * Clear inventory.
     */
    @SuppressFBWarnings({"DLS_DEAD_LOCAL_STORE"})
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

        Player pl = getPlayer();
        //playerInventory.setArmorContents(lobbyArmor);
        //playerInventory.setContents(lobbyBar);
        pl.getInventory().setArmorContents(lobbyArmor);
        pl.getInventory().setContents(lobbyBar);
        plugin.getLobbyHandler().getLobbyLoadOut().getSettings().apply(pl);
        for (PotionEffect effect : pl.getActivePotionEffects()) {
            pl.removePotionEffect(effect.getType());
        }

        lobbyEffects.forEach(pl::addPotionEffect);

        sendMessage("Lobby effect should be applied");


    }

    /**
     * Restore player inventory and null the storage object.
     */
    public void restorePlayerSettings() {

        Player pl = getPlayer();

        pl.getInventory().setArmorContents(store.getArmor().clone());
        pl.getInventory().setContents(store.getContents().clone());
        for (PotionEffect effect : pl.getActivePotionEffects()) {
            pl.removePotionEffect(effect.getType());
        }
        pl.addPotionEffects(store.getEffects());
        store.getSettings().restore(pl);
        store = null;
    }

    public boolean isGod() {

        return state.equals(PlayerState.LOBBY) || state.equals(PlayerState.QUEUED);
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
     *                 remove them from the queue.
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

    public final void setName(String name) {

        this.name = name;
    }

    /**
     * Send message.
     *
     * @param mess    the message to display
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
     * @param mess    the error message to display
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

    public void sendActionMessage(String mess) {

        try {
            plugin.getTitleMaker().sendActionBar(getPlayer(), mess);
        }
        catch (ReflectionException e) {
            e.printStackTrace();
        }
    }
}
