package com.relicum.duel.Configs;

import com.google.gson.reflect.TypeToken;
import com.relicum.configs.Gson.Loaders.AbstractLoader;

/**
 * Name: LobbyLoader.java Created: 11 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class LobbyLoader extends AbstractLoader<LobbyPlayerConfigs> {

    public LobbyLoader(String directoryPath, String fileName) {
        super(directoryPath, fileName);
        setToken();
    }

    @Override
    public void setToken() {

        super.token = new TypeToken<LobbyPlayerConfigs>() {
        }.getType();
    }
}
