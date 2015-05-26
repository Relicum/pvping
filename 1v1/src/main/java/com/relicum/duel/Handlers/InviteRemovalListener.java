package com.relicum.duel.Handlers;

import com.relicum.duel.Objects.GameInvite;

import javax.annotation.Nullable;

/**
 * InviteRemovalListener a listener that receives notifications whenever {@link GameInvite} times out.
 *
 * @author Relicum
 * @version 0.0.1
 */
public interface InviteRemovalListener {

    /**
     * On removal is called by the {@link InviteCache} when a invite expires.
     * <p>Impliment this and pass it to the {@link InviteCache} to receive notifications.
     *
     * @param notification instance of {@link InviteRemovalNotification}
     */
    void onRemoval(@Nullable InviteRemovalNotification notification);
}
