package com.relicum.duel.Handlers;

import com.google.common.base.Objects;
import com.relicum.duel.Duel;
import com.relicum.duel.Objects.GameInvite;
import org.apache.commons.lang.Validate;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * InviteCache stores and manages game invites between players.
 *
 * @author Relicum
 */
public class InviteCache {

    enum NullListener implements InviteRemovalListener {
        INSTANCE;

        @Override
        public void onRemoval(InviteRemovalNotification notification) {}
    }


    InviteRemovalListener listener;
    private Map<UUID, GameInvite> invites;
    private Duel plugin;
    private BukkitTask checkTask;
    private boolean taskRunning = false;


    public InviteCache(Duel plugin) {
        this.plugin = plugin;
        this.invites = new HashMap<>();

    }

    public InviteCache(Duel plugin, long delay, long period) {
        this.plugin = plugin;
        this.invites = new HashMap<>();
        startCheckTask(delay, period);
    }

    public InviteCache(Duel plugin, long delay, long period, InviteRemovalListener removalListener) {
        this.plugin = plugin;
        this.invites = new HashMap<>();
        Validate.notNull(removalListener, "InviteRemovalListener can not be null");
        Validate.isTrue(listener == null, "InviteRemovalListener has already been set");

        listener = removalListener;

        startCheckTask(delay, period);
    }

    InviteRemovalListener getListener() {

        return Objects.firstNonNull(listener, NullListener.INSTANCE);
    }


    /**
     * Start check task which repeatedly checks for invites that have expired.
     *
     * @param delay  the delay in ticks before the task starts
     * @param period the period in ticks the task will repeat.
     */
    public void startCheckTask(long delay, long period) {

        System.out.println("Check task is starting...");

        if (isTaskRunning()) {
            throw new IllegalStateException("The Check Task is already running");
        }

        taskRunning = true;

        this.checkTask = new BukkitRunnable() {

            @Override
            public void run() {
                System.out.println("Check task...");
                checkInviteValidity();
            }
        }.runTaskTimer(plugin, delay, period);

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
     * Remove invite.
     *
     * @param uuid the uuid
     * @return the {@link GameInvite}
     */
    public GameInvite removeInvite(UUID uuid) {

        return invites.remove(uuid);
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

        invites.values()
                .stream()
                .filter(invite -> !invite.isValid())
                .forEach(invite1 -> getListener().onRemoval(new InviteRemovalNotification(invite1.getUuid().toString(), invite1)));

    }


    /**
     * Invalidate all invites.
     */
    public void invalidateAll() {

        invites.values().forEach(GameInvite::systemInvalidate);

    }

    /**
     * Clear all invite data.
     * <p>This does not finalise any of the invites, it purely clears the internal store.
     */
    public void clear() {

        invites.clear();
    }

    /**
     * Checks if the Check Task is currently running.
     *
     * @return true and it is running, false and it isn't.
     */
    public boolean isTaskRunning() {
        return taskRunning;
    }

}
