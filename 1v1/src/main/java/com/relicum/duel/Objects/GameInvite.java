package com.relicum.duel.Objects;

import com.relicum.duel.Duel;
import com.relicum.pvpcore.Kits.LoadOut;

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

    /**
     * Instantiates a new Game invite.
     *
     * @param plugin  the plugin
     * @param inviter the {@link PvpPlayer} submitting the invite
     * @param invitee the {@link PvpPlayer} the invite is sent to.
     * @param loadOut the {@link LoadOut} the game will use.
     */
    public GameInvite(Duel plugin, PvpPlayer inviter, PvpPlayer invitee, LoadOut loadOut) {
        this.plugin = plugin;
        this.inviter = inviter;
        this.invitee = invitee;
        this.loadOut = loadOut;
        this.timeout = System.currentTimeMillis() + 10000;
        this.uuid = UUID.randomUUID();
    }

    /**
     * Has the invite it timed out or is it still valid.
     * <p>When this method is called and its found the invite is invalid it will automatically deal with informing players etc.
     *
     * @return true and its still valid, false and it has timed out.
     */
    public boolean isValid() {

        if (System.currentTimeMillis() > timeout) {

            inviteTimesOut();
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Gets {@link PvpPlayer} that submitted the invite.
     *
     * @return the inviter {@link PvpPlayer}
     */
    public PvpPlayer getInviter() {
        return inviter;
    }

    /**
     * Gets {@link PvpPlayer} that was sent the invite.
     *
     * @return the invitee
     */
    public PvpPlayer getInvitee() {
        return invitee;
    }

    /**
     * Gets {@link LoadOut} the game will use.
     *
     * @return the {@link LoadOut}
     */
    public LoadOut getLoadOut() {
        return loadOut;
    }

    /**
     * Gets timeout.
     *
     * @return the timeout
     */
    public long getTimeout() {
        return timeout;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void invalidatedByInviter() {

    }

    public void invalidatedByInvitee() {


    }

    public void inviteTimesOut() {


    }

    public void accept() {


    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameInvite)) return false;
        GameInvite that = (GameInvite) o;
        return Objects.equals(timeout, that.timeout) &&
                       Objects.equals(uuid, that.uuid) &&
                       Objects.equals(inviter, that.inviter) &&
                       Objects.equals(invitee, that.invitee) &&
                       Objects.equals(loadOut, that.loadOut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, inviter, invitee, loadOut, timeout);
    }

    @Override
    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this)
                       .add("invitee", invitee.toString())
                       .add("inviter", inviter.toString())
                       .add("loadOut", loadOut.toString())
                       .add("timeout", timeout)
                       .add("uuid", uuid.toString())
                       .toString();
    }
}
