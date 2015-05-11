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

        loader.setPath(Paths.get(DIR_PATH + uuid + ".json"));

        if (!checkFile(uuid)) {

            PlayerStats ps = new PlayerStats(uuid, 0, 0, 0, 0, 0, 0, 0, 0);

            loader.save(ps);
            playerStatsMap.put(uuid, ps);

        } else

            playerStatsMap.put(uuid, loader.load());

    }

    /**
     * Has the player already got a {@link PlayerStats} object loaded.
     *
     * @param uuid the string uuid of the player
     * @return true if they have, false if not.
     */
    public boolean hasStatsLoaded(String uuid) {

        return playerStatsMap.containsKey(uuid);

    }

    /**
     * Gets a players {@link PlayerStats} object.
     *
     * @param uuid the string uuid of the {@link org.bukkit.entity.Player}
     * @return the {@link PlayerStats} object.
     */
    public PlayerStats getAPlayersStats(String uuid) {

        if (hasStatsLoaded(uuid))

            return playerStatsMap.get(uuid);

        else

            return null;

    }

    /**
     * Save a players stats
     *
     * @param playerStats the player stats to be saved.
     */
    public void save(String uuid) {

        loader.setPath(Paths.get(DIR_PATH + uuid + ".json"));

        loader.save(playerStatsMap.get(uuid));

    }

    /**
     * Save a players stats
     *
     * @param playerStats the player stats to be saved.
     */
    public void save(PlayerStats stats) {

        loader.setPath(Paths.get(DIR_PATH + stats.getUuid() + ".json"));

        loader.save(stats);

    }

    /**
     * Save a players stats and removed it from internal stats map.
     *
     * @param playerStats the player stats to be saved.
     */
    public void removeAndSave(String uuid) {

        loader.setPath(Paths.get(DIR_PATH + uuid + ".json"));

        loader.save(playerStatsMap.remove(uuid));

    }

    /**
     * Check if the player has a {@link PlayerStats} file saved on disk.
     *
     * @param fileName the string uuid of the {@link org.bukkit.entity.Player}
     * @return true if they have a file, false if they do not. In it is false a
     * blank file is automatically created for them.
     */
    public boolean checkFile(String fileName) {

        if (!Files.exists(Paths.get(DIR_PATH + fileName + ".json"))) {

            try {

                Files.createFile(Paths.get(DIR_PATH + fileName + ".json"));
                return false;

            } catch (IOException e) {

                e.printStackTrace();
                return false;

            }
        }

        return true;
    }

    /**
     * Check stats directory exists if not it will create it.
     *
     * @return true, if the directory is present, false and a IO error occured.
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

    /**
     * Save and clear all stats from the internal map.
     */
    public void saveAndClearAll() {

        for (PlayerStats playerStats : playerStatsMap.values()) {

            save(playerStats.getUuid());

        }

        playerStatsMap.clear();

    }
}
