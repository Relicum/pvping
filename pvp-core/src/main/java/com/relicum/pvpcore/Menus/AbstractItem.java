package com.relicum.pvpcore.Menus;

import com.relicum.pvpcore.FormatUtil;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Name: AbstractItem.java Created: 04 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public abstract class AbstractItem implements BaseItem, Permissible, Actionable {

    protected String displayName;
    protected ActionHandler actionHandler;
    protected List<String> lores = new ArrayList<>();
    protected ItemStack item;
    protected int slot;
    protected String permission;
    protected boolean permRequired;
    protected ActionMenu menu;

    protected ClickAction action;

    /**
     * Instantiates a new Abstract item.
     *
     * @param paramItem the {@link ItemStack} used as the icon
     * @param paramSlot the slot inventory position
     */
    public AbstractItem(ItemStack paramItem, int paramSlot) {

        item = paramItem;
        slot = paramSlot;

        ItemMeta meta = item.getItemMeta();
        if (meta.hasDisplayName())
            setText(paramItem.getItemMeta().getDisplayName());
        if (meta.hasLore())
            setDescription(paramItem.getItemMeta().getLore());

    }

    /**
     * Instantiates a new Abstract item.
     *
     * @param paramItem the {@link ItemStack} used as the icon
     * @param paramSlot the slot inventory position
     * @param paramAction the action to perform when the icon is clicked
     * {@link ClickAction}
     */
    public AbstractItem(ItemStack paramItem, int paramSlot, ClickAction paramAction, ActionHandler actionHandler) {

        item = paramItem;
        slot = paramSlot;
        action = paramAction;
        this.actionHandler = actionHandler;
        ItemMeta meta = item.getItemMeta();
        if (meta.hasDisplayName())
            setText(paramItem.getItemMeta().getDisplayName());
        if (meta.hasLore())
            setDescription(paramItem.getItemMeta().getLore());
    }

    /**
     * Instantiates a new Abstract item.
     *
     * @param paramItem the {@link ItemStack} used as the icon
     * @param paramSlot the slot inventory position
     * @param paramAction the action to perform when the icon is clicked
     * {@link ClickAction}
     * @param paramDisplayName the icon display name
     */
    public AbstractItem(ItemStack paramItem, int paramSlot, ClickAction paramAction, String paramDisplayName) {

        item = paramItem;
        slot = paramSlot;
        action = paramAction;
        displayName = paramDisplayName;
        ItemMeta meta = item.getItemMeta();
        if (meta.hasLore())
            setDescription(paramItem.getItemMeta().getLore());
    }

    /**
     * Instantiates a new Abstract item.
     *
     * @param paramItem the {@link ItemStack} used as the icon
     * @param paramSlot the slot inventory position
     * @param paramAction the action to perform when the icon is clicked
     * {@link ClickAction}
     * @param paramDisplayName the icon display name
     * @param paramLores the icon lores
     */
    public AbstractItem(ItemStack paramItem, int paramSlot, ClickAction paramAction, String paramDisplayName, List<String> paramLores) {

        item = paramItem;
        slot = paramSlot;
        action = paramAction;
        displayName = paramDisplayName;
        lores = paramLores;
    }

    public ActionHandler getActionHandler() {

        return actionHandler;
    }

    public ActionMenu getMenu() {

        return menu;
    }

    public void addToMenu(ActionMenu menu) {

        this.menu = menu;
    }

    public void setActionHandler(ActionHandler paramActionHandler) {

        actionHandler = paramActionHandler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasAction() {

        return !action.equals(ClickAction.NO_ACTION);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAction(ClickAction paramAction) {

        Validate.notNull(paramAction);
        action = paramAction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClickAction getAction() {

        return action;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class getActionHandlerClass() {

        return action.getActionClass();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setText(String paramName) {

        Validate.notNull(paramName);
        this.displayName = FormatUtil.colorize(paramName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getText() {

        return displayName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDescription(List<String> paramLore) {

        Validate.notNull(paramLore);
        this.lores.addAll(paramLore.stream().map(FormatUtil::colorize).collect(Collectors.toList()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getDescription() {

        return lores;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIcon(ItemStack paramStack) {

        Validate.notNull(paramStack);
        this.item = paramStack;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemStack getIcon() {

        return item;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIndex(int paramSlot) {

        Validate.notNull(paramSlot);
        Validate.isTrue(paramSlot > 0, "Icon slot must be greater than 1");
        this.slot = paramSlot;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getIndex() {

        return slot;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPermission(Player player) {

        return player.hasPermission(getPermission());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPermission(String name) {

        Validate.notNull(name);
        this.permission = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPermission() {

        return permission;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPermissionSet() {

        return permission != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean required() {

        return permRequired;
    }

    public ActionResponse onClick(Player paramPlayer) {

        getActionHandler().perform(paramPlayer, this);
        return null;
    }

    public void removeFromMenu(ActionMenu actionMenu) {

        if (this.menu == actionMenu)
        {

            menu = null;

        }

    }

}
