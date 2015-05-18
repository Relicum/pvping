package com.relicum.pvpcore.Menus;

import lombok.ToString;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * BooleanItem
 *
 * @author Relicum
 * @version 0.0.1
 */
@ToString(callSuper = true)
public class BooleanItem extends AbstractItem {

    private BStack on;
    private BStack off;
    private boolean active;

    public BooleanItem(ItemStack paramItem, int paramSlot, ClickAction paramAction, ActionHandler actionHandler) {
        super(paramItem, paramSlot, paramAction, actionHandler);
    }


    public BooleanItem(BStack on, BStack off, int slot, boolean isActive, ClickAction action, ActionHandler handler) {

        super(new ItemStack(Material.INK_SACK, 1), slot, action, handler);
        this.on = on;
        this.off = off;
        active = isActive;
        setIcon(updateIcon());

    }

    public void setOn(BStack ons) {
        this.on = ons;
    }

    public void setOff(BStack offs) {
        this.off = offs;
    }

    public void init() {
        setIcon(updateIcon());
    }

    public ItemStack updateIcon() {

        if (active) {
            return getEnableStack();

        }
        else {

            return getDisabledStack();
        }
    }

    public ItemStack getEnableStack() {

        ItemStack en = new ItemStack(Material.INK_SACK, 1);
        en.setDurability((short) 10);
        ItemMeta meta = en.getItemMeta();
        //super.setDescription(on.getLore());
        // super.setText(on.getDisplayName());
        meta.setDisplayName(on.getDisplayName());
        meta.setLore(on.getLore());
        en.setItemMeta(meta);
        return en;

    }

    public ItemStack getDisabledStack() {

        ItemStack dis = new ItemStack(Material.INK_SACK, 1);
        dis.setDurability((short) 8);
        ItemMeta meta = dis.getItemMeta();
        //super.setDescription(on.getLore());
        // super.setText(on.getDisplayName());
        meta.setDisplayName(off.getDisplayName());
        meta.setLore(off.getLore());
        dis.setItemMeta(meta);
        return dis;
    }


    public boolean isActive() {
        return active;
    }

    /**
     * Set active to switch the icon depending on state, if the item is in its on position set to true.
     *
     * @param act set to true for its positive state or false for its negative state.
     */
    public void setActive(boolean act) {

        this.active = act;

    }


}
