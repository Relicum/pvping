package com.relicum.pvpcore.Kits;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Name: KitManager.java Created: 03 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class KitManager<T extends JavaPlugin> {

    private T plugin;

    public KitManager(T plugin) {

        this.plugin = plugin;
    }

    public T getPlugin() {

        return plugin;
    }
}
