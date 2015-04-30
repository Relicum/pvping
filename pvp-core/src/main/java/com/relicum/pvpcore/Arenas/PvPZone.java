package com.relicum.pvpcore.Arenas;

import com.relicum.locations.PointList;
import com.relicum.locations.SpawnPoint;
import com.relicum.pvpcore.Enums.ArenaState;
import com.relicum.pvpcore.Enums.ArenaType;
import org.apache.commons.lang.Validate;
import java.util.List;

/**
 * PvPZone represents a simple 1v1 PVP battle between 2 players.
 *
 * @author Relicum
 * @version 0.0.1
 */
public class PvPZone {

    private String name;
    private PointList<SpawnPoint> spawns;
    private SpawnPoint endSpawn;
    private SpawnPoint specSpawn;
    private int minPlayers;
    private int maxPlayers;
    private ArenaType arenaType;
    private ArenaState state = ArenaState.LOADING;

    public String getName() {
        return name;
    }

    public ArenaState getState() {

        return state;
    }

    public void setState(ArenaState state) {

        this.state = state;
    }

    public SpawnPoint getEndSpawn() {
        return endSpawn;
    }

    public void setEndSpawn(SpawnPoint endSpawn) {
        this.endSpawn = endSpawn;
    }

    public void addSpawn(SpawnPoint paramSpawn) {
        this.spawns.addPoint(paramSpawn);
    }

    public SpawnPoint getSpawn(int index) {
        return spawns.getPoint(index);
    }

    public SpawnPoint removeSpawn(int index) {
        return spawns.removePoint(index);
    }

    public void setSpawns(List<SpawnPoint> points) {
        Validate.isTrue(points.size() <= maxPlayers, "Too many spawn points, maximum is " + maxPlayers);
        spawns = new PointList<>(points);
    }

    public SpawnPoint getSpecSpawn() {
        return specSpawn;
    }

    public void setSpecSpawn(SpawnPoint specSpawn) {
        this.specSpawn = specSpawn;
    }

    public ArenaType getArenaType() {
        return arenaType;
    }

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

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }
}
