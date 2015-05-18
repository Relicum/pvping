package com.relicum.pvpcore.Menus;

import com.google.common.base.Objects;
import com.relicum.pvpcore.FormatUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * MStack simple class for persisting an {@link ItemStack} that is used on a {@link ActionMenu}
 *
 * @author Relicum
 * @version 0.0.1
 */
public class BStack {

    private String displayName;
    private List<String> lore = new ArrayList<>();
    private short dur;


    public BStack(String displayName, List<String> lores) {

        this.displayName = FormatUtil.colorize(displayName);
        this.lore.addAll(lores);
        this.dur = (short) 10;
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

    public short getDur() {
        return dur;
    }

    public ItemStack getBStack() {

        ItemStack stack = new ItemStack(Material.INK_SACK);
        stack.setDurability(dur);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(getDisplayName());
        meta.setLore(getLore());

        return stack.clone();
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                       .add("displayName", displayName)
                       .add("lore", lore.toString())
                       .toString();
    }
}
