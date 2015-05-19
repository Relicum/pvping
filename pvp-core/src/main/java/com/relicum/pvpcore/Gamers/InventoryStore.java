package com.relicum.pvpcore.Gamers;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Collection;

/**
 * InventoryStore immutable object that stores the contents of a
 * {@link org.bukkit.inventory.PlayerInventory}.
 *
 * @author Relicum
 * @version 0.0.1
 */
public class InventoryStore {

    private final ItemStack[] armor;
    private final ItemStack[] contents;
    private final PlayerSettings settings;
    private final Collection<PotionEffect> effects;


    public InventoryStore(ItemStack[] armor, ItemStack[] contents, Collection<PotionEffect> potionEffects, PlayerSettings paramSettings) {

        this.armor = armor.clone();
        this.contents = contents.clone();
        this.effects = new ArrayList<>(potionEffects.size());
        this.effects.addAll(potionEffects);
        this.settings = paramSettings;

    }

    public InventoryStore(PlayerInventory playerInventory, Collection<PotionEffect> potionEffects, PlayerSettings paramSettings) {

        this.armor = playerInventory.getArmorContents().clone();
        this.contents = playerInventory.getContents().clone();
        this.effects = new ArrayList<>(potionEffects.size());
        this.effects.addAll(potionEffects);
        this.settings = paramSettings;

    }

    /**
     * Get armor.
     *
     * @return the {@link ItemStack} array containing the armor.
     */
    public ItemStack[] getArmor() {

        return armor;
    }

    /**
     * Get contents.
     *
     * @return the {@link ItemStack} array containing the contents.
     */
    public ItemStack[] getContents() {

        return contents;
    }

    /**
     * Gets effects.
     *
     * @return the list of {@link PotionEffect} the player had before joining.
     */
    public Collection<PotionEffect> getEffects() {

        return effects;
    }


    /**
     * Gets Player settings.
     *
     * @return the player settings
     */
    public PlayerSettings getSettings() {

        return settings;
    }
}
