package com.relicum.pvpcore.Locations;


import com.massivecraft.massivecore.xlib.gson.reflect.TypeToken;
import com.relicum.locations.SpawnPoint;
import com.relicum.pvpcore.Configs.AbstractLoader;

/**
 * SpawnLoader save and load {@link SpawnPoint} to Json.
 *
 * @author Relicum
 * @version 0.0.1
 */
public class SpawnLoader extends AbstractLoader<SpawnPoint> {

    public SpawnLoader(String paramPath) {

        super(paramPath);
        setToken();
    }

    @Override
    public void setToken() {

        super.token = new TypeToken<SpawnPoint>() {
        }.getType();
    }
}
