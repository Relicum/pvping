package com.relicum.pvpcore.Arenas;

import com.relicum.locations.SpawnPoint;
import com.relicum.pvpcore.Enums.ArenaState;
import com.relicum.pvpcore.Enums.ArenaType;

import java.util.Map;
import java.util.UUID;

/**
 * IZone represents an Arena that players can pvp in under controlled
 * conditions.
 */
public interface IZone {

    /**
     * Gets name of this Zone, this does not need to be unique.
     * <p>
     * Zones can be stored under a Collection of identical Zones that only
     * differ by location.
     * <p>
     * The {@link UUID} will be unique to each zone.
     *
     * @return the name
     */
    String getName();

    /**
     * Get the name id for the zone, this is made up of the name with id number
     * on the end.
     * <p>
     * The id number is just an auto incrementing number as another zone gets
     * added its id number will be 1 higher. This allows for easy human
     * identification.
     *
     * @return the name id
     */
    String getNameId();

    /**
     * Sets the zones current {@link ArenaState}.
     *
     * @param state the {@link ArenaState} to be set.
     */
    void setState(ArenaState state);

    /**
     * Gets the current zones {@link ArenaState}.
     *
     * @return the {@link ArenaState}
     */
    ArenaState getState();

    /**
     * Gets a player {@link SpawnPoint} by key.
     *
     * @param key the key
     * @return the spawn
     */
    SpawnPoint getSpawn(String key);

    /**
     * Add a player {@link SpawnPoint}.
     * <p>
     * The total number of spawns should be equal to the max players.
     *
     * @param paramKey the String key the spawn is identified
     * @param paramSpawn the {@link SpawnPoint} to add.
     */
    void addSpawn(String paramKey, SpawnPoint paramSpawn);

    /**
     * Set all the {@link SpawnPoint} at once.
     * <p>
     * The total number of spawns should be equal to the max players.
     *
     * @param points the map of {@link SpawnPoint}
     */
    void setSpawns(Map<String, SpawnPoint> points);

    /**
     * Checks if the specified spawn point is set.
     *
     * @param key to check if a spawn point has been set.
     * @return true if it has been set, false if ot.
     */
    boolean containsSpawn(String key);

    /**
     * Remove a player {@link SpawnPoint} by key.
     *
     * @param key the key of the {@link SpawnPoint} to remove.
     * @return the {@link SpawnPoint} that's been removed.
     */
    SpawnPoint removeSpawn(String key);

    /**
     * Set the {@link SpawnPoint} players will be sent at the end of the game.
     *
     * @param endSpawn the {@link SpawnPoint}
     */
    void setEndSpawn(SpawnPoint endSpawn);

    /**
     * Get the {@link SpawnPoint} players are sent to at the end of the game.
     *
     * @return the end spawn
     */
    SpawnPoint getEndSpawn();

    /**
     * Set the spectator {@link SpawnPoint} if spectating is allowed.
     *
     * @param specSpawn the spectator {@link SpawnPoint}
     */
    void setSpecSpawn(SpawnPoint specSpawn);

    /**
     * Get the spectator {@link SpawnPoint}.
     *
     * @return the spectator {@link SpawnPoint}
     */
    SpawnPoint getSpecSpawn();

    /**
     * Set the {@link ArenaType}.
     *
     * @param types the {@link ArenaType}
     */
    void setArenaType(ArenaType types);

    /**
     * Get the {@link ArenaType}.
     *
     * @return the arena type
     */
    ArenaType getArenaType();

    /**
     * Set max players.
     *
     * @param maxPlayers the max players
     */
    void setMaxPlayers(int maxPlayers);

    /**
     * Get max players.
     *
     * @return the max players
     */
    int getMaxPlayers();

    /**
     * Set min players.
     *
     * @param minPlayers the min players
     */
    void setMinPlayers(int minPlayers);

    /**
     * Get min players.
     *
     * @return the min players
     */
    int getMinPlayers();

}
