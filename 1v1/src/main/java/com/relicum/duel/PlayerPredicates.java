package com.relicum.duel;

import org.bukkit.entity.Player;

import java.util.function.Predicate;

/**
 * Name: PlayerPredicates.java Created: 13 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class PlayerPredicates {

    public static Predicate<Player> hasPermission(String perm) {

        return p -> p.hasPermission(perm) || p.isOp();
    }

    public static Predicate<Player> inWorld(String world) {

        return player -> player.getWorld().getName().equalsIgnoreCase(world);
    }

    public static boolean test(Player player, Predicate<Player> predicate) {

        return predicate.test(player);

    }

    public static boolean inWorldWithPermission(Player player) {

        return hasPermission("duel.player.join").and(inWorld("world")).test(player);
    }
}
