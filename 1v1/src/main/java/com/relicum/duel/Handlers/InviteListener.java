package com.relicum.duel.Handlers;

import com.relicum.duel.Objects.GameInvite;

import javax.annotation.Nullable;

/**
 * InviteListener is am implementation of {@link InviteRemovalListener} which handles expired {@link GameInvite}
 *
 * @author Relicum
 * @version 0.0.1
 */
public class InviteListener implements InviteRemovalListener {


    public InviteListener() {

    }


    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("ConstantConditions")
    @Override
    public void onRemoval(@Nullable InviteRemovalNotification notification) {

        if (notification != null) {
            notification.getGameInvite().inviteTimesOut();
            System.out.println("InviteRemovalListener must of been called");

        }
        else {

            System.out.println("InviteRemovalNotification is null, maybe the players both quit");
        }
    }


}
