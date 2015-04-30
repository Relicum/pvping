package com.relicum.pvpcore.Arenas;

import com.google.common.collect.Maps;
import com.relicum.pvpcore.Enums.ArenaState;
import java.util.Map;
import java.util.UUID;

/**
 * PvPZones
 *
 * @author Relicum
 * @version 0.0.1
 */
public class PvPZones {

    private String name;
    private Map<UUID, PvPZone> zones = Maps.newHashMap();

    public PvPZones(String name) {

        this.name = name;
    }

    public String getName() {

        return name;
    }

    public boolean contains(UUID paramUUID) {

        return zones.containsKey(paramUUID);
    }

    public void addZone(PvPZone paramZone) {

        zones.putIfAbsent(paramZone.getUuid(), paramZone);
    }

    public PvPZone getZone(UUID paramUUID) {

        return zones.get(paramUUID);
    }

    public PvPZone removeZone(UUID paramUUID) {

        return zones.remove(paramUUID);
    }

    public int getTotalZones() {

        return zones.size();
    }

    public int getAvailableZones() {

        return getZonesInState(ArenaState.AVAILABLE);
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

    public int getZonesRestarting() {

        return getZonesInState(ArenaState.RESTARTING);
    }

    public int getZonesInState(ArenaState state) {

        return (int) zones.values().stream().filter(p -> p.getState().equals(state)).count();
    }

    public PvPZone getNextAvailableZone() throws Exception {

        for (PvPZone zone : zones.values()) {
            if (zone.getState().equals(ArenaState.AVAILABLE)) {
                zone.setState(ArenaState.WAITING);
                return zone;
            }
        }
        throw new Exception("No Zones for " + getName() + " Available");
    }
}
