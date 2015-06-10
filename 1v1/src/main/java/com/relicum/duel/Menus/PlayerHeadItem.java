package com.relicum.duel.Menus;

import com.relicum.duel.Objects.GameInvite;
import com.relicum.pvpcore.Menus.AbstractItem;
import com.relicum.pvpcore.Menus.ActionHandler;
import com.relicum.pvpcore.Menus.ClickAction;
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

    private String headUuid;
    private GameInvite invite;


    /**
     * Instantiates a new PlayerHeadItem item.
     *
     * @param paramSlot      the slot inventory position
     * @param paramAction    the action to perform when the icon is clicked
     *                       {@link ClickAction}
     * @param paramSkullMeta instance of {@link SkullMeta} that has its values preset
     * @param headId         the {@link java.util.UUID} of the skull owner in string format.
     */
    public PlayerHeadItem(int paramSlot, ClickAction paramAction, SkullMeta paramSkullMeta, String headId) {
        super();
        setSkullMeta(paramSkullMeta);
        this.headUuid = headId;
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
     * @param headId         the {@link java.util.UUID} of the skull owner in string format.
     */
    public PlayerHeadItem(int paramSlot, ClickAction paramAction, ActionHandler actionHandler, SkullMeta paramSkullMeta, String headId) {
        super();
        setSkullMeta(paramSkullMeta);
        this.headUuid = headId;
        setIcon(new ItemStack(Material.SKULL_ITEM, 1, (short) 3));
        super.setIndex(paramSlot);
        super.setAction(paramAction);
        super.setActionHandler(actionHandler);

    }

    /**
     * Gets skull owners {@link java.util.UUID} in string format.
     *
     * @return the string formatted {@link java.util.UUID} of the skull owner.
     */
    public String getHeadUuid() {

        return this.headUuid;
    }

    /**
     * Gets skull owner.
     *
     * @return the skull owner
     */
    public String getSkullOwner() {

        if (super.skullMeta.hasOwner()) {
            return super.skullMeta.getOwner();
        }
        else {
            return "";
        }
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

    /**
     * Gets the {@link GameInvite} where the Inviter is also the skull owner.
     *
     * @return the {@link GameInvite}.
     */
    public GameInvite getInvite() {

        return invite;
    }

    /**
     * Set the {@link GameInvite}.
     *
     * @param invite the {@link GameInvite} where the Inviter is also the skull owner.
     */
    public void setInvite(GameInvite invite) {

        this.invite = invite;
    }
}
