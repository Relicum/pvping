package com.relicum.pvpcore.Menus;

import org.bukkit.inventory.ItemStack;
import java.util.List;

/**
 * The interface Base item.
 */
public interface BaseItem {

    /**
     * Sets display name.
     *
     * @param paramName the param name
     */
    void setText(String paramName);

    /**
     * Gets display name.
     *
     * @return the display name
     */
    String getText();

    /**
     * Sets lore.
     *
     * @param paramLore the param lore
     */
    void setDescription(List<String> paramLore);

    /**
     * Gets lore.
     *
     * @return the lore
     */
    List<String> getDescription();

    /**
     * Sets icon {@link ItemStack}.
     *
     * @param paramIcon the param icon
     */
    void setIcon(ItemStack paramIcon);

    /**
     * Gets item.
     *
     * @return the item
     */
    ItemStack getIcon();

    /**
     * Sets slot index.
     *
     * @param paramIndex the param slot
     */
    void setIndex(int paramIndex);

    /**
     * Gets slot index.
     *
     * @return the slot index
     */
    int getIndex();

}
