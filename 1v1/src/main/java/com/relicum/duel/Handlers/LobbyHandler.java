package com.relicum.duel.Handlers;

import com.relicum.duel.Configs.LobbyLoadOutLoader;
import com.relicum.duel.Configs.LobbyPlayerConfigs;
import com.relicum.duel.Duel;
import com.relicum.duel.Objects.LobbyLoadOut;
import com.relicum.pvpcore.Kits.LobbyHotBar;
import org.bukkit.event.Listener;

import java.io.File;

/**
 * Name: LobbyHandler.java Created: 12 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class LobbyHandler implements Listener {

    private transient Duel plugin;
    private transient GameHandler gameHandler;
    private LobbyLoadOut lobbyLoadOut;
    private LobbyLoadOutLoader loader;


    public LobbyHandler(Duel plugin) {
        this.plugin = plugin;
        if (plugin.isFirstLoad())
            doFirstLoad();
        else {
            loader = new LobbyLoadOutLoader(plugin.getDataFolder().toString() + File.separator, "lobbysettings");
            lobbyLoadOut = loader.load();
        }
    }

    public LobbyHandler(Duel plugin, GameHandler gameHandler) {
        this.plugin = plugin;
        this.gameHandler = gameHandler;

        loader = new LobbyLoadOutLoader(plugin.getDataFolder().toString() + File.separator, "lobbysettings");
        lobbyLoadOut = loader.load();

    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    /**
     * Gets plugin.
     *
     * @return main plugin instance.
     */
    public Duel getPlugin() {
        return plugin;
    }

    /**
     * Gets lobby load out.
     *
     * @return the {@link LobbyLoadOut}
     */
    public LobbyLoadOut getLobbyLoadOut() {
        return lobbyLoadOut;
    }

    /**
     * Gets Gson loader.
     *
     * @return the loader {@link LobbyLoadOutLoader}
     */
    public LobbyLoadOutLoader getLoader() {
        return loader;
    }

    /**
     * Gets game handler {@link GameHandler}.
     *
     * @return the {@link GameHandler}
     */
    public GameHandler getGameHandler() {
        return gameHandler;
    }

    public void doFirstLoad() {

        lobbyLoadOut = new LobbyLoadOut();
        lobbyLoadOut.setContents(LobbyHotBar.create().getItems());
        lobbyLoadOut.setArmor(new LobbyArmor(true));
        //lobbyLoadOut.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1000000, 2, false, false));
        //PotionEffect p = new PotionEffect(PotionEffectType.FAST_DIGGING, 10, 2, false, false);
        //lobbyLoadOut.addPotionEffect(p);
        lobbyLoadOut.setSettings(LobbyPlayerConfigs.create(true).getSettings());
        loader = new LobbyLoadOutLoader(plugin.getDataFolder().toString() + File.separator, "lobbysettings");
        loader.save(lobbyLoadOut);

    }
}
