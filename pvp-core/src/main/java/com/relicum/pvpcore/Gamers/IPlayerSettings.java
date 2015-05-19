package com.relicum.pvpcore.Gamers;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;

/**
 * Name: IPlayerSettings.java Created: 03 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public interface IPlayerSettings {

    /**
     * Get armor.
     *
     * @return the {@link ItemStack} array containing the armor.
     */
    ItemStack[] getArmor();

    /**
     * Get contents.
     *
     * @return the {@link ItemStack} array containing the contents.
     */
    ItemStack[] getContents();

    /**
     * Gets effects.
     *
     * @return the list of {@link PotionEffect} the player had before joining.
     */
    Collection<PotionEffect> getEffects();

    /**
     * Gets Player game settings.
     *
     * @return the player settings
     */
    PlayerGameSettings getSettings();


}
