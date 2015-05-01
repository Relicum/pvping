package com.relicum.duel;

import com.relicum.commands.CommandRegister;
import com.relicum.duel.Commands.Join;
import com.relicum.duel.Commands.Leave;
import com.relicum.duel.Handlers.GameQueueHandler;
import com.relicum.duel.Objects.PvpPlayer;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

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
    private GameQueueHandler gameQueue;

    public PvpPlayer player;

    public void onEnable() {

        instance = this;

        saveConfig();

        gameQueue = new GameQueueHandler(this);

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

    public GameQueueHandler getGameQueue()
    {
        return gameQueue;
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
