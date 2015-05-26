package com.relicum.pvpcore.Arenas;

import com.relicum.locations.PointsGroup;
import com.relicum.locations.SpawnPoint;
import com.relicum.pvpcore.Enums.ArenaState;
import com.relicum.pvpcore.Enums.ArenaType;
import com.relicum.pvpcore.Enums.Symbols;
import com.relicum.pvpcore.Menus.Spawns1v1;
import lombok.ToString;
import org.apache.commons.lang.Validate;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.relicum.pvpcore.Enums.Symbols.CROSS;
import static com.relicum.pvpcore.Enums.Symbols.TICK;

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
    private ArenaState state = ArenaState.EDITING;
    private boolean enabled = false;
    private transient boolean editing = false;

    private PvPZone() {

    }

    public PvPZone(ArenaType arenaType, String name, int nextId) {

        this.name = name;
        this.id = nextId;
        this.nameId = name + "-" + nextId;
        setArenaType(arenaType);


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
    public void setState(ArenaState state) {

        this.state = state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SpawnPoint getSpawn(@Nullable String key) {

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

        for (Map.Entry<String, SpawnPoint> entry : points.entrySet()) {
            spawns.put(entry.getKey(), entry.getValue());
        }

    }

    /**
     * Checks if the spectator spawn is set.
     *
     * @return true if it is set, false if not
     */
    public boolean spectatorSet() {

        return specSpawn != null;
    }

    /**
     * Checks if the game end spawn is set.
     *
     * @return true if it is set, false if not
     */
    public boolean endSpawnSet() {

        return endSpawn != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsSpawn(@Nullable String key) {

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

        switch (types) {

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
                break;
            }
            default: {

                this.setMinPlayers(2);
                this.setMaxPlayers(2);
                spawns = new PointsGroup<>(2);
                arenaType = ArenaType.ARENA1v1;
            }
        }

    }

    /**
     * Checks if this zone can be enabled.
     * <p>All spawns need to be set before it can be enabled
     *
     * @return true if it can be enable, false if it can not.
     */
    public boolean canBeEnabled() {

        return spectatorSet() && endSpawnSet() && (getSpawnsSize() == 2);
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

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnable(boolean enable) {
        this.enabled = enable;
    }

    public int getId() {

        return id;
    }

    public int getSpawnsSize() {

        return spawns.size();
    }


    public List<String> getLore() {

        List<String> t = new ArrayList<>();
        t.add(" ");
        t.add("&6PVPZone name:&b " + name);
        t.add("&6PVPZone ID:&b " + id);
        t.add("&6Zone Type:&b " + arenaType.getType());
        t.add(" ");
        if (isEnabled()) {
            t.add("&6Zone is:&a Enabled " + Symbols.TICK.asString());
        }
        else {
            t.add("&6Zone is:&4 Disabled " + Symbols.CROSS.asString());
        }

        t.add("&6Zone State: &b " + state.name());

        if (isEditing()) {

            t.add("&6Edit Mode: &4 ON");
        }

        else {
            t.add("&6Edit Mode: &aOFF");
        }

        t.add(" ");
        t.add("&aMin Players &c" + minPlayers);
        t.add("&aMax Players &c" + maxPlayers);
        t.add(" ");
        t.add("&6SpawnPoints Set");
        t.add(" ");

        for (Spawns1v1 sp : Spawns1v1.values()) {

            if (sp.equals(Spawns1v1.END)) {

                if (this.endSpawnSet()) {

                    t.add("&6End Spawn: &a&l " + TICK.asString());
                }
                else {

                    t.add("&6End Spawn: &4&l " + CROSS.asString());
                }
            }

            else if (sp.equals(Spawns1v1.SPECTATOR)) {

                if (this.spectatorSet()) {

                    t.add("&6Spectator Spawn: &a&l " + TICK.asString());
                }
                else {

                    t.add("&6Spectator Spawn: &4&l " + CROSS.asString());
                }
            }

            else {

                if (containsSpawn(sp.getName())) {

                    t.add("&6" + sp.getTitle() + ": &a&l " + TICK.asString());
                }
                else {

                    t.add("&6" + sp.getTitle() + ": &4&l " + CROSS.asString());
                }
            }
        }

        return t;

    }


}
