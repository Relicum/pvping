package com.relicum.duel.Configs;

import com.massivecraft.massivecore.xlib.gson.reflect.TypeToken;
import com.relicum.configs.Gson.Loaders.AbstractLoader;

/**
 * Name: ConfigLoader.java Created: 12 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class ConfigLoader extends AbstractLoader<DuelConfigs> {


    public ConfigLoader(String directoryPath, String fileName) {
        super(directoryPath, fileName);
        setToken();
    }

    @Override
    public void setToken() {
        super.token = new TypeToken<DuelConfigs>() {
        }.getType();
    }
}
