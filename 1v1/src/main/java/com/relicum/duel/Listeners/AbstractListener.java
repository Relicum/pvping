package com.relicum.duel.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Name: AbstractListener.java Created: 21 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public abstract class AbstractListener<T extends JavaPlugin> implements Listener {

    private static final long serialVersionUID = 1L;

    protected T plugin;

    public AbstractListener(T plugin) {
        this.plugin = plugin;
    }

    public void register() {

        getPlugin().getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "New Listener Registered: " + ChatColor.GREEN + this.getClass().getSimpleName());
    }

    public void unregister() {

        this.getPlugin().getServer().getServicesManager().unregister(this);
    }

    public T getPlugin() {
        return plugin;
    }
}
