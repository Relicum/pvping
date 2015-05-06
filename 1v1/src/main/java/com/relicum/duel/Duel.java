package com.relicum.duel;

import com.relicum.commands.CommandRegister;
import com.relicum.duel.Commands.Join;
import com.relicum.duel.Commands.Leave;
import com.relicum.duel.Commands.ZoneCreator;
import com.relicum.duel.Commands.ZoneModify;
import com.relicum.duel.Handlers.GameQueueHandler;
import com.relicum.duel.Menus.MenuManager;
import com.relicum.duel.Objects.PvpPlayer;
import com.relicum.pvpcore.Arenas.ZoneManager;
import com.relicum.pvpcore.Game.StatsManager;
import com.relicum.pvpcore.Menus.MenuAPI;
import com.relicum.titleapi.TitleApi;
import com.relicum.titleapi.TitleMaker;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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
@SuppressFBWarnings({ "UNKNOWN" })
public class Duel extends JavaPlugin implements Listener {

    private static Duel instance;
    private GameQueueHandler gameQueue;
    private ZoneManager<Duel> zoneManager;
    private MenuAPI<Duel> menuAPI;
    private StatsManager statsManager;
    private CommandRegister playerCommands;
    private CommandRegister adminCommands;
    private TitleMaker titleMaker;
    private MenuManager menuManager;

    public PvpPlayer player;

    public void onEnable() {

        instance = this;

        saveConfig();
        statsManager = new StatsManager(this);

        try {
            this.titleMaker = ((TitleApi) getServer().getPluginManager().getPlugin("TitleApi")).getTitleApi(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        menuAPI = new MenuAPI<>(this);
        zoneManager = new ZoneManager<>(this, getConfig().getConfigurationSection("zone.name").getValues(false).keySet());
        gameQueue = new GameQueueHandler(this);
        menuManager = new MenuManager(this);
        playerCommands = new CommandRegister(this);
        adminCommands = new CommandRegister(this);
        getCommand("1v1").setExecutor(playerCommands);
        getCommand("1v1").setTabCompleter(playerCommands);
        getCommand("noxarena").setExecutor(adminCommands);
        getCommand("noxarena").setTabCompleter(adminCommands);
        playerCommands.register(new Leave(this));
        playerCommands.register(new Join(this));
        adminCommands.register(new ZoneCreator(this));
        adminCommands.register(new ZoneModify(this));
        playerCommands.endRegistration();
        adminCommands.endRegistration();

    }

    public void onDisable() {

        saveConfig();
        statsManager.saveAndClearAll();
        gameQueue.clearAllPlayers();
    }

    /**
     * Utility method for getting a plugins Main JavaPlugin Class
     *
     * @return Duel a static instance of the main plugin Class
     */
    public static Duel get() {

        return instance;

    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public MenuAPI<Duel> getMenuAPI() {
        return menuAPI;
    }

    public ZoneManager<Duel> getZoneManager() {
        return zoneManager;
    }

    public GameQueueHandler getGameQueue() {

        return gameQueue;

    }

    public StatsManager getStatsManager() {

        return statsManager;
    }

    public TitleMaker getTitleMaker() {
        return titleMaker;
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
