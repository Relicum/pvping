package com.relicum.pvpcore.Arenas;

import com.relicum.pvpcore.Configs.ZoneLoader;
import com.relicum.utilities.Files.FileUtils;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

/**
 * Name: ZoneManager.java Created: 01 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class ZoneManager<T extends JavaPlugin> {

    private T plugin;

    private ZoneLoader zoneLoader;

    private Map<String, ZoneCollection> zonesMap = new HashMap<>();

    private Set<String> zoneNames = new HashSet<>();

    private String BASE_DIR;

    public ZoneManager(T plugin) {

        this.plugin = plugin;
        BASE_DIR = plugin.getDataFolder().toString() + File.separator + "zones" + File.separator;
        try {
            FileUtils.createDirectory(BASE_DIR);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.zoneLoader = new ZoneLoader(BASE_DIR);
    }

    public T getPlugin() {
        return plugin;
    }

    public boolean registerCollection(String name) throws DuplicateZoneException {
        if (zoneNames.contains(name)) {
            throw new DuplicateZoneException("Attempt was made to create a duplicate zone collection with the name " + name);

        }

        if (zoneNames.add(name)) {
            if (!createDirectory(BASE_DIR + name + File.separator))
                return false;

            zonesMap.put(name, new ZoneCollection(name));
            plugin.getLogger().info("New ZoneCollection " + name + " successfully registered");

            return true;

        }

        plugin.getLogger().severe("Unable to register ZoneCollection " + name);

        return false;
    }

    public boolean registerZone(String coll, PvPZone zone) throws InvalidZoneException {
        if (!zoneNames.contains(coll))
            throw new InvalidZoneException("Unable to locate ZoneCollection with the name: " + coll);

        if (containsZone(coll, zone.getUuid()))
            throw new InvalidZoneException("The zone " + coll + " already exists with uuid: " + zone.getUuid().toString());

        zoneLoader.setPath(Paths.get(BASE_DIR + coll + File.separator + zone.getUuid().toString() + ".json"));
        zoneLoader.save(zone);

        zonesMap.get(coll).addZone(zone);

        return true;
    }

    public ZoneCollection addPvpZones(String name, ZoneCollection pZones) {
        return zonesMap.putIfAbsent(name, pZones);
    }

    public void addZoneToZones(String name, PvPZone pZone) {
        zonesMap.get(name).addZone(pZone);
    }

    public boolean containsZone(String name, UUID uuid) {

        return containsCollection(name) && zonesMap.get(name).contains(uuid);
    }

    /**
     * Contains check if a {@link ZoneCollection} is stored.
     *
     * @param name the name of the {@link ZoneCollection} to search for.
     * @return true if its stored false if not.
     */
    public boolean containsCollection(String name) {
        return zonesMap.containsKey(name);
    }

    public boolean createDirectory(String path) {
        try {
            return FileUtils.createDirectory(path);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
