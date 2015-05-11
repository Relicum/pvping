package com.relicum.pvpcore.Menus;

import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Name: ActionItem.java Created: 04 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class ActionItem extends AbstractItem {


    /**
     * Instantiates a new Abstract item.
     *
     * @param paramItem the {@link ItemStack} used as the icon
     * @param paramSlot the slot inventory position
     */
    public ActionItem(ItemStack paramItem) {

        super(paramItem);
    }

    /**
     * Instantiates a new Abstract item.
     *
     * @param paramItem the {@link ItemStack} used as the icon
     * @param paramSlot the slot inventory position
     */
    public ActionItem(ItemStack paramItem, int paramSlot) {

        super(paramItem, paramSlot);
    }

    /**
     * Instantiates a new Abstract item.
     *
     * @param paramItem   the {@link ItemStack} used as the icon
     * @param paramSlot   the slot inventory position
     * @param paramAction the action to perform when the icon is clicked
     *                    {@link ClickAction}
     */
    public ActionItem(ItemStack paramItem, int paramSlot, ClickAction paramAction, ActionHandler actionHandler) {

        super(paramItem, paramSlot, paramAction, actionHandler);
        this.setActionHandler(actionHandler);

    }

    /**
     * Instantiates a new Abstract item.
     *
     * @param paramItem        the {@link ItemStack} used as the icon
     * @param paramSlot        the slot inventory position
     * @param paramAction      the action to perform when the icon is clicked
     *                         {@link ClickAction}
     * @param paramDisplayName the icon display name
     */
    public ActionItem(ItemStack paramItem, int paramSlot, ClickAction paramAction, String paramDisplayName) {

        super(paramItem, paramSlot, paramAction, paramDisplayName);
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
    public ActionItem(ItemStack paramItem, int paramSlot, ClickAction paramAction, String paramDisplayName, List<String> paramLores) {

        super(paramItem, paramSlot, paramAction, paramDisplayName, paramLores);

    }

}
