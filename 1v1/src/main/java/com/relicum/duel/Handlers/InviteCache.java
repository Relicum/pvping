package com.relicum.duel.Handlers;

import com.google.common.base.Objects;
import com.relicum.duel.Commands.DuelMsg;
import com.relicum.duel.Duel;
import com.relicum.duel.Objects.GameInvite;
import com.relicum.duel.Objects.InviteResponse;
import org.apache.commons.lang.Validate;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * InviteCache stores and manages game invites between players.
 *
 * @author Relicum
 */
public class InviteCache {

    private final static int MISSED = 12;
    InviteRemovalListener listener;
    private Map<UUID, GameInvite> invites;
    private BukkitTask checkTask;
    private boolean taskRunning = false;
    private long repeatAfter;
    private long startDelay;
    private int count = 0;


    /**
     * Instantiates a new Invite cache.
     *
     * @param plugin          the plugin
     * @param startDelay      the start delay of the repeating task in ticks.
     * @param repeatAfter     how often the task will repeat in ticks.
     * @param removalListener the removal listener
     */
    public InviteCache(long startDelay, long repeatAfter, InviteRemovalListener removalListener) {

        this.invites = new HashMap<>();
        this.repeatAfter = repeatAfter;
        this.startDelay = startDelay;

        Validate.notNull(removalListener, "InviteRemovalListener can not be null");
        Validate.isTrue(listener == null, "InviteRemovalListener has already been set");

        listener = removalListener;

    }


    InviteRemovalListener getListener() {

        return Objects.firstNonNull(listener, NullListener.INSTANCE);
    }


    /**
     * Checks if the Check Task is currently running.
     *
     * @return true and it is running, false and it isn't.
     */
    public boolean isTaskRunning() {
        return taskRunning;
    }

    /**
     * Start check task which repeatedly checks for invites that have expired.
     * <p>The start delay and evict time were set in the constructor.
     */
    public void startCheckTask() {

        if (isTaskRunning()) {
            throw new IllegalStateException("The Check Task is already running");
        }


        taskRunning = true;

        DuelMsg.getInstance().logInfoFormatted("Check task is starting...");
        count = 0;
        this.checkTask = new BukkitRunnable() {

            @Override
            public void run() {
                DuelMsg.getInstance().logInfoFormatted("[" + count + "]Check task...");
                checkInviteValidity();
            }
        }.runTaskTimer(JavaPlugin.getPlugin(Duel.class), startDelay, repeatAfter);
    }

    /**
     * Cancel check task.
     */
    public void cancelCheckTask() {

        if (isTaskRunning()) {
            checkTask.cancel();
        }

        taskRunning = false;
    }

    /**
     * Add a new {@link GameInvite} invite.
     *
     * @param invite the {@link GameInvite} to add
     */
    public void addInvite(GameInvite invite) {

        this.invites.put(invite.getUuid(), invite);
        if (!isTaskRunning()) {
            startCheckTask();
        }
    }

    /**
     * Get an invite {@link GameInvite}.
     *
     * @param uuid the uuid
     * @return the game invite
     */
    public GameInvite getInvite(UUID uuid) {

        return invites.get(uuid);
    }

    /**
     * Is the {@link UUID} contained in the map.
     *
     * @param uuid the uuid
     * @return true if it is, false if its not.
     */
    public boolean contains(UUID uuid) {

        return invites.containsKey(uuid);
    }


    /**
     * Remove invite, this updates any InviteRemovalListener with the info.
     *
     * @param uuid     the uuid of the {@link GameInvite}
     * @param response the response is the reason for removal {@link InviteResponse}
     */
    public void removeInvite(UUID uuid, InviteResponse response) {

        getListener().onRemoval(new InviteRemovalNotification(uuid.toString(), invites.remove(uuid), response));

    }


    /**
     * Total invites.
     *
     * @return the total under of currently stored invites.
     */
    public int totalInvites() {

        return invites.size();
    }

    /**
     * Check invite validity returning if they have timed out if calls {@link GameInvite#inviteTimesOut()}.
     * <p>This will automatically handle the process of invalidating the invite.
     * <p>This should be called on a repeating task, set to repeat more often than the invite timeout limit.
     */
    public void checkInviteValidity() {

        List<UUID> gameInvites = invites.values()
                                         .stream()
                                         .filter(invite -> !invite.isValid())
                                         .map(GameInvite::getUuid)
                                         .collect(Collectors.toList());
        if (gameInvites.size() > 0) {

            gameInvites.stream().forEach(invite -> removeInvite(invite, InviteResponse.TIMEOUT));
            count = 0;
        }
        else {

            count++;

            if (count > MISSED && invites.size() == 0) {

                cancelCheckTask();
                DuelMsg.getInstance().logInfoFormatted("Cache task cancelled as it has had " + MISSED + " miss's in a row");
            }
        }

    }

    /**
     * Expire all Invites and clear the internal map and setting it to null.
     */
    public void invalidateAll() {

        if (invites.size() > 0) {
            invites.values().forEach(this::logInvalid);
            invites.clear();
        }
    }

    private void logInvalid(GameInvite invite) {

        DuelMsg.getInstance().logInfoFormatted("System has expired invite from " + invite.getInviterName() + " to " + invite.getInviteeName());
    }

    /**
     * Clear all invite data.
     * <p>This does not finalise any of the invites, it purely clears the internal store.
     */
    public void clear() {

        invites.clear();
    }

    /**
     * Clean and destroy.
     * <p>Invalidates all, stops running task check, clears internal map and sets it to null.
     */
    public void cleanAndDestroy() {
        DuelMsg.getInstance().logInfoFormatted("Shutting down Invite Cache");
        invalidateAll();
        cancelCheckTask();
        clear();
        invites = null;
        listener = null;
    }


    enum NullListener implements InviteRemovalListener {
        INSTANCE;

        @Override
        public void onRemoval(InviteRemovalNotification notification) {}
    }

}
