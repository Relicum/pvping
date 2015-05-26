package com.relicum.duel.Objects;

import com.massivecraft.massivecore.adapter.relicum.RankArmor;
import com.relicum.duel.Commands.DuelMsg;
import com.relicum.duel.Duel;
import com.relicum.duel.Tasks.ActionTimer;
import com.relicum.locations.SpawnPoint;
import com.relicum.pvpcore.Enums.EndReason;
import com.relicum.pvpcore.Enums.PlayerState;
import com.relicum.pvpcore.Enums.RestoreReason;
import com.relicum.pvpcore.FormatUtil;
import com.relicum.pvpcore.Game.PlayerStats;
import com.relicum.pvpcore.Gamers.InventoryStore;
import com.relicum.pvpcore.Gamers.PlayerSettings;
import com.relicum.pvpcore.Gamers.PvpResponse;
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
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
    private boolean restore = false;
    private RestoreReason restoreReason;
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
    private PvpGame game;
    private int start = 10;
    private boolean online = false;

    /**
     * Instantiates a new PvpPlayer.
     *
     * @param paramPlayer the param player
     * @param paramPlugin the param plugin
     */
    public PvpPlayer(final Player paramPlayer, final Duel paramPlugin, SpawnPoint backLocation, RankArmor rank) {

        super(paramPlayer, paramPlugin);
        this.rank = rank;

        setOnline(true);

        this.backLocation = backLocation;

        lobbyArmor = plugin.getLobbyHandler().getLobbyLoadOut().getArmor(rank);

        lobbyBar = plugin.getLobbyHandler().getLobbyLoadOut().getContents();

        lobbyEffects = new ArrayList<>(plugin.getLobbyHandler().getLobbyLoadOut().getEffects());

        stats = paramPlugin.getStatsManager().getAPlayersStats(paramPlayer.getUniqueId().toString());

        setName(paramPlayer.getName());
        // teleport(getPlugin().getServer().getWorld("world").getSpawnLocation());


        setState(PlayerState.LOBBY);


//        List<String> messages = new ArrayList<>();
//        messages.add(FormatUtil.colorize("&a&lWelcome to 1v1 Zone"));
//        messages.add(FormatUtil.colorize("&f&oBrought To You By"));
//        messages.add(FormatUtil.colorize("&6&lRELICUM"));
//        messages.add(FormatUtil.colorize("&f&lAlong with &a&lU&b&lH&c&lC &6&lZone"));


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


    //************************************** MANAGE PLAYER STATE ******************************************//

    /**
     * Get the current player state {@link PlayerState}
     *
     * @return the current {@link PlayerState}
     */
    public PlayerState getState() {

        return state;
    }


    public final PvpResponse registerState(PlayerState paramState) {

        return setState(paramState);
    }

    /**
     * Set the current {@link PlayerState} of the player and update accordingly.
     *
     * @param playerState the {@link PlayerState} to set.
     */
    private PvpResponse setState(PlayerState playerState) {

        this.state = playerState;

        if (inLobby()) {
            if (saveInv) {
                firstJoinedLobby();
            }
            else {
                applyLobbyState();
            }

        }

        else if (isLeaving()) {

            setRestore(true, RestoreReason.LEAVE_CMD);


        }
        else if (state.equals(PlayerState.QUIT)) {
            stateGameEnd();
        }

        return new PvpResponse(PvpResponse.ResponseType.SUCCESS, null);
    }


    /**
     * Check if the player is in the lobby.
     *
     * @return true and they are in the lobby, false and they are not.
     */
    public final boolean inLobby() {

        return state == PlayerState.LOBBY;
    }

    /**
     * Check if the player is in the Pre Game state.
     * <p>This is when they are no longer considered to be in the lobby but are not in a running game.
     *
     * @return true if they are, false if they aren't.
     */
    public final boolean isPreGame() {

        return state == PlayerState.PREGAME;
    }

    /**
     * Check if the player is in a game.
     *
     * @return true if they are, false if they aren't
     */
    public final boolean inGame() {

        return state == PlayerState.INGAME;
    }

    /**
     * Checks if the player is in the Post Game state.
     * <p>This is when the game has ended but they have not returned to the lobby yet.
     *
     * @return true if they are, false if they aren't
     */
    public final boolean isPostGame() {

        return state == PlayerState.POSTGAME;
    }

    /**
     * Checks if the player is in the leaving state.
     * <p>This is when a player has chosen the leave 1v1
     *
     * @return true if they are, false if they aren't
     */
    public final boolean isLeaving() {

        return state == PlayerState.LEAVING;
    }

    /**
     * Checks if the player has quit, this could be for a number of reasons.
     * <p>They rage quit, they timed out, kicked or banned etc.
     * <p>But the definition of hasQuit is that they did not leave in a normal way.
     *
     * @return true and they have quit, false if they have not.
     */
    public final boolean hasQuit() {

        return state == PlayerState.QUIT;
    }


    /**
     * Sets in lobby.
     */
    public void setInLobby() {

        setState(PlayerState.LOBBY);
    }

    /**
     * Set pre game.
     */
    public void setPreGame() {

        setState(PlayerState.PREGAME);
    }

    /**
     * Sets in game.
     */
    public void setInGame() {

        setState(PlayerState.INGAME);
    }

    /**
     * Set post game.
     */
    public void setPostGame() {

        setState(PlayerState.POSTGAME);
    }

    /**
     * Set leaving.
     */
    public void setLeaving() {

        setState(PlayerState.LEAVING);
    }


    /**
     * Sets has quit.
     */
    public void setHasQuit() {

        setState(PlayerState.QUIT);
    }


    /**
     * Should the player have their original inventory and setting restored that they started with.
     * <p>This should only occure if they are leaving or have quit 1v1
     *
     * @return true and player will be restored, false and they won't.
     */
    public final boolean isRestore() {

        return restore;
    }

    /**
     * Sets to true for the player to have their original inventory and settings restored.
     * <p>Only set to true if the player is leaving or has quit 1v1.
     *
     * @param restore set true and player will be restored, false and they won't and it may cancel a previous request to restore.
     * @param reason  the {@link RestoreReason}
     */
    public void setRestore(boolean restore, RestoreReason reason) {
        this.restore = restore;
        this.restoreReason = reason;
    }

    /**
     * Gets restore reason, will have a value of {@link RestoreReason#VOID} if it has not been set.
     *
     * @return the {@link RestoreReason}
     */
    public RestoreReason getRestoreReason() {

        if (restoreReason != null) {
            return restoreReason;
        }
        else {
            return RestoreReason.VOID;
        }
    }


    public void firstJoinedLobby() {

        Player pl = getPlayer();

        savePlayerSettings(getPlayer());

        applyLobbyInventory(pl);


        saveInv = false;

        createScoreboard();
    }

    public void applyLobbyState() {


        updateScoreboard();
        clearInventory();
        applyLobbyInventory();
        UpdateInventory.now(getPlayer(), plugin);


    }

    /**
     * Save player inventory,potions and settings.
     */
    public void savePlayerSettings() {

        Player pl = getPlayer();
        savePlayerSettings(pl);

    }


    /**
     * Save player inventory,potions and settings and clear the inventory.
     *
     * @param player the {@link Player} to save the settings for
     */
    public void savePlayerSettings(Player pl) {

        this.store = new InventoryStore(pl.getInventory(), pl.getActivePotionEffects(), PlayerSettings.save(pl));

        pl.getActivePotionEffects()
                .stream()
                .map(PotionEffect::getType)
                .forEach(pl::removePotionEffect);

//        for (PotionEffect effect : pl.getActivePotionEffects()) {
//            pl.removePotionEffect(effect.getType());
//        }

        clearInventory(pl);

    }

    /**
     * Clear inventory.
     */
    @SuppressFBWarnings({"DLS_DEAD_LOCAL_STORE"})
    public void clearInventory() {
        clearInventory(getPlayer());
    }

    /**
     * Clear inventory.
     */
    @SuppressFBWarnings({"DLS_DEAD_LOCAL_STORE"})
    public void clearInventory(Player player) {

        PlayerInventory inv = player.getInventory();
        player.closeInventory();
        inv.setArmorContents(new ItemStack[4]);
        inv.clear();

    }

    /**
     * Apply lobby inventory.
     */
    public void applyLobbyInventory() {

        applyLobbyInventory(getPlayer());

    }

    /**
     * Apply lobby inventory.
     *
     * @param pl the {@link Player} to apply it to
     */
    public void applyLobbyInventory(Player pl) {

        setMetaDataToLobby(pl);
        pl.getInventory().setArmorContents(lobbyArmor.clone());
        pl.getInventory().setContents(lobbyBar.clone());
        plugin.getLobbyHandler().getLobbyLoadOut().getSettings().apply(pl);
        pl.addPotionEffects(lobbyEffects);

        updateInventory(pl);


    }

    /**
     * Update inventory.
     *
     * @param player the {@link Player}
     */
    public void updateInventory(Player player) {

        UpdateInventory.now(player, getPlugin());
    }

    /**
     * Update inventory.
     */
    public void updateInventory() {

        updateInventory(getPlayer());
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
                Player p = getPlayer();
                //p.removeMetadata(Duel.META_KEY, plugin);
                clearInventory();
                restorePlayerSettings();
                updateScoreboard(true);
                UpdateInventory.now(p, plugin);
                stats.incrementWin();
                plugin.getStatsManager().removeAndSave(getUuid().toString());
                teleport(backLocation.toLocation());

                break;
            }
            case LOGGED: {
                try {

                    Player p = plugin.getServer().getPlayer(getUuid());
                    p.removeMetadata(Duel.META_KEY, plugin);
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

    public UUID getGameId() {

        return game.getUuid();
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
     * Restore player inventory and null the storage object.
     */
    public void restorePlayerSettings() {

        Player pl = getPlayer();

        if (hasMetaData(pl)) {
            pl.removeMetadata(Duel.META_KEY, plugin);
            System.out.println("Removing Metadata from restore");
        }

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

        return state == PlayerState.LOBBY;
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
        if (isOnline()) {
            DuelMsg.getInstance().sendMessage(getPlayer(), FormatUtil.formatNoColor(mess, objects));
        }
    }

    /**
     * Send message.
     *
     * @param mess the message to display
     */
    public void sendMessage(String mess) {
        if (isOnline()) {
            DuelMsg.getInstance().sendMessage(getPlayer(), mess);
        }
    }

    /**
     * Send error message.
     *
     * @param mess    the error message to display
     * @param objects the variables
     */
    public void sendErrorMessage(String mess, Object[] objects) {
        if (isOnline()) {
            DuelMsg.getInstance().sendErrorMessage(getPlayer(), FormatUtil.formatNoColor(mess, objects));
        }
    }

    /**
     * Send error message.
     *
     * @param mess the error message to display
     */
    public void sendErrorMessage(String mess) {
        if (isOnline()) {
            DuelMsg.getInstance().sendErrorMessage(getPlayer(), mess);
        }
    }

    /**
     * Send action bar message to the player.
     *
     * @param mess the message to send
     */
    public void sendActionMessage(String mess) {
        if (isOnline()) {
            try {
                plugin.getTitleMaker().sendActionBar(getPlayer(), mess);
            }
            catch (ReflectionException e) {
                e.printStackTrace();
            }
        }
    }


    public void destroy() {

        clearInventory();
        restorePlayerSettings();
        updateScoreboard(true);
        player.clear();
        System.out.println("PvpPlayer Object destroyed");
    }

    /**
     * Gets if the player is currently online.
     *
     * @return true if they are online false if not..
     */
    public boolean isOnline() {

        return online;
    }

    /**
     * Sets players online status.
     *
     * @param online set true if they are online, or false if they are offline.
     */
    public void setOnline(boolean online) {
        this.online = online;
    }

    /**
     * Has the player got metadata attached to them.
     *
     * @param player the player
     * @return true if they do false if not.
     */
    public boolean hasMetaData(Player player) {

        return player.hasMetadata(Duel.META_KEY);
    }

    /**
     * Check and get metadata, if they do not have any the returned int will be -1.
     *
     * @return the metadata int, 1 and there in lobby, 2 they are in game, -1 and no meta is set.
     */
    public int checkAndGetMeta() {

        Player player = getPlayer();

        if (hasMetaData(player)) {
            System.out.println("Check and get is good to go");
            return player.getMetadata(Duel.META_KEY).get(0).asInt();
        }

        return -1;
    }

    /**
     * Remove meta data.
     */
    public void removeMetaData() {

        removeMetaData(getPlayer());
    }

    /**
     * Remove meta data.
     */
    public void removeMetaData(Player pl) {

        pl.removeMetadata(Duel.META_KEY, plugin);
    }

    /**
     * Set meta data to lobby.
     */
    public void setMetaDataToLobby() {

        setMetaDataToLobby(getPlayer());
    }

    /**
     * Set meta data to lobby.
     */
    public void setMetaDataToLobby(Player pl) {

        pl.setMetadata(Duel.META_KEY, new FixedMetadataValue(plugin, 1));
        System.out.println("Setting meta to lobby");
    }

    /**
     * Set meta data to game.
     */
    public void setMetaDataToGame() {

        setMetaDataToGame(getPlayer());
    }

    /**
     * Set meta data to game.
     */
    public void setMetaDataToGame(Player pl) {

        pl.setMetadata(Duel.META_KEY, new FixedMetadataValue(plugin, 2));
        System.out.println("Setting meta to game");
    }


}
