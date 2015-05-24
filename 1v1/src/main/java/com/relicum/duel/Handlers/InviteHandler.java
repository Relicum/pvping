package com.relicum.duel.Handlers;

import com.relicum.duel.Duel;
import com.relicum.duel.Objects.GameInvite;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Name: InviteHandler.java Created: 24 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class InviteHandler {

    private Map<UUID, GameInvite> invites;
    private Duel plugin;

    public InviteHandler(Duel plugin) {
        this.plugin = plugin;
        this.invites = new HashMap<>();
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
     * Check invite validity returning a list of all invites that have timed out.
     *
     * @return the list of in valid {@link GameInvite}
     */
    public List<GameInvite> checkInviteValidity() {

        return invites.values().stream().filter(p -> !p.isValid()).collect(Collectors.toList());

    }
}
