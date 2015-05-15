package com.relicum.pvpcore.Arenas;

import com.google.common.collect.Maps;
import com.relicum.pvpcore.Enums.ArenaState;

import java.util.Map;
import java.util.Set;

/**
 * ZoneCollection
 *
 * @author Relicum
 * @version 0.0.1
 */
public class ZoneCollection {

    private String name;
    private Map<String, PvPZone> zones = Maps.newHashMap();

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

        zones.putIfAbsent(paramZone.getNameId(), paramZone);
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

    public int getZonesSetup() {

        return getZonesInState(ArenaState.SETUP);
    }

    public int getZonesWaiting() {

        return getZonesInState(ArenaState.WAITING);

    }

    public int getZonesInGame() {

        return getZonesInState(ArenaState.INGAME);
    }

    public int getZonesDisabled() {

        return getZonesInState(ArenaState.DISABLED);
    }

    public int getZonesInState(ArenaState state) {

        return (int) zones.values()
                             .stream()
                             .filter(p -> p.getState()
                                                  .equals(state))
                             .count();
    }

    public PvPZone getNextAvailableZone() throws Exception {

        for (PvPZone zone : zones.values()) {
            if (zone.getState()
                        .equals(ArenaState.WAITING)) {
                zone.setState(ArenaState.WAITING);
                return zone;
            }
        }
        throw new Exception("No Zones for " + getName() + " Available");
    }
}
