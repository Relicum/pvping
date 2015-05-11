package com.relicum.pvpcore.Enums;

/**
 * The enum Arena type holds the different arenas and their short hand name.
 */
public enum ArenaType {
    ARENA1v1("1v1"), ARENA2v2("2v2"), ARENAPvP("pvp"), ARENAFFA("ffa"), ARENAPB("pb");

    private final String type;

    ArenaType(String paramType) {

        type = paramType;
    }

    /**
     * Gets the short hand name of the arena type.
     *
     * @return the short hand name of the arena type.
     */
    public String getType() {

        return type;
    }

}
