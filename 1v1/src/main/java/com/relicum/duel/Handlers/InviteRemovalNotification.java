package com.relicum.duel.Handlers;

import com.relicum.duel.Objects.GameInvite;
import com.relicum.duel.Objects.InviteResponse;

import javax.annotation.Nullable;

/**
 * InviteRemovalNotification
 *
 * @author Relicum
 * @version 0.0.1
 */
public final class InviteRemovalNotification {


    @Nullable
    private String key;
    @Nullable
    private GameInvite gameInvite;

    private InviteResponse response;

    InviteRemovalNotification(@Nullable String key, @Nullable GameInvite invite, @Nullable InviteResponse response) {
        this.key = key;
        this.gameInvite = invite;
        this.response = response;
    }

    /**
     * Get the key as {@link java.util.UUID} in string format.
     *
     * @return the uuid string which is unique to this {@link GameInvite}.
     */
    public
    @Nullable
    String getKey() {

        if (key != null) {
            return key;
        }

        return "";
    }

    /**
     * Gets {@link GameInvite} that has timed out.
     *
     * @return the {@link GameInvite}
     */
    @Nullable
    public GameInvite getGameInvite() {
        if (gameInvite != null) {
            return gameInvite;
        }

        return null;
    }

    /**
     * Gets response of the removal giving the reason from {@link InviteResponse}.
     *
     * @return the response the reason for removal {@link InviteResponse}
     */
    public InviteResponse getResponse() {
        return response;
    }

    public void clear() {

        if (gameInvite != null)
            gameInvite.clear();
        this.key = null;
    }

    private static final long serialVersionUID = 0;
}
