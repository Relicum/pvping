package com.relicum.duel.Configs;

import com.google.common.base.Objects;
import com.relicum.pvpcore.Gamers.PlayerGameSettings;
import com.relicum.pvpcore.Gamers.PlayerGameSettingsBuilder;

/**
 * Name: LobbyPlayerConfigs.java Created: 11 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class LobbyPlayerConfigs {

    private PlayerGameSettings settings;

    public LobbyPlayerConfigs() {

    }

    public void setDefaults() {
        this.settings = PlayerGameSettingsBuilder.builder().build();
    }

    public PlayerGameSettings getSettings() {
        return settings;
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("settings", settings)
                .toString();
    }
}
