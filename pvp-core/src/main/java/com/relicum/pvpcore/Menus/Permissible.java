package com.relicum.pvpcore.Menus;

import org.bukkit.entity.Player;

/**
 * Permissible is an object that can have a simple permission assigned to it.
 *
 * @author Relicum
 * @version 0.0.1
 */
public interface Permissible {

    /**
     * Has permission.
     *
     * @param player the player to check if they have permission
     * @return the boolean
     */
    boolean hasPermission(Player player);

    /**
     * Set string based permission.
     *
     * @param name the permission
     */
    void setPermission(String name);

    /**
     * Get the permission.
     *
     * @return the String permission
     */
    String getPermission();

    /**
     * Checks to see if a permission has been set.
     *
     * @return true if a permission has been set.
     */
    boolean isPermissionSet();

    /**
     * Is a permission required for the activity to be performed.
     *
     * @return true is a permission is required, defaults to false
     */
    boolean required();

}
