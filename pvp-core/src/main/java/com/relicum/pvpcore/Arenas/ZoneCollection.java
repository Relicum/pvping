package com.relicum.pvpcore.Arenas;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.relicum.pvpcore.Enums.ArenaState;
import com.relicum.pvpcore.Enums.ArenaType;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ZoneCollection
 *
 * @author Relicum
 * @version 0.0.1
 */
public class ZoneCollection {

    private String name;
    private Map<String, PvPZone> zones = Maps.newHashMap();
    private Set<String> enabledZones = Sets.newConcurrentHashSet();

    public ZoneCollection(String name) {

        this.name = name;

    }

    public String getName() {

        return name;
    }

    public boolean contains(String paramNameId) {

        return zones.containsKey(paramNameId);
    }

    public Set<Map.Entry<String, PvPZone>> getZones() {

        return zones.entrySet();
    }

    public void addZone(PvPZone paramZone) {

        zones.put(paramZone.getNameId(), paramZone);
        if (paramZone.isEnabled() && !paramZone.getState().equals(ArenaState.EDITING) && paramZone.getArenaType().equals(ArenaType.ARENA1v1))

        {
            enabledZones.add(paramZone.getNameId());
        }
    }

    public PvPZone getZone(String paramNameId) {

        return zones.get(paramNameId);
    }

    public PvPZone removeZone(String paramNameId) {

        return zones.remove(paramNameId);
    }

    public int getTotalZones() {

        return zones.size();
    }


    /**
     * Gets a list of enabled zones in this collection.
     *
     * @return the list of {@link PvPZone} that are enabled.
     */
    public Iterable<String> getEnabledZones() {

        return zones.values().stream().filter(PvPZone::isEnabled).map(PvPZone::getNameId).collect(Collectors.toList());
    }

    /**
     * Gets a list of disabled zones in this collection.
     *
     * @return the list of {@link PvPZone} that are disabled
     */
    public List<PvPZone> getDisabledZones() {

        return zones.values().stream().filter(p -> !p.isEnabled()).collect(Collectors.toList());
    }

    /**
     * Gets number of enabled zones in this collection.
     *
     * @return the number enabled zones
     */
    public int getNumEnabledZones() {

        return (int) zones.values().stream().filter(PvPZone::isEnabled).count();
    }

    /**
     * Gets number of disabled zones in this collection.
     *
     * @return the number of disabled zones
     */
    public int getNumDisabledZones() {

        return (int) zones.values().stream().filter(p -> !p.isEnabled()).count();
    }

    public int getZonesSetup() {

        return getZonesInState(ArenaState.EDITING);
    }

    public int getZonesWaiting() {

        return getZonesInState(ArenaState.WAITING);

    }


    public int getZonesInGame() {

        return getZonesInState(ArenaState.INGAME);
    }


    public int getZonesInState(ArenaState state) {

        return (int) zones.values().stream().filter(p -> p.getState().equals(state)).count();
    }

    public PvPZone getNextAvailableZone() throws Exception {

        for (PvPZone zone : zones.values()) {
            if (zone.getState().equals(ArenaState.WAITING)) {
                zone.setState(ArenaState.WAITING);
                return zone;
            }
        }
        throw new Exception("No Zones for " + getName() + " Available");
    }
}
