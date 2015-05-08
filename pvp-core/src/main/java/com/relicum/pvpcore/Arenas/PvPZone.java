package com.relicum.pvpcore.Arenas;

import com.relicum.locations.PointsGroup;
import com.relicum.locations.SpawnPoint;
import com.relicum.pvpcore.Enums.ArenaState;
import com.relicum.pvpcore.Enums.ArenaType;
import com.relicum.pvpcore.FormatUtil;
import com.relicum.pvpcore.Menus.OpenMenuItem;
import lombok.ToString;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * PvPZone represents a simple 1v1 PVP battle between 2 players.
 *
 * @author Relicum
 * @version 0.0.1
 */
@ToString
public class PvPZone implements IZone {

    private String nameId;
    private String name;
    private int id;
    private PointsGroup<String, SpawnPoint> spawns;
    private SpawnPoint endSpawn;
    private SpawnPoint specSpawn;
    private int minPlayers;
    private int maxPlayers;
    private ArenaType arenaType;
    private ArenaState state = ArenaState.SETUP;
    private boolean editing = false;

    private PvPZone() {

    }

    public PvPZone(ArenaType arenaType, String name, int nextId) {

        this.name = name;
        this.id = nextId;
        this.nameId = name + "-" + nextId;
        setArenaType(arenaType);
        endSpawn = new SpawnPoint("world", -159.0d, 68.0d, 248.0d);
    }

    /**
     * Is editing.
     *
     * @return the boolean
     */
    public boolean isEditing() {

        return editing;
    }

    /**
     * Sets editing.
     *
     * @param editing the editing
     */
    public void setEditing(boolean editing) {

        this.editing = editing;
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
    public String getNameId() {

        return nameId;
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
    public SpawnPoint getSpawn(String key) {

        Validate.notNull(key);
        return spawns.get(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addSpawn(String paramKey, SpawnPoint paramSpawn) {

        Validate.notNull(paramKey);
        Validate.notNull(paramSpawn);
        spawns.put(paramKey, paramSpawn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSpawns(Map<String, SpawnPoint> points) {

        Validate.isTrue(points.size() <= maxPlayers, "You can not add more spawn points than max players");

        for (Map.Entry<String, SpawnPoint> entry : points.entrySet())
        {
            spawns.put(entry.getKey(), entry.getValue());
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsSpawn(String key) {

        Validate.notNull(key);
        return spawns.containsKey(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SpawnPoint removeSpawn(String key) {

        Validate.notNull(key);

        return spawns.remove(key);
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

        switch (types)
        {

        case ARENA1v1: {
            setMinPlayers(2);
            setMaxPlayers(2);
            spawns = new PointsGroup<>(2);
            arenaType = types;
            break;
        }
        case ARENAFFA: {

            setMinPlayers(2);
            setMaxPlayers(6);
            spawns = new PointsGroup<>(6);
            arenaType = types;
        }
        default: {

            setMinPlayers(2);
            setMaxPlayers(2);
            spawns = new PointsGroup<>(2);
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

    public int getId() {

        return id;
    }

    public int getSpawnsSize() {

        return spawns.size();
    }

    public OpenMenuItem getOpenMenuItem(int index) {

        return new OpenMenuItem("&a" + nameId, new ItemStack(Material.PAPER, 1), index, Arrays.asList(" ", "&6PVPZone name:&b " + name,
            "&6PVPZone ID:&b " + id, "&6Zone Type:&b " + arenaType.getType(), "&6Zone State: &b " + state.name(), " ", "&aMin Players &c" + minPlayers,
            "&aMax Players &c" + maxPlayers, " ", "&6End Spawn:", "" + FormatUtil.getFormattedPoint(endSpawn)));

    }

    public List<String> getLore() {

        return Arrays.asList(" ", "&6PVPZone name:&b " + name, "&6PVPZone ID:&b " + id, "&6Zone Type:&b " + arenaType.getType(),
            "&6Zone State: &b " + state.name(), " ", "&aMin Players &c" + minPlayers, "&aMax Players &c" + maxPlayers, " ", "&6End Spawn:",
            "" + FormatUtil.getFormattedPoint(endSpawn));
    }

}
