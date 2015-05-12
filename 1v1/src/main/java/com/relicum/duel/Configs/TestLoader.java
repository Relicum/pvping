package com.relicum.duel.Configs;

import com.massivecraft.massivecore.xlib.gson.reflect.TypeToken;
import com.relicum.configs.Gson.Loaders.AbstractLoader;
import com.relicum.duel.Handlers.LobbyArmor;

/**
 * Name: TestLoader.java Created: 11 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class TestLoader extends AbstractLoader<LobbyArmor> {


    public TestLoader(String directoryPath, String fileName) {
        super(directoryPath, fileName);
        setToken();


    }

    @Override
    public void setToken() {
        super.token = new TypeToken<LobbyArmor>() {
        }.getType();
    }
}
