package com.relicum.pvpcore.Menus;

/**
 * Spawns1v1 used to define constants for the different spawn names.
 *
 * @author Relicum
 */
public enum Spawns1v1 {

    PLAYER_ONE("spawn1", "Spawn 1"), PLAYER_TWO("spawn2", "Spawn 2"), SPECTATOR("spectator",
                                                                                "Spectator Spawn"), END("end", "End Game Spawn");

    private final String theName;
    private final String title;

    Spawns1v1(String string, String title) {

        this.theName = string;
        this.title = title;
    }

    public String getName() {

        return theName;
    }

    public String getTitle() {

        return title;
    }

    public static Spawns1v1 fromName(String theName) {

        switch (theName) {
            case "spawn1":
                return Spawns1v1.PLAYER_ONE;
            case "spawn2":
                return Spawns1v1.PLAYER_TWO;
            case "spectator":
                return Spawns1v1.SPECTATOR;
            case "end":
                return Spawns1v1.END;
            default:
                return null;
        }
    }

    public static Spawns1v1 fromTitle(String theTitle) {

        switch (theTitle) {
            case "Spawn 1":
                return Spawns1v1.PLAYER_ONE;
            case "Spawn 2":
                return Spawns1v1.PLAYER_TWO;
            case "Spectator Spawn":
                return Spawns1v1.SPECTATOR;
            case "End Game Spawn":
                return Spawns1v1.END;
            default:
                return null;
        }
    }
}
