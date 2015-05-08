package com.relicum.pvpcore.Configs;

import com.google.gson.reflect.TypeToken;
import com.relicum.configs.Gson.Loaders.AbstractLoader;
import com.relicum.pvpcore.Arenas.PvPZone;

/**
 * Name: ZoneLoader.java Created: 01 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class ZoneLoader extends AbstractLoader<PvPZone> {

    public ZoneLoader(String directoryPath) {

        super(directoryPath);
        setToken();
    }

    @Override
    public void setToken() {

        super.token = new TypeToken<PvPZone>() {
        }.getType();
    }
}
