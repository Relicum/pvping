package com.relicum.pvpcore.Game;

import com.relicum.pvpcore.Arenas.PvPZone;
import com.relicum.pvpcore.Gamers.WeakGamer;
import com.relicum.pvpcore.Kits.LoadOut;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Game WIP
 *
 * @author Relicum
 * @version 0.0.1
 */
public class Game<T extends JavaPlugin> {

    private transient T plugin;
    private UUID uuid;
    private PvPZone zone;
    private int rounds = 3;
    private int maxRoundLength = 120;
    private LoadOut kit;
    private int countDown = 5;
    private boolean ranked = false;
    private int rewardPerRound = 10;
    private int rewardPerWin = 5;
    private int penaltyPerLoss = -5;
    private int penaltyForQuit = -25;
    private transient Map<UUID, WeakGamer> players;

    public Game() {

    }

    public Game(T plugin, PvPZone pZone, LoadOut loadOut) {

        this.plugin = plugin;
        this.uuid = UUID.randomUUID();
        this.zone = pZone;
        this.players = new HashMap<>(zone.getMaxPlayers());
        this.kit = loadOut;
    }

    public T getPlugin() {

        return plugin;
    }

    public void addPlayer(WeakGamer weakGamer) {

        if (players.containsKey(weakGamer.getUuid())) {
            throw new IllegalArgumentException("The player is already in this game");
        }

        players.put(weakGamer.getUuid(), weakGamer);

    }

    public void setZone(PvPZone pZone) {

        this.zone = pZone;
    }

    public PvPZone getZone() {

        return zone;
    }

    public int getCountDown() {

        return countDown;
    }

    public void setCountDown(int countDown) {

        this.countDown = countDown;
    }

    public LoadOut getKit() {

        return kit;
    }

    public int getMaxPlayers() {

        return zone.getMaxPlayers();
    }

    public int getMinPlayers() {

        return zone.getMinPlayers();
    }

    public void setRounds(int rounds) {

        this.rounds = rounds;
    }

    public int getRounds() {

        return rounds;
    }

    public int getMaxRoundLength() {

        return maxRoundLength;
    }

    public void setMaxRoundLength(int maxRoundLength) {

        this.maxRoundLength = maxRoundLength;
    }

    public UUID getUuid() {

        return uuid;
    }
}
