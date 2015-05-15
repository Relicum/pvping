package com.relicum.duel.Listeners;

import com.relicum.duel.Duel;
import com.relicum.duel.Handlers.LobbyHandler;
import org.bukkit.event.Listener;

/**
 * Name: AutoJoinLobby.java Created: 13 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class AutoJoinLobby implements Listener {

    private Duel plugin;
    private LobbyHandler lobbyHandler;

    private boolean dedicated = true;
    private boolean lobbyEnabled = false;
    private boolean autoJoin = true;
    private boolean adminMode = false;

    public AutoJoinLobby(Duel plugin, LobbyHandler lobbyHandler) {

        this.plugin = plugin;
        this.lobbyHandler = lobbyHandler;
        this.lobbyEnabled = lobbyHandler.isLobbyEnabled();

    }


    /**
     * Gets dedicated.
     *
     * @return Value of dedicated.
     */
    public boolean isDedicated() {

        return dedicated;
    }

    /**
     * Gets adminMode.
     *
     * @return Value of adminMode.
     */
    public boolean isAdminMode() {

        return adminMode;
    }

    /**
     * Gets lobbyEnabled.
     *
     * @return Value of lobbyEnabled.
     */
    public boolean isLobbyEnabled() {

        return lobbyEnabled;
    }

    /**
     * Gets autoJoin.
     *
     * @return Value of autoJoin.
     */
    public boolean isAutoJoin() {

        return autoJoin;
    }
}
