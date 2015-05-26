package com.relicum.duel.Objects;

import com.relicum.duel.Commands.DuelMsg;
import com.relicum.duel.Duel;
import com.relicum.pvpcore.Kits.LoadOut;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

/**
 * GameInvite hols details of players challenging other players to 1v1's
 *
 * @author Relicum
 * @version 0.0.1
 */
public class GameInvite {

    private Duel plugin;
    private UUID uuid;
    private PvpPlayer inviter;
    private PvpPlayer invitee;
    private LoadOut loadOut;
    private long timeout;
    private boolean active;

    /**
     * Instantiates a new Game invite.
     *
     * @param plugin  the plugin
     * @param inviter the {@link PvpPlayer} submitting the invite
     * @param invitee the {@link PvpPlayer} the invite is sent to.
     * @param loadOut the {@link LoadOut} the game will use.
     */
    public GameInvite(Duel plugin, PvpPlayer inviter, PvpPlayer invitee, @Nullable LoadOut loadOut) {
        this.plugin = plugin;
        this.setInviter(inviter);
        this.setInvitee(invitee);
        this.setLoadOut(loadOut);
        this.setTimeout(System.currentTimeMillis() + 10000);
        this.setUuid(UUID.randomUUID());
        this.active = true;
    }

    /**
     * Has the invite it timed out or is it still valid.
     *
     * @return true and its still valid, false and it has timed out.
     */
    public boolean isValid() {

        return System.currentTimeMillis() <= getTimeout();
    }

    /**
     * Gets {@link PvpPlayer} that submitted the invite.
     *
     * @return the inviter {@link PvpPlayer}
     */
    public PvpPlayer getInviter() {
        return inviter;
    }

    public void setInviter(PvpPlayer inviter) {
        this.inviter = inviter;
    }

    /**
     * Gets {@link PvpPlayer} that was sent the invite.
     *
     * @return the invitee
     */
    public PvpPlayer getInvitee() {
        return invitee;
    }

    public void setInvitee(PvpPlayer invitee) {
        this.invitee = invitee;
    }

    /**
     * Gets {@link LoadOut} the game will use.
     *
     * @return the {@link LoadOut}
     */
    public LoadOut getLoadOut() {
        return loadOut;
    }

    public void setLoadOut(@Nullable LoadOut loadOut) {
        this.loadOut = loadOut;
    }

    /**
     * Gets timeout.
     *
     * @return the timeout
     */
    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void invalidatedByInviter() {

        process(Response.CANCELED);
    }

    public void invalidatedByInvitee() {

        process(Response.DECLINED);
    }

    public void inviteTimesOut() {

        new BukkitRunnable() {
            @Override
            public void run() {
                process(Response.TIMEOUT);
            }
        }.runTask(plugin);


    }

    public void accept() {

        process(Response.ACCEPTED);

    }

    public void inviterOffline() {

        process(Response.INVITER_OFFLINE);
    }

    public void inviteeOffline() {

        process(Response.INVITEE_OFFLINE);
    }

    public void systemInvalidate() {

        process(Response.SYSTEM);
    }

    private void process(Response response) {

        active = false;

        switch (response) {
            case ACCEPTED: {
                getInviter().sendMessage("&6" + getInvitee().getName() + "&a has accepted your challenge");
                getInvitee().sendMessage("You have accepted the invite from &6" + getInviter().getName());

                break;
            }
            case TIMEOUT: {
                getInviter().sendErrorMessage("Your invite to &6" + getInvitee().getName() + " has expired");
                plugin.getInviteCache().removeInvite(getUuid());
                break;
            }
            case DECLINED: {
                getInviter().sendErrorMessage("&6" + getInvitee().getName() + "&a has declined your challenge");
                getInvitee().sendMessage("You have declined the invite from &6" + getInviter().getName());
                break;
            }
            case CANCELED: {
                getInviter().sendMessage("You have canceled your invite to &6" + getInvitee().getName());
                getInvitee().sendErrorMessage("&6" + getInviter().getName() + "&a has cancelled their invite");
                break;
            }
            case INVITER_OFFLINE: {
                getInvitee().sendErrorMessage("&6" + getInviter().getName() + "&a has gone offline invite is cancelled");
                break;
            }
            case INVITEE_OFFLINE: {
                getInviter().sendErrorMessage("&6" + getInvitee().getName() + "&a has gone offline invite is cancelled");
                break;
            }
            case SYSTEM: {
                plugin.getLogger().info("System has expired invite from " + getInviter().getName() + " to " + getInvitee().getName());
                plugin.getInviteCache().removeInvite(getUuid());
                break;
            }
            default: {

                DuelMsg.getInstance().logSevereFormatted("Unknown invite Response type, this is a bug");
            }

        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameInvite)) return false;
        GameInvite that = (GameInvite) o;
        return Objects.equals(getTimeout(), that.getTimeout()) &&
                       Objects.equals(getUuid(), that.getUuid()) &&
                       Objects.equals(getInviter(), that.getInviter()) &&
                       Objects.equals(getInvitee(), that.getInvitee()) &&
                       Objects.equals(getLoadOut(), that.getLoadOut());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getInviter(), getInvitee(), getLoadOut(), getTimeout());
    }

    @Override
    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this)
                       .add("invitee", getInvitee().toString())
                       .add("inviter", getInviter().toString())
                       .add("loadOut", getLoadOut().toString())
                       .add("timeout", getTimeout())
                       .add("uuid", getUuid().toString())
                       .toString();
    }

    public enum Response {

        ACCEPTED,
        DECLINED,
        TIMEOUT,
        CANCELED,
        INVITER_OFFLINE,
        INVITEE_OFFLINE,
        SYSTEM,
    }
}
