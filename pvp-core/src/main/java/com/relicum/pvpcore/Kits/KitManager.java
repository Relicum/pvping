package com.relicum.pvpcore.Kits;

import com.relicum.utilities.Files.FileUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * Name: KitManager.java Created: 03 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public abstract class KitManager<T extends JavaPlugin> {

    private T plugin;

    private String BASE_DIR;

    public KitManager(T plugin) {

        this.plugin = plugin;

        BASE_DIR = plugin.getDataFolder().toString() + File.separator + "zones" + File.separator;

        try {
            FileUtils.createDirectory(BASE_DIR);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public T getPlugin() {

        return plugin;
    }
}
