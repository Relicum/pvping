package com.relicum.duel.Objects;

import com.relicum.duel.Duel;
import com.relicum.pvpcore.Kits.LoadOut;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

/**
 * GameInvite holds details of players challenging other players to 1v1's
 *
 * @author Relicum
 * @version 0.0.1
 */
public class GameInvite {


    private UUID uuid;
    private String inviterId;
    private String inviterName;
    private String inviteeId;
    private String inviteeName;
    private LoadOut loadOut;
    private long timeout;
    private boolean active;


    /**
     * Instantiates a new Game invite.
     *
     * @param inviterId   the string uuid of the player submitting the invite.
     * @param inviterName the player name submitting the invite.
     * @param inviteeId   the string uuid of the player the invite is sent to.
     * @param inviteeName the player name the invite is sent to.
     * @param loadOut     the {@link LoadOut} the game will use.
     */
    public GameInvite(String inviterId, String inviterName, String inviteeId, String inviteeName, @Nullable LoadOut loadOut) {

        this.inviterId = inviterId;
        this.inviterName = inviterName;
        this.inviteeId = inviteeId;
        this.inviteeName = inviteeName;
        this.loadOut = loadOut;
        this.timeout = (System.currentTimeMillis() + 30000);
        this.uuid = UUID.randomUUID();
        this.active = true;

    }

    /**
     * Instantiates a new Game invite.
     *
     * @param inviterId   the string uuid of the player submitting the invite.
     * @param inviterName the player name submitting the invite.
     * @param inviteeId   the string uuid of the player the invite is sent to.
     * @param inviteeName the player name the invite is sent to.
     * @param loadOut     the {@link LoadOut} the game will use.
     * @param timeOut     the number of milli seconds before the invite expires default is 30 seconds
     */
    public GameInvite(String inviterId, String inviterName, String inviteeId, String inviteeName, @Nullable LoadOut loadOut, long timeOut) {

        this.inviterId = inviterId;
        this.inviterName = inviterName;
        this.inviteeId = inviteeId;
        this.inviteeName = inviteeName;
        this.loadOut = loadOut;
        this.timeout = (System.currentTimeMillis() + timeOut);
        this.uuid = UUID.randomUUID();
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

    public void remove(InviteResponse response) {

        Duel.get().getInviteCache().removeInvite(getUuid(), response);
    }

    /**
     * Send message.
     * <p>Checks they are still have a {@link com.relicum.pvpcore.Enums.PlayerState#LOBBY} if not the message will not be sent.
     *
     * @param message the message
     * @param who     the id or either the inviter or invitee
     */
    public void sendMessage(String message, String who) {

        if (Duel.get().getLobbyHandler().isInLobby(who)) {
            Duel.get().getGameHandler().sendMessage(message, who);
        }

    }

    /**
     * Send error message.
     * <p>Checks they are still have a {@link com.relicum.pvpcore.Enums.PlayerState#LOBBY} if not the message will not be sent.
     *
     * @param message the message
     * @param who     the id or either the inviter or invitee
     */
    public void sendErrorMessage(String message, String who) {

        if (Duel.get().getLobbyHandler().isInLobby(who)) {
            Duel.get().getGameHandler().sendErrorMessage(message, who);
        }
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


    /**
     * Gets the invites {@link UUID}.
     *
     * @return the uuid
     */
    public UUID getUuid() {
        return uuid;
    }


    /**
     * Gets inviter string uuid.
     *
     * @return the inviter string uuid
     */
    public String getInviterId() {
        return inviterId;
    }

    /**
     * Gets invitee string uuid
     *
     * @return the invitee string uuid
     */
    public String getInviteeId() {
        return inviteeId;
    }

    /**
     * Gets inviter name.
     *
     * @return the inviter name
     */
    public String getInviterName() {
        return inviterName;
    }

    /**
     * Gets invitee name.
     *
     * @return the invitee name
     */
    public String getInviteeName() {
        return inviteeName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameInvite)) return false;
        GameInvite invite = (GameInvite) o;
        return Objects.equals(uuid, invite.uuid) &&
                       Objects.equals(inviterId, invite.inviterId) &&
                       Objects.equals(inviteeId, invite.inviteeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, inviterId, inviteeId);
    }

    @Override
    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this)
                       .add("uuid", uuid)
                       .add("inviterId", inviterId)
                       .add("inviteeId", inviteeId)
                       .add("inviterName", inviterName)
                       .add("inviteeName", inviteeName)
                       .add("timeout", timeout)
                       .add("loadOut", loadOut.toString())
                       .toString();
    }

    public void clear() {

        this.uuid = null;
        this.inviterId = null;
        this.inviterName = null;
        this.inviteeId = null;
        this.inviteeName = null;
        this.loadOut = null;

    }

    /**
     * Gets active.
     *
     * @return true and the invite is still valid.
     */
    public boolean isActive() {

        return active;
    }

    /**
     * Sets new active.
     *
     * @param active set true to mark it as valid or false to mark it as invalid.
     */
    public void setActive(boolean active) {

        this.active = active;
    }
}
