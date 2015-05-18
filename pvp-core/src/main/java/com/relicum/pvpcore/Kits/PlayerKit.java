package com.relicum.pvpcore.Kits;

import com.google.common.base.Objects;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Name: PlayerKit.java Created: 14 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class PlayerKit implements Cloneable {

    private final String name;
    private final ItemStack[] armor;
    private final ItemStack[] contents;
    private final Collection<PotionEffect> potionEffects;

    public PlayerKit(String name, ItemStack[] contents, ItemStack[] armor, Collection<PotionEffect> potionEffects) {
        this.name = name;
        this.contents = contents;
        this.armor = armor;
        this.potionEffects = potionEffects;
    }

    public ItemStack[] getArmor() {
        return armor;
    }

    public ItemStack[] getContents() {
        return contents;
    }

    public String getName() {
        return name;
    }

    public Collection<PotionEffect> getPotionEffects() {
        return potionEffects;
    }


    @Override
    public PlayerKit clone() {

        Object obj;

        try {
            obj = super.clone();

            if (PlayerKit.class.isAssignableFrom(obj.getClass())) {
                System.out.println("The Kit Object is cloneable and can be assigned from its super class");
            }
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return new PlayerKit(getName().intern(), getContents().clone(), getArmor().clone(), new ArrayList<>(getPotionEffects()));
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                       .add("armor", Arrays.toString(armor))
                       .add("name", name)
                       .add("contents", Arrays.toString(contents))
                       .add("potionEffects", potionEffects.toString())
                       .toString();
    }
}
