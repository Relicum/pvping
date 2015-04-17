package com.relicum.duel;

import com.relicum.duel.Commands.Hello;
import com.relicum.pvpcore.Commands.CommandRegister;
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
