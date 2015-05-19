package com.relicum.pvpcore.Configs.Loaders;


import com.massivecraft.massivecore.xlib.gson.reflect.TypeToken;
import com.relicum.pvpcore.Configs.AbstractLoader;
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
        setToken();

    }

    @Override
    public void setToken() {

        super.token = new TypeToken<PlayerStats>() {
        }.getType();
    }
}
