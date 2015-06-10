package com.relicum.duel.Handlers;

import com.relicum.duel.Duel;
import com.relicum.duel.Objects.GameInvite;
import com.relicum.duel.Objects.InviteResponse;

import javax.annotation.Nullable;

/**
 * The type Invite handler.
 */
public class InviteListener implements InviteRemovalListener {


    private Duel plugin;

    public InviteListener(Duel plugin) {

        this.plugin = plugin;

    }


    public Duel getPlugin() {
        return plugin;
    }


    /**
     * Send message.
     * <p>Checks they are still have a {@link com.relicum.pvpcore.Enums.PlayerState#LOBBY} if not the message will not be sent.
     *
     * @param message the message
     * @param who     the id or either the inviter or invitee
     */
    public void sendMessage(String message, String who) {

        if (plugin.getLobbyHandler().isInLobby(who)) {
            plugin.getGameHandler().sendMessage(message, who);
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

        if (plugin.getLobbyHandler().isInLobby(who)) {
            plugin.getGameHandler().sendErrorMessage(message, who);
        }
    }


    /**
     * On removal is called by the {@link InviteCache} when a invite expires.
     * <p>Impliment this and pass it to the {@link InviteCache} to receive notifications.
     *
     * @param notification instance of {@link InviteRemovalNotification}
     */
    @SuppressWarnings("ConstantConditions")
    @Override
    public void onRemoval(@Nullable InviteRemovalNotification notification) {

        if (notification != null) {
            if (notification.getResponse().equals(InviteResponse.TIMEOUT)) {

                String invr = notification.getGameInvite().getInviterId();
                String inve = notification.getGameInvite().getInviteeId();
                if (plugin.getGameHandler().isKnown(invr)) {

                    plugin.getGameHandler().getPvpPlayer(invr).removeInvite(notification.getGameInvite());

                    System.out.println("The inviter has found and had their record removed");
                }
                if (plugin.getGameHandler().isKnown(inve)) {

                    plugin.getGameHandler().getPvpPlayer(inve).removeFromInvite(notification.getGameInvite());

                    System.out.println("The invitee has found and had their record removed");
                }

                processTimeout(notification.getGameInvite());

                notification.clear();


                System.out.println("InviteRemovalListener must of been called from Invite handler");
            }
            else if (notification.getResponse().equals(InviteResponse.CANCELED)) {

                processCancelled(notification.getGameInvite());

                notification.clear();
            }

            else if (notification.getResponse().equals(InviteResponse.INVITER_OFFLINE)) {

                processInviterOffLine(notification.getGameInvite());

                notification.clear();
            }

        }
        else {

            System.out.println("InviteRemovalNotification is null called from Invite handler, maybe the players both quit");
        }
    }

    private void processInviterOffLine(GameInvite invite) {


        sendErrorMessage("&6" + invite.getInviterName() + "&a has gone offline invite is cancelled", invite.getInviteeId());
        removeInvites(invite);
    }

    private void processCancelled(GameInvite invite) {

        sendMessage("You have canceled your invite to &6" + invite.getInviteeName(), invite.getInviterId());
        sendErrorMessage("&6" + invite.getInviterName() + "&a has cancelled their invite", invite.getInviteeId());
        removeInvites(invite);
    }

    private void processTimeout(GameInvite invite) {

        sendErrorMessage("Your invite to &6" + invite.getInviteeName() + "&c has expired", invite.getInviterId());
        removeInvites(invite);
//        String uuid = invite.getInviteeId();
//        if (plugin.getGameHandler().isKnown(uuid)) {
//
//            plugin.getGameHandler().getPvpPlayer(uuid).removeFromInvite(invite);
//            System.out.println("Should of removed from other player");
//
//        }
    }

    private void removeInvites(GameInvite invite) {

//        //Start with inviter
//        String initerUUID=invite.getInviterId();
//        String otherUUID = invite.getInviteeId();
//        if (plugin.getGameHandler().isKnown(initerUUID)){
//            System.out.println("Inviter is know to us");
//            PvpPlayer pv1 = plugin.getGameHandler().getPvpPlayer(initerUUID);
//
//           ListIterator<GameInvite> iterator= pv1.getSentInvites().listIterator();
//
//            while (iterator.hasNext()){
//
//                GameInvite gin = iterator.next();
//                if (gin.getInviteeId().equals(otherUUID))
//                    iterator.remove();
//            }
//
//            Iterator<GameInvite> iterator2= pv1.getInvites().values().iterator();
//
//            while (iterator2.hasNext()){
//
//                GameInvite gin2 = iterator2.next();
//
//                if (gin2.getInviterId().equals(otherUUID))
//                    iterator2.remove();
//            }
//
//        }
//
//        if (plugin.getGameHandler().isKnown(otherUUID)){
//            System.out.println("Inviteee is know to us");
//            PvpPlayer pv1 = plugin.getGameHandler().getPvpPlayer(otherUUID);
//
//            ListIterator<GameInvite> iterator= pv1.getSentInvites().listIterator();
//
//            while (iterator.hasNext()){
//
//                GameInvite gin = iterator.next();
//                if (gin.getInviteeId().equals(initerUUID))
//                    iterator.remove();
//            }
//
//            Iterator<GameInvite> iterator2= pv1.getInvites().values().iterator();
//
//            while (iterator2.hasNext()){
//
//                GameInvite gin2 = iterator2.next();
//
//                if (gin2.getInviterId().equals(initerUUID))
//                    iterator2.remove();
//            }
//
//        }
    }

//    private void process(InviteResponse response) {
//
//        switch (response) {
//            case ACCEPTED: {
//
//                sendMessage("&6" + getInviteeName() + "&a has accepted your challenge", getInviterId());
//                sendMessage("You have accepted the invite from &6" + getInviterName(), getInviteeId());
//                break;
//            }
//            case TIMEOUT: {
//                sendErrorMessage("Your invite to &6" + getInviteeName() + "&c has expired", getInviterId());
//                //Duel.get().getInviteListener().removeInvite(getUuid());
//                break;
//            }
//            case DECLINED: {
//                sendErrorMessage("&6" + getInviteeName() + "&a has declined your challenge", getInviterId());
//                sendMessage("You have declined the invite from &6" + getInviterName(), getInviteeId());
//                break;
//            }
//            case CANCELED: {
//                sendMessage("You have canceled your invite to &6" + getInviteeName(), getInviterId());
//                sendErrorMessage("&6" + getInviterName() + "&a has cancelled their invite", getInviteeId());
//                break;
//            }
//            case INVITER_OFFLINE: {
//                sendErrorMessage("&6" + getInviterName() + "&a has gone offline invite is cancelled", getInviteeId());
//                break;
//            }
//            case INVITEE_OFFLINE: {
//                sendErrorMessage("&6" + getInviteeName() + "&a has gone offline invite is cancelled", getInviterId());
//                break;
//            }
//            case SYSTEM: {
//                Duel.get().getLogger().info("System has expired invite from " + getInviterName() + " to " + getInviteeName());
//                break;
//            }
//            default: {
//
//                DuelMsg.getInstance().logSevereFormatted("Unknown invite Response type, this is a bug");
//            }
//
//        }
//
//    }
}
