package com.relicum.duel;

import com.relicum.duel.Commands.Hello;
import com.relicum.pvpcore.Commands.CommandRegister;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Name: Duel.java Created: 17 April 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class Duel extends JavaPlugin {

    private static Duel instance;
    private CommandRegister commandRegister;

    public void onEnable() {

        instance = this;

        saveConfig();

        ConfigurationSection section = getConfig().createSection("spawns.config");
        section.set("populate-map", false);
        section.set("center-x", 0);
        section.set("center-z", 0);
        section.set("distance-between", 70);
        section.set("generate-walls", true);
        section.set("offset", 25);
        section.set("world", "world");
        section.set("frequency", 3);
        section.set("padding", 208);

        saveConfig();

        commandRegister = new CommandRegister(this);
        getCommand("duel").setExecutor(commandRegister);
        getCommand("duel").setTabCompleter(commandRegister);
        commandRegister.register(new Hello(this));
        commandRegister.endRegistration();

    }

    public void onDisable() {

    }

    /**
     * Utility method for getting a plugins Main JavaPlugin Class
     *
     * @return Duel a static instance of the main plugin Class
     */
    public static Duel getInstance() {

        return instance;

    }

}
