package com.relicum.pvpcore.Arenas;

import com.relicum.locations.PointList;
import com.relicum.locations.SpawnPoint;
import com.relicum.pvpcore.Enums.ArenaState;
import com.relicum.pvpcore.Enums.ArenaType;
import org.apache.commons.lang.Validate;
import java.util.List;
import java.util.UUID;

/**
 * PvPZone represents a simple 1v1 PVP battle between 2 players.
 *
 * @author Relicum
 * @version 0.0.1
 */
public class PvPZone implements IZone {

    private UUID uuid;
    private String name;
    private PointList<SpawnPoint> spawns;
    private SpawnPoint endSpawn;
    private SpawnPoint specSpawn;
    private int minPlayers;
    private int maxPlayers;
    private ArenaType arenaType;
    private ArenaState state = ArenaState.LOADING;

    private PvPZone() {
    }

    public PvPZone(ArenaType arenaType, String name) {
        this.arenaType = arenaType;
        this.name = name;
        this.uuid = UUID.randomUUID();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID getUuid() {
        return uuid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArenaState getState() {

        return state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setState(ArenaState state) {

        this.state = state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SpawnPoint getEndSpawn() {
        return endSpawn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setEndSpawn(SpawnPoint endSpawn) {
        this.endSpawn = endSpawn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addSpawn(SpawnPoint paramSpawn) {
        this.spawns.addPoint(paramSpawn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SpawnPoint getSpawn(int index) {
        return spawns.getPoint(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SpawnPoint removeSpawn(int index) {
        return spawns.removePoint(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSpawns(List<SpawnPoint> points) {
        Validate.isTrue(points.size() <= maxPlayers, "Too many spawn points, maximum is " + maxPlayers);
        spawns = new PointList<>(points);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SpawnPoint getSpecSpawn() {
        return specSpawn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSpecSpawn(SpawnPoint specSpawn) {
        this.specSpawn = specSpawn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArenaType getArenaType() {
        return arenaType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setArenaType(ArenaType types) {

        switch (types) {

            case ARENA1v1: {
                setMinPlayers(2);
                setMaxPlayers(2);
                spawns = new PointList<>(2);
                arenaType = types;
                break;
            }
            case ARENAFFA: {

                setMinPlayers(2);
                setMaxPlayers(6);
                spawns = new PointList<>(6);
                arenaType = types;
            }
            default: {

                setMinPlayers(2);
                setMaxPlayers(2);
                spawns = new PointList<>(2);
                arenaType = ArenaType.ARENA1v1;
            }
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMinPlayers() {
        return minPlayers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }
}
