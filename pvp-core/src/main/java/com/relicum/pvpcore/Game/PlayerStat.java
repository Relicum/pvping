package com.relicum.pvpcore.Game;

import com.relicum.pvpcore.Enums.GameResult;

/**
 * PlayerStat simple container for a players stats for their current game.
 *
 * @author Relicum
 * @version 0.0.1
 */
public class PlayerStat {

    private String uuid;
    private GameResult result;
    private int deaths = 0;
    private int kills = 0;

    /**
     * Instantiates a new Player stat.
     *
     * @param uuid the players uuid as a string.
     */
    public PlayerStat(String uuid) {

        this.uuid = uuid;
    }

    /**
     * Gets the players uuid as a string.
     *
     * @return the uuid as a string.
     */
    public String getUuid() {

        return uuid;
    }

    /**
     * Gets the number of deaths.
     *
     * @return the number of deaths the player had in the game.
     */
    public int getDeaths() {

        return deaths;
    }

    /**
     * Sets the number of deaths the player had in the game.
     *
     * @param deaths the number of deaths
     */
    public void setDeaths(int deaths) {

        this.deaths = deaths;
    }

    /**
     * Gets the number of kills.
     *
     * @return the number of kills
     */
    public int getKills() {

        return kills;
    }

    /**
     * Sets number of kills the player had in the game.
     *
     * @param kills the number of kills
     */
    public void setKills(int kills) {

        this.kills = kills;
    }

    /**
     * Gets the game result.
     *
     * @return the result see {@link GameResult}
     */
    public GameResult getResult() {

        return result;
    }

    /**
     * Sets result of the game.
     *
     * @param result the result of the game, see {@link GameResult}
     */
    public void setResult(GameResult result) {

        this.result = result;
    }
}
