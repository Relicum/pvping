package com.relicum.duel.Configs;

import com.massivecraft.massivecore.xlib.gson.reflect.TypeToken;
import com.relicum.pvpcore.Configs.Loaders.AbstractLoader;
import com.relicum.pvpcore.Kits.KitData;

/**
 * Name: KitDataLoader.java Created: 14 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class KitDataLoader extends AbstractLoader<KitData> {


    public KitDataLoader(String directoryPath, String fileName) {
        super(directoryPath, fileName);
        setToken();
    }

    @Override
    public void setToken() {
        super.token = new TypeToken<KitData>() {}.getType();
    }
}
