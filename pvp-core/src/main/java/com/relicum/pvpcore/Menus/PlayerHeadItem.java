package com.relicum.pvpcore.Menus;

import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * Name: PlayerHeadItem.java Created: 24 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class PlayerHeadItem extends AbstractItem {


    /**
     * Instantiates a new PlayerHeadItem item.
     *
     * @param paramSlot      the slot inventory position
     * @param paramAction    the action to perform when the icon is clicked
     *                       {@link ClickAction}
     * @param paramSkullMeta instance of {@link SkullMeta} that has its values preset
     */
    public PlayerHeadItem(int paramSlot, ClickAction paramAction, SkullMeta paramSkullMeta) {
        super();
        setSkullMeta(paramSkullMeta);

        setIcon(new ItemStack(Material.SKULL_ITEM, 1, (short) 3));
        super.setIndex(paramSlot);
        super.setAction(paramAction);

    }

    /**
     * Instantiates a new PlayerHeadItem item.
     *
     * @param paramSlot      the slot inventory position
     * @param paramAction    the action to perform when the icon is clicked
     *                       {@link ClickAction}
     * @param actionHandler
     * @param paramSkullMeta instance of {@link SkullMeta} that has its values preset
     */
    public PlayerHeadItem(int paramSlot, ClickAction paramAction, ActionHandler actionHandler, SkullMeta paramSkullMeta) {
        super();
        setSkullMeta(paramSkullMeta);

        setIcon(new ItemStack(Material.SKULL_ITEM, 1, (short) 3));
        super.setIndex(paramSlot);
        super.setAction(paramAction);
        super.setActionHandler(actionHandler);

    }


    /**
     * {@inheritDoc}
     */
    @Override
    public ItemStack getItem() {

        return item.clone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIcon(ItemStack paramStack) {

        Validate.notNull(paramStack);
        Validate.isTrue(paramStack.getType().equals(Material.SKULL_ITEM), "Item is not of type Skull_Item");
        paramStack.setItemMeta(getSkullMeta());
        this.item = paramStack;
    }


    /**
     * Gets the {@link SkullMeta} that will be applied to the item.
     *
     * @return {@link SkullMeta}
     */
    @Override
    public SkullMeta getSkullMeta() {

        return super.skullMeta;

    }

    /**
     * Sets the {@link SkullMeta} for the item.
     *
     * @param skullMeta {@link SkullMeta}.
     */
    @Override
    public void setSkullMeta(SkullMeta skullMeta) {

        Validate.notNull(skullMeta);
        setText(skullMeta.getDisplayName());
        setDescription(skullMeta.getLore());
        super.skullMeta = skullMeta;
    }
}
