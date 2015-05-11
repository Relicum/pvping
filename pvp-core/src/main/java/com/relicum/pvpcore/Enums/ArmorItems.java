package com.relicum.pvpcore.Enums;

/**
 * Name: ArmorItems.java Created: 27 April 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public enum ArmorItems {

    GOLD_BOOTS("BOOTS"),
    GOLD_LEGGINGS("LEGGINGS"),
    GOLD_CHESTPLATE("CHESTPLATE"),
    GOLD_HELMET("HELMET"),
    IRON_BOOTS("BOOTS"),
    IRON_LEGGINGS("LEGGINGS"),
    IRON_CHESTPLATE("CHESTPLATE"),
    IRON_HELMET("HELMET"),
    DIAMOND_BOOTS("BOOTS"),
    DIAMOND_LEGGINGS("LEGGINGS"),
    DIAMOND_CHESTPLATE("CHESTPLATE"),
    DIAMOND_HELMET("HELMET"),
    CHAINMAIL_BOOTS("BOOTS"),
    CHAINMAIL_LEGGINGS("LEGGINGS"),
    CHAINMAIL_CHESTPLATE("CHESTPLATE"),
    CHAINMAIL_HELMET("HELMET"),
    LEATHER_BOOTS("BOOTS"),
    LEATHER_LEGGINGS("LEGGINGS"),
    LEATHER_CHESTPLATE("CHESTPLATE"),
    LEATHER_HELMET("HELMET");

    private String part;

    ArmorItems(String paramPart) {

        this.part = paramPart;
    }

    /**
     * Search the enum to see if a given item is on the list
     * <p>
     * Return true if it is false if it's not
     *
     * @param item the item {@link org.bukkit.Material} in it's String format
     * @return the boolean
     */
    public static boolean find(String item) {

        for (ArmorItems v : values()) {
            if (v.name().equalsIgnoreCase(item)) {
                return true;
            }
        }
        return false;
    }

    public String getItem() {

        return part;
    }

}
