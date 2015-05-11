package com.relicum.duel.Menus;

import com.relicum.pvpcore.Menus.AbstractItem;
import com.relicum.pvpcore.Menus.ActionHandler;
import com.relicum.pvpcore.Menus.ClickAction;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Name: SpawnPointItem.java Created: 06 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class SpawnPointItem extends AbstractItem {

    public ItemStack altIcon;
    public boolean state = false;

    /**
     * Instantiates a new Abstract item.
     *
     * @param paramItem the {@link ItemStack} used as the icon
     * @param paramSlot the slot inventory position
     */
    public SpawnPointItem(ItemStack paramItem, int paramSlot) {

        super(paramItem, paramSlot);
    }

    /**
     * Instantiates a new Abstract item.
     *
     * @param paramItem     the {@link ItemStack} used as the icon
     * @param paramSlot     the slot inventory position
     * @param paramAction   the action to perform when the icon is clicked
     *                      {@link ClickAction}
     * @param actionHandler
     */
    public SpawnPointItem(ItemStack paramItem, ItemStack altIcon, int paramSlot, ClickAction paramAction, ActionHandler actionHandler) {

        super(paramItem, paramSlot, paramAction, actionHandler);
        this.altIcon = altIcon;
    }

    /**
     * Instantiates a new Abstract item.
     *
     * @param paramItem     the {@link ItemStack} used as the icon
     * @param paramSlot     the slot inventory position
     * @param paramAction   the action to perform when the icon is clicked
     *                      {@link ClickAction}
     * @param actionHandler the icon display name
     */
    public SpawnPointItem(ItemStack paramItem, int paramSlot, ClickAction paramAction, ActionHandler actionHandler) {

        super(paramItem, paramSlot, paramAction, actionHandler);
    }

    /**
     * Instantiates a new Abstract item.
     *
     * @param paramItem        the {@link ItemStack} used as the icon
     * @param paramSlot        the slot inventory position
     * @param paramAction      the action to perform when the icon is clicked
     *                         {@link ClickAction}
     * @param paramDisplayName the icon display name
     * @param paramLores       the icon lores
     */
    public SpawnPointItem(ItemStack paramItem, int paramSlot, ClickAction paramAction, String paramDisplayName, List<String> paramLores) {

        super(paramItem, paramSlot, paramAction, paramDisplayName, paramLores);
    }

    /**
     * Gets altIcon.
     *
     * @return Value of altIcon.
     */
    public ItemStack getAltIcon() {

        return altIcon;
    }

    /**
     * Sets new altIcon.
     *
     * @param altIcon New value of altIcon.
     */
    public void setAltIcon(ItemStack altIcon) {

        this.altIcon = altIcon;
    }

    /**
     * Gets state.
     *
     * @return Value of state.
     */
    public boolean isState() {

        return state;
    }

    /**
     * Sets new state.
     *
     * @param state New value of state.
     */
    public void setState(boolean state) {

        this.state = state;
    }
}
