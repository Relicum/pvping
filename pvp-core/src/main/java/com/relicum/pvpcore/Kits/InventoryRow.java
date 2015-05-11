package com.relicum.pvpcore.Kits;

import com.relicum.pvpcore.Menus.Slot;
import org.bukkit.inventory.ItemStack;

/**
 * InventoryRow
 *
 * @author Relicum
 * @version 0.0.1
 */
public interface InventoryRow {

    void addItem(Slot slot, ItemStack item);

    ItemStack[] getItems();

    boolean isModifiable();

    void setModifiable(boolean mod);

    boolean isDropable();

    void setDropable(boolean drop);

}
