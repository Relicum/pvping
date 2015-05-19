package com.relicum.duel.Configs;

import com.massivecraft.massivecore.xlib.gson.reflect.TypeToken;
import com.relicum.pvpcore.Configs.AbstractLoader;
import com.relicum.pvpcore.Kits.LoadOut;

/**
 * LoadOutLoader
 *
 * @author Relicum
 * @version 0.0.1
 */
public class LoadOutLoader extends AbstractLoader<LoadOut> {


    public LoadOutLoader(String directoryPath, String fileName) {

        super(directoryPath, fileName);
        setToken();


    }

    @Override
    public void setToken() {

        super.token = new TypeToken<LoadOut>() {
        }.getType();
    }
}
