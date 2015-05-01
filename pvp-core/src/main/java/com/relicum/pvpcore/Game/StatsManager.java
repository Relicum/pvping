package com.relicum.pvpcore.Game;

import com.relicum.pvpcore.Configs.StatsLoader;
import org.bukkit.plugin.Plugin;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * StatsManager load, saves and calculates all the game stats of players.
 *
 * @author Relicum
 * @version 0.0.1
 */
public class StatsManager {

    private Plugin plugin;
    private Map<String, PlayerStats> playerStatsMap = new HashMap<>();
    private final String DIR_PATH;
    private StatsLoader loader;

    public StatsManager(Plugin paramPlugin) {
        this.plugin = paramPlugin;
        this.DIR_PATH = plugin.getDataFolder().toString() + File.separator + "stats" + File.separator;
        if (checkDir()) {
            plugin.getLogger().info("Duel stats directory successfully located");
        }

        loader = new StatsLoader(DIR_PATH);
    }

    /**
     * Load player stats from file and stores them in local map.
     *
     * @param uuid the uuid of the player.
     */
    public void load(String uuid) {
        loader.newFile(uuid);

        playerStatsMap.put(uuid, loader.load());
    }

    /**
     * Save a players stats
     *
     * @param playerStats the player stats to be saved.
     */
    public void save(String uuid) {
        loader.newFile(uuid);

        loader.save(playerStatsMap.get(uuid));
    }

    public void removeAndSave(String uuid) {
        loader.newFile(uuid);

        loader.save(playerStatsMap.remove(uuid));
    }

    /**
     * Check stats directory exists if not it will create it.
     *
     * @return true,if the directory is present, false and a IO error occured.
     */
    public boolean checkDir() {
        if (!Files.exists(Paths.get(DIR_PATH))) {
            try {
                Files.createDirectory(Paths.get(DIR_PATH));
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    public Plugin getPlugin() {
        return plugin;
    }
}
