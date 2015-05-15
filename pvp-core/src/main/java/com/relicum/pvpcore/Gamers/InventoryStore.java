package com.relicum.pvpcore.Gamers;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    private transient Collection<PotionEffect> effects;
    private List<ItemStack> potionStack;

    public InventoryStore(ItemStack[] armor, ItemStack[] contents, List<ItemStack> potionEffects, PlayerSettings paramSettings) {

        this.armor = armor.clone();
        this.contents = contents.clone();
        this.potionStack = new ArrayList<>(potionEffects.size());
        this.potionStack.addAll(potionEffects);
        this.settings = paramSettings;
        this.effects = new ArrayList<>(potionEffects.size());
        this.effects.addAll(getPotionEffects());
    }

    public InventoryStore(PlayerInventory playerInventory, List<ItemStack> potionEffects, PlayerSettings paramSettings) {

        this.armor = playerInventory.getArmorContents().clone();
        this.contents = playerInventory.getContents().clone();
        this.potionStack = new ArrayList<>(potionEffects.size());
        this.potionStack.addAll(potionEffects);
        this.settings = paramSettings;
        this.effects = new ArrayList<>(potionEffects.size());
        this.effects.addAll(getPotionEffects());
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

    private Collection<PotionEffect> getPotionEffects() {


        List<PotionEffect> ef = new ArrayList<>(potionStack.size());
        for (ItemStack stack : potionStack) {
            PotionMeta meta = (PotionMeta) stack.getItemMeta();
            ef.add(meta.getCustomEffects().get(0));
        }
        return ef;
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
