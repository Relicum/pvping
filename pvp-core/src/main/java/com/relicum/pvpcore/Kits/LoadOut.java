package com.relicum.pvpcore.Kits;

import com.google.common.base.Objects;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Collection;

/**
 * LoadOut //todo needs documenting.
 *
 * @author Relicum
 * @version 0.0.1
 */
public class LoadOut {

    private String loadoutName;
    private PlayerInventory inventory;
    private Collection<PotionEffect> effects = new ArrayList<>();


    public LoadOut(String loadoutName) {

        this.loadoutName = loadoutName;
    }

    public LoadOut(PlayerInventory inventory, Collection<PotionEffect> effects, String loadoutName) {

        this.inventory = inventory;
        this.effects.addAll(effects);
        this.loadoutName = loadoutName;
    }

    public String getLoadoutName() {

        return loadoutName;
    }

    public PlayerInventory getInventory() {

        return inventory;
    }

    public ItemStack[] getArmor() {

        return inventory.getArmorContents().clone();
    }

    public ItemStack[] getContents() {

        return inventory.getContents().clone();
    }

    public Collection<PotionEffect> getEffects() {

        return new ArrayList<>(effects);
    }


    public void setLoadoutName(String loadoutName) {
        this.loadoutName = loadoutName;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                       .add("loadoutName", loadoutName)
                       .add("inventory", inventory.toString())
                       .add("effects", effects.toString())
                       .toString();
    }
}
