package com.relicum.pvpcore.Kits;

import org.bukkit.inventory.ItemStack;

/**
 * InventoryRow
 *
 * @author Relicum
 * @version 0.0.1
 */
public interface InventoryRow {

    void addItem(int slot, ItemStack item);

    ItemStack[] getItems();

    boolean isModifiable();

    void setModifiable(boolean mod);

}
