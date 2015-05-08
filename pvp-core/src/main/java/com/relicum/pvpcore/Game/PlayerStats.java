package com.relicum.pvpcore.Game;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import java.util.HashMap;
import java.util.Map;

/**
 * PlayerStats temporary holds the plays personally game stats and saves it to a
 * file.
 *
 * @author Relicum
 * @version 0.0.1
 */
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@SerializableAs("PlayerStats")
public class PlayerStats implements ConfigurationSerializable {

    private String uuid;
    private int played = 0;
    private int wins = 0;
    private int losses = 0;
    private int draws = 0;
    private int kills = 0;
    private int deaths = 0;
    private int streak = 0;
    private int highestStreak = 0;

    private PlayerStats() {

    }

    public PlayerStats(String uuid) {

        this.uuid = uuid;
    }

    /**
     * Increment the number of player wins by 1.
     */
    public void incrementWin() {

        this.wins += 1;
        this.streak += 1;
        if (streak > highestStreak)
            highestStreak = streak;
    }

    /**
     * Increment the number of player loss by 1.
     */
    public void incrementLoss() {

        this.losses += 1;
        if (streak > highestStreak)
            highestStreak = streak;

        this.streak = 0;
    }

    public void setLosses(int losses) {

        this.losses = losses;
    }

    /**
     * Increment the number of player draws by 1.
     */
    public void incrementDraws() {

        this.draws += 1;
    }

    /**
     * Increment kills by specified amount.
     *
     * @param kills the number of kills to increment the total by.
     */
    public void incrementKills(int kills) {

        this.kills += kills;
    }

    /**
     * Increment deaths by specified amount.
     *
     * @param deaths the number of deaths to increment the total by.
     */
    public void incrementDeaths(int deaths) {

        this.deaths += deaths;
    }

    /**
     * Increment the number of games played by specified amount.
     *
     * @param paramPlayed the number to increment the number of games played.
     */
    public void incrementPlayed(int paramPlayed) {

        this.played += paramPlayed;
    }

    /**
     * Increment games played by 1.
     */
    public void incrementPlayed() {

        this.played += 1;
    }

    /**
     * Gets games played.
     *
     * @return the number of games played
     */
    public int getPlayed() {

        return played;
    }

    /**
     * Gets players current KDR.
     *
     * @return the KDR
     */
    public int getKD() {

        return kills / deaths;
    }

    /**
     * Gets deaths.
     *
     * @return Value of deaths.
     */
    public int getDeaths() {

        return deaths;
    }

    /**
     * Gets draws.
     *
     * @return Value of draws.
     */
    public int getDraws() {

        return draws;
    }

    /**
     * Gets wins.
     *
     * @return Value of wins.
     */
    public int getWins() {

        return wins;
    }

    /**
     * Gets losses.
     *
     * @return Value of losses.
     */
    public int getLosses() {

        return losses;
    }

    /**
     * Gets highestStreak.
     *
     * @return Value of highestStreak.
     */
    public int getHighestStreak() {

        return highestStreak;
    }

    /**
     * Gets streak.
     *
     * @return Value of streak.
     */
    public int getStreak() {

        return streak;
    }

    /**
     * Gets uuid.
     *
     * @return Value of uuid.
     */
    public String getUuid() {

        return uuid;
    }

    /**
     * Gets kills.
     *
     * @return Value of kills.
     */
    public int getKills() {

        return kills;
    }

    @Override
    public Map<String, Object> serialize() {

        Map<String, Object> map = new HashMap<>();
        map.put("uuid", getUuid());
        map.put("played", getPlayed());
        map.put("wins", getWins());
        map.put("losses", getLosses());
        map.put("draws", getDraws());
        map.put("kills", getKills());
        map.put("deaths", getDeaths());
        map.put("streak", getStreak());
        map.put("highestStreak", getHighestStreak());
        return map;
    }

    public static PlayerStats deserialize(Map<String, Object> map) {

        Object objUUID = map.get("uuid"), objPlay = map.get("played"), objWins = map.get("wins"), objLosses = map.get("losses"), objDraws = map.get("draws"), objKills = map
                .get("kills"), objDeaths = map.get("deaths"), objStreak = map.get("streak"), objHight = map.get("highestStreak");

        return new PlayerStats((String) objUUID, (Integer) objWins, (Integer) objLosses, (Integer) objDraws, (Integer) objKills, (Integer) objDeaths,
                (Integer) objStreak, (Integer) objHight, (Integer) objPlay);
    }
}
