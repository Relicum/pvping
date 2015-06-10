package com.relicum.duel.Objects;

import com.massivecraft.massivecore.adapter.relicum.RankArmor;
import com.relicum.duel.Commands.DuelMsg;
import com.relicum.duel.Duel;
import com.relicum.duel.Menus.CloseItem;
import com.relicum.duel.Menus.PlayerQueueMenu;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
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
    private List<UUID> sentInvites;
    private List<UUID> invites;
    private Map<UUID, String> invitesToPlayer;
    private long inviteTimeOut = 0;
    private PlayerQueueMenu queueMenu;

    /**
     * Instantiates a new PvpPlayer.
     *
     * @param paramPlayer the param player
     * @param paramPlugin the param plugin
     */
    public PvpPlayer(final Player paramPlayer, final Duel paramPlugin, SpawnPoint backLocation, RankArmor rank) {

        super(paramPlayer, paramPlugin);
        this.rank = rank;
        this.sentInvites = new ArrayList<>();
        this.invites = new ArrayList<>();
        this.invitesToPlayer = new HashMap<>();
        setOnline(true);

        this.backLocation = backLocation;

        lobbyArmor = plugin.getLobbyHandler().getLobbyLoadOut().getArmor(rank);

        lobbyBar = plugin.getLobbyHandler().getLobbyLoadOut().getContents();

        lobbyEffects = new ArrayList<>(plugin.getLobbyHandler().getLobbyLoadOut().getEffects());

        stats = paramPlugin.getStatsManager().getAPlayersStats(paramPlayer.getUniqueId().toString());

        setName(paramPlayer.getName());
        // teleport(getPlugin().getServer().getWorld("world").getSpawnLocation());


        setState(PlayerState.LOBBY);

        queueMenu = plugin.getMenuManager().getInvitesMenu();
        queueMenu.addMenuItem(new CloseItem(8), 8);
        queueMenu.addMenuItem(plugin.getSkullHandler().getHeadItem(getName(), 0, paramPlayer.getUniqueId().toString()), 0);

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

    public PlayerQueueMenu getQueueMenu() {
        return queueMenu;
    }


    //************************************** MANAGE GAME INVITES ******************************************//

    public boolean canSendInvite() {

        return inLobby() && (sentInvites.size() == 0 || (sentInvites.size() > 0 && !(System.currentTimeMillis() <= inviteTimeOut)));

    }

    /**
     * Can this user send invite to another player.
     * <p>This checks both players at the same time.
     *
     * @param player the player {@link PvpPlayer} to check aganist.
     * @return the {@link PvpResponse}
     */
    public PvpResponse canSendInviteTo(PvpPlayer player) {

        if (!canSendInvite()) {

            return new PvpResponse(PvpResponse.ResponseType.FAILURE, "You must wait 10 seconds between sending invites");
        }


        for (UUID invite : invites) {
            if (player.getSentInvites().contains(invite)) {
                return new PvpResponse(PvpResponse.ResponseType.FAILURE, player.getName() + " already has an invite open for you");
            }
        }


        for (UUID invite : player.getInvites()) {
            if (getSentInvites().contains(invite)) {

                return new PvpResponse(PvpResponse.ResponseType.FAILURE, "You already have an invite open for " + player.getName());
            }
        }


        if (!maxInvitesSent()) {

            return new PvpResponse(PvpResponse.ResponseType.FAILURE, "You do not have any spare slots for sending invites");
        }

        if (!player.maxInvitesReceived()) {

            return new PvpResponse(PvpResponse.ResponseType.FAILURE, player.getName() + " does not have enough slots to receive invites");
        }


        return new PvpResponse(PvpResponse.ResponseType.SUCCESS, null);
    }

    public List<UUID> getSentInvites() {
        return sentInvites;
    }

    public boolean maxInvitesSent() {

        return sentInvites.size() < 9;
    }

    public boolean maxInvitesReceived() {

        return invites.size() < 9;
    }

    /**
     * Add invites that other players send to you.
     *
     * @param invite the invite {@link GameInvite}
     * @return the {@link PvpResponse}
     */
    public PvpResponse addFromInvite(GameInvite invite) {

        if (invites.size() > 8) {

            return new PvpResponse(PvpResponse.ResponseType.FAILURE, "Max of 8 invites reached,decline some before being able to accept more");

        }

        try {

            invites.add(invite.getUuid());
            invitesToPlayer.put(invite.getUuid(), invite.getInviterName());
            queueMenu.addMenuItem(plugin.getSkullHandler().getHeadItem(invite.getInviterName(), 1, invite.getInviterId()), 1);
            queueMenu.updateMenu();
            return new PvpResponse(PvpResponse.ResponseType.SUCCESS, null);

        }
        catch (Exception e) {

            e.printStackTrace();
            return new PvpResponse(PvpResponse.ResponseType.FAILURE, "Error adding invite from &6" + invite.getInviterName());
        }
    }


    /**
     * Add invites that you have sent to other players.
     *
     * @param invite the {@link GameInvite}
     * @return the {@link PvpResponse}
     */
    public PvpResponse addInvite(GameInvite invite) {

        if (!canSendInvite()) {

            return new PvpResponse(PvpResponse.ResponseType.FAILURE, "You must wait 10 seconds between sending invites");
        }

        inviteTimeOut = System.currentTimeMillis() + 10000;

        sentInvites.add(invite.getUuid());

        return new PvpResponse(PvpResponse.ResponseType.SUCCESS, null);
    }

    public PvpResponse removeInvite(GameInvite invite, InviteResponse response) {

        if (!sentInvites.contains(invite.getUuid())) {
            return new PvpResponse(PvpResponse.ResponseType.FAILURE, "Invite not found");
        }

        if (sentInvites.remove(invite.getUuid())) {

            plugin.getInviteCache().removeInvite(invite.getUuid(), response);

            inviteTimeOut = 0;

            return new PvpResponse(PvpResponse.ResponseType.SUCCESS, null);
        }


        else {
            inviteTimeOut = 0;
            return new PvpResponse(PvpResponse.ResponseType.SUCCESS, "Only a success a it wasn't found in the cache it is still same result");
        }


    }

    /**
     * Remove invite that has been sent to you.
     *
     * @param invite {@link GameInvite}
     */
    public void removeFromInvite(GameInvite invite) {
        if (invites.remove(invite.getUuid())) {
            invitesToPlayer.remove(invite.getUuid(), invite.getInviterName());
            queueMenu.removeMenuItem(1);
            queueMenu.updateMenu();
            System.out.println("Removed Invitee Invite");
        }
        else {
            System.out.println("FAILED to remove Invitee invite");
        }

        if (inviteTimeOut != 0) {
            inviteTimeOut = 0;
        }
    }

    /**
     * Remove invites that you have sent to others.
     *
     * @param invite {@link GameInvite}
     */
    public void removeInvite(GameInvite invite) {

        if (sentInvites.remove(invite.getUuid())) {
            System.out.println("Removed Inviter Invite");
        }
        else {
            System.out.println("FAILED to remove Inviter invite");
        }
        if (inviteTimeOut != 0) {
            inviteTimeOut = 0;
        }
    }

    /**
     * Clear invites that have been sent to you.
     */
    public void clearInvites() {
        invites.clear();
    }

    /**
     * Get all invites that have been sent to you.
     *
     * @return the invites
     */
    public List<UUID> getInvites() {
        return invites;
    }

    public long getInviteTimeOut() {

        return inviteTimeOut;
    }

    public int getNumberInvites() {

        return sentInvites.size();
    }

    public void resetInviteTimeOut() {

        inviteTimeOut = 0;
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

                ListIterator<UUID> iterator = sentInvites.listIterator();
                while (iterator.hasNext()) {
                    UUID invite = iterator.next();
                    plugin.getInviteCache().removeInvite(invite, InviteResponse.CANCELED);
                    iterator.remove();
                }

                invites.stream()
                        .filter(uuid -> plugin.getInviteCache().contains(uuid))
                        .forEach(uuid -> plugin.getInviteCache().getInvite(uuid).setActive(false));

                invites.clear();

                // sentInvites.clear();

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

                    ListIterator<UUID> iterator = sentInvites.listIterator();
                    while (iterator.hasNext()) {
                        UUID invite = iterator.next();
                        plugin.getInviteCache().removeInvite(invite, InviteResponse.INVITER_OFFLINE);
                        iterator.remove();
                    }

                    invites.stream()
                            .filter(uuid -> plugin.getInviteCache().contains(uuid))
                            .forEach(uuid -> plugin.getInviteCache().getInvite(uuid).setActive(false));

                    invites.clear();

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


}
