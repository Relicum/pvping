package com.relicum.pvpcore.Menus;

import com.google.common.base.Objects;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * MStack simple class for persisting an {@link ItemStack} that is used on a {@link com.relicum.pvpcore.Menus.ActionMenu}
 *
 * @author Relicum
 * @version 0.0.1
 */
public class MStack {

    private ItemStack stack;
    private String displayName;
    private List<String> lore = new ArrayList<>();
    private short durability = 0;

    public MStack(ItemStack stack) {
        this.stack = stack;
        this.durability = stack.getDurability();
    }

    /**
     * Gets the {@link ItemStack} to be added to a menu.
     *
     * @return the {@link ItemStack}
     */
    public ItemStack getStack() {
        return stack.clone();
    }

    /**
     * Gets {@link ItemStack} display name.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets items lore.
     *
     * @return the {@link ItemStack} lore
     */
    public List<String> getLore() {
        return lore;
    }

    public short getDurability() {
        return durability;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                       .add("displayName", displayName)
                       .add("stack", stack.toString())
                       .add("lore", lore.toString())
                       .toString();
    }
}
