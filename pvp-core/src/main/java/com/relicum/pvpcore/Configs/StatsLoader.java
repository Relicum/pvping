package com.relicum.pvpcore.Configs;

import com.google.gson.reflect.TypeToken;
import com.relicum.configs.Gson.Loaders.AbstractLoader;
import com.relicum.pvpcore.Game.PlayerStats;

/**
 * Name: StatsLoader.java Created: 01 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class StatsLoader extends AbstractLoader<PlayerStats> {

    public StatsLoader(String paramPath) {
        super(paramPath);

    }

    @Override
    public void setToken() {
        super.token = new TypeToken<PlayerStats>() {
        }.getType();
    }
}
