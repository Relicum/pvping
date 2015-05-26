package com.relicum.duel.Handlers;

import com.relicum.duel.Objects.GameInvite;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;

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
    private WeakReference<GameInvite> gameInvite;

    InviteRemovalNotification(@Nullable String key, @Nullable GameInvite invite) {
        this.key = key;
        this.gameInvite = new WeakReference<>(invite);
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
            return gameInvite.get();
        }

        return null;
    }

    /**
     * Clear the {@link WeakReference} of the {@link GameInvite}.
     */
    public void clearInviteReference() {

        if (gameInvite != null) {
            gameInvite.clear();
        }
    }

    private static final long serialVersionUID = 0;
}
