package com.relicum.pvpcore.Menus;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Name: MenuStack.java Created: 16 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class MenuStack {

    private Map<Integer, BooleanItem> items = new HashMap<>();

    public void addSlotStack(int slot, BooleanItem stack) {

        this.items.put(slot, stack);
    }

    public ItemStack getItem(int slot) {
        return items.get(slot).getItem();
    }

    public BooleanItem getSlotStack(int slot) {

        return items.get(slot);
    }

    public int Size() {
        return items.size();
    }

    public Iterator<BooleanItem> getItemsIterator() {

        return items.values().iterator();
    }

}
