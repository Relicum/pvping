package com.relicum.duel.Configs;

import com.google.gson.reflect.TypeToken;
import com.relicum.configs.Gson.Loaders.AbstractLoader;
import com.relicum.duel.Objects.LobbyLoadOut;

/**
 * LobbyLoadOutLoader loads and save players settings and loadout while in the lobby.
 *
 * @author Relicum
 * @version 0.0.1
 */
public class LobbyLoadOutLoader extends AbstractLoader<LobbyLoadOut> {

    public LobbyLoadOutLoader(String directoryPath, String fileName) {
        super(directoryPath, fileName);
        setToken();
    }

    @Override
    public void setToken() {

        super.token = new TypeToken<LobbyLoadOut>() {
        }.getType();
    }
}
