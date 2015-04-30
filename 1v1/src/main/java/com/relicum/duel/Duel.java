package com.relicum.duel;

import com.relicum.commands.CommandRegister;
import com.relicum.duel.Commands.Join;
import com.relicum.duel.Commands.Leave;
import com.relicum.duel.Objects.PvpPlayer;
import com.relicum.locations.SpawnPoint;
import com.relicum.pvpcore.Locations.SpawnLoader;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Name: Duel.java Created: 17 April 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class Duel extends JavaPlugin implements Listener {

    private static Duel instance;
    private CommandRegister commandRegister;

    public PvpPlayer player;

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
        getServer().getPluginManager().registerEvents(this, this);
        saveConfig();
        SpawnPoint point;
        SpawnLoader loader = new SpawnLoader(getDataFolder().toString() + File.separator + "spawns.json");
        if (checkAndCreate(getDataFolder().toString() + File.separator + "spawns.json")) {
            point = loader.load();
        } else {
            point = new SpawnPoint("world", 120.0d, 64.0d, 23.3d);
            loader.save(point);
        }

        System.out.println(point.toString());

        commandRegister = new CommandRegister(this);
        getCommand("1v1").setExecutor(commandRegister);
        getCommand("1v1").setTabCompleter(commandRegister);
        getCommand("noxarena").setExecutor(commandRegister);
        getCommand("noxarena").setTabCompleter(commandRegister);
        commandRegister.register(new Leave(this));
        commandRegister.register(new Join(this));
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

    public boolean checkAndCreate(String filePath) {

        if (!Files.exists(Paths.get(filePath))) {
            try {
                Files.createFile(Paths.get(filePath));
                return false;
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        return true;
    }

    public void onJoin(PlayerJoinEvent e) {

        new BukkitRunnable() {

            @Override
            public void run() {

                player = new PvpPlayer(e.getPlayer(), Duel.this);

            }
        }.runTaskLater(this, 60l);

    }

    public void onQuit(PlayerQuitEvent e) {

        try {
            if (player != null) {
                if (player.getUuid().equals(e.getPlayer().getUniqueId())) {
                    player.destroy();
                    player = null;
                }

            }
        } catch (Exception ignored) {
            System.out.println("Already null");
        }

    }

}
