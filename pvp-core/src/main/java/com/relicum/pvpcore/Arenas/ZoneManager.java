package com.relicum.pvpcore.Arenas;

import com.relicum.pvpcore.Configs.ZoneLoader;
import com.relicum.utilities.Files.FileUtils;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    public ZoneManager(T plugin, Set<String> names) {

        this.plugin = plugin;
        BASE_DIR = plugin.getDataFolder().toString() + File.separator + "zones" + File.separator;

        try {
            FileUtils.createDirectory(BASE_DIR);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.zoneLoader = new ZoneLoader(BASE_DIR);

        if (names != null && names.size() > 0) {
            init(names);
        }
    }

    private void init(Set<String> names) {

        zoneNames.addAll(names);

        for (String name : zoneNames) {
            zonesMap.putIfAbsent(name, new ZoneCollection(name));
            plugin.getLogger().info("New ZoneCollection Initialized for " + name);
            List<Path> paths;
            try {
                paths = FileUtils.getAllFilesInDirectory(BASE_DIR + name + File.separator, "json");

                if (paths.size() > 0) {
                    for (Path path : paths) {
                        zoneLoader.setPath(path);
                        PvPZone tp = zoneLoader.load();

                        zonesMap.get(name).addZone(tp);
                        plugin.getLogger().info("New zone added to collection " + name);
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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

        if (containsZone(coll, zone.getNameId()))
            throw new InvalidZoneException("The zone " + coll + " already exists with uuid: " + zone.getNameId());

        zoneLoader.setPath(Paths.get(BASE_DIR + coll + File.separator + zone.getNameId() + ".json"));
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

    public boolean containsZone(String name, String nameId) {

        return containsCollection(name) && zonesMap.get(name).contains(nameId);
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

    /**
     * Gets the total number of {@link PvPZone} in a {@link ZoneCollection}.
     *
     * @param name the name of the {@link ZoneCollection}
     * @return the total number of {@link PvPZone} or -1 if the name is not a
     *         valid collection.
     */
    public int getCollectionSize(String name) {
        if (!containsCollection(name))
            return -1;

        return zonesMap.get(name).getTotalZones();
    }

    /**
     * Gets total unique {@link ZoneCollection}.
     *
     * @return the total unique {@link ZoneCollection}
     */
    public int getTotalUniqueCollections() {
        return zoneNames.size();
    }

    /**
     * Gets {@link ZoneCollection} by name id.
     *
     * @param name the name
     * @return the {@link ZoneCollection}
     */
    public ZoneCollection getAllInCollection(String name) {
        return zonesMap.get(name);
    }

    /**
     * Gets a list of all {@link ZoneCollection} names.
     *
     * @return the {@link ZoneCollection} names
     */
    public List<String> getCollectionNames() {
        return zoneNames.stream().collect(Collectors.toList());
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
