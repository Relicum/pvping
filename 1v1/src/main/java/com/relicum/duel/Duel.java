package com.relicum.duel;


import com.relicum.commands.CommandRegister;
import com.relicum.duel.Commands.*;
import com.relicum.duel.Configs.ConfigLoader;
import com.relicum.duel.Configs.DuelConfigs;
import com.relicum.duel.Handlers.GameHandler;
import com.relicum.duel.Handlers.InviteCache;
import com.relicum.duel.Handlers.InviteListener;
import com.relicum.duel.Handlers.LobbyHandler;
import com.relicum.duel.Handlers.SkullHandler;
import com.relicum.duel.Kits.KitManager;
import com.relicum.duel.Menus.MenuManager;
import com.relicum.duel.Objects.PvpPlayer;
import com.relicum.locations.SpawnPoint;
import com.relicum.pvpcore.Arenas.ZoneManager;
import com.relicum.pvpcore.Game.StatsManager;
import com.relicum.pvpcore.Menus.MenuAPI;
import com.relicum.titleapi.TitleApi;
import com.relicum.titleapi.TitleMaker;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

/**
 * Name: Duel.java Created: 17 April 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
@SuppressFBWarnings({"UNKNOWN"})
public class Duel extends JavaPlugin implements Listener {

    //public static final String META_KEY = "DUEL-META";
    private static Duel instance;
    public PvpPlayer player;
    private CacheManager cacheManager;
    private InviteCache inviteCache;
    private InviteListener inviteListener;
    private KitManager kitHandler;
    private boolean firstLoad = false;
    private GameHandler gameHandler;
    private ZoneManager<Duel> zoneManager;
    private MenuAPI<Duel> menuAPI;
    private StatsManager statsManager;
    private CommandRegister playerCommands;
    private CommandRegister adminCommands;
    private TitleMaker titleMaker;
    private MenuManager menuManager;
    private LobbyHandler lobbyHandler;
    private ConfigLoader configLoader;
    private DuelConfigs configs;
    private SkullHandler skullHandler;


    /**
     * Utility method for getting a plugins Main JavaPlugin Class
     *
     * @return Duel a static instance of the main plugin Class
     */
    public static Duel get() {

        return instance;

    }

    public void onEnable() {


        instance = this;

        if (!checkAndCreate(getDataFolder().toString() + File.separator + "config.json")) {

            firstLoad = true;

            if (doFirstLoad()) {

                DuelMsg.getInstance().logInfoFormatted("Initialisation of files completed successfully");
            }

            else {

                DuelMsg.getInstance().logSevereFormatted("Exceptions were thrown during initialisation, please check the logs");
            }
        }
        else {

            configLoader = new ConfigLoader(getDataFolder().toString() + File.separator, "config");
            configs = configLoader.load();
            if (!configs.isEnable()) {

                getServer().getPluginManager().disablePlugin(this);

                return;
            }

            skullHandler = new SkullHandler(this);

            cacheManager = new CacheManager();

            inviteListener = new InviteListener(this);

            inviteCache = new InviteCache(100l, 100l, inviteListener);

            kitHandler = new KitManager(this);
            gameHandler = new GameHandler(this);

            lobbyHandler = new LobbyHandler(this, gameHandler);

            statsManager = new StatsManager(this);

            if (configs.getCollectionSize() > 0) {

                zoneManager = new ZoneManager<>(this, configs.getCollectionNames());
            }
            else {

                zoneManager = new ZoneManager<>(this, Collections.<String>emptyList());
            }


            menuAPI = new MenuAPI<>(this);


            menuManager = new MenuManager(this);


        }


        getServer().getPluginManager().registerEvents(this, this);

        try {
            this.titleMaker = ((TitleApi) getServer().getPluginManager().getPlugin("TitleApi")).getTitleApi(this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }


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
        adminCommands.register(new BuildLoadOut(this));
        adminCommands.register(new DuelSettings(this));
        adminCommands.register(new LoadoutModify(this));
        adminCommands.register(new ClearAll(this));
        adminCommands.register(new DebugCmd(this));
        playerCommands.endRegistration();
        adminCommands.endRegistration();

        getServer().getScheduler().runTaskTimerAsynchronously(this, new TestTask(), 0, 0);


    }


//    private void setEconomy(){
//
//        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
//
//        if (rsp != null) {
//            economy = rsp.getProvider();
//            getLogger().info("Successfully hooked into Vault Economy");
//        }
//        else {
//            getLogger().severe("Unable to hook into Vault Economy");
//        }
//
//    }

    public boolean isFirstLoad() {

        return firstLoad;
    }

    public void onDisable() {

        DuelMsg.getInstance().logInfoFormatted("Saving Configuration files");
        configLoader.save(configs);
        cacheManager.invalidateAll();
        cacheManager.cleanUp();
        inviteCache.cleanAndDestroy();
        skullHandler.clear();
        kitHandler.saveAndRemoveAll();
        statsManager.saveAndClearAll();
        gameHandler.clearAllPlayers();
    }


    public InviteCache getInviteCache() {
        return inviteCache;
    }

    public SkullHandler getSkullHandler() {
        return skullHandler;
    }

    public InviteListener getInviteListener() {
        return inviteListener;
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public DuelConfigs getConfigs() {

        return configs;
    }

    public void saveConfigs() {

        configLoader.save(configs);
    }

    public MenuManager getMenuManager() {

        return menuManager;
    }

    public KitManager getKitHandler() {
        return kitHandler;
    }

    public LobbyHandler getLobbyHandler() {

        return lobbyHandler;
    }

    public MenuAPI<Duel> getMenuAPI() {

        return menuAPI;
    }

    public ZoneManager<Duel> getZoneManager() {

        return zoneManager;
    }

    public GameHandler getGameHandler() {

        return gameHandler;

    }

    public StatsManager getStatsManager() {

        return statsManager;
    }

    public TitleMaker getTitleMaker() {

        return titleMaker;
    }


    private boolean doFirstLoad() {

        try {
            this.configs = new DuelConfigs(new SpawnPoint(getServer().getWorld("world").getSpawnLocation()));
            this.configLoader = new ConfigLoader(getDataFolder().toString() + File.separator, "config");

            configLoader.save(configs);
            DuelMsg.getInstance().logInfoFormatted("Successfully created config.json");
            configs.setFirstLoad(false);

//            if (!Files.exists(Paths.get(getDataFolder().toString() + File.separator + "kits" + File.separator))) {
//                Files.createDirectories(Paths.get(getDataFolder().toString() + File.separator + "kits" + File.separator));
//            }

            skullHandler = new SkullHandler(this);
            cacheManager = new CacheManager();
            inviteListener = new InviteListener(this);

            inviteCache = new InviteCache(100l, 100l, inviteListener);
            kitHandler = new KitManager(this);
            gameHandler = new GameHandler(this);
            lobbyHandler = new LobbyHandler(this);
            lobbyHandler.setGameHandler(gameHandler);


            statsManager = new StatsManager(this);


            if (configs.getCollectionSize() > 0) {

                zoneManager = new ZoneManager<>(this, configs.getCollectionNames());
            }
            else {

                zoneManager = new ZoneManager<>(this, Collections.<String>emptyList());
            }


            menuAPI = new MenuAPI<>(this);


            menuManager = new MenuManager(this);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean checkAndCreate(String filePath) {

        if (!Files.exists(Paths.get(filePath))) {
            try {
                Files.createDirectory(Paths.get(getDataFolder().toString() + File.separator));
                Files.createFile(Paths.get(filePath));
                return false;
            }
            catch (IOException e) {
                e.printStackTrace();

            }
        }
        return true;
    }

}
