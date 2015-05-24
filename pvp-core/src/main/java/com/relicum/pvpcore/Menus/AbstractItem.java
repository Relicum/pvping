package com.relicum.pvpcore.Menus;

import com.google.common.base.Objects;
import com.relicum.pvpcore.FormatUtil;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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

    private static final long serialVersionUID = 1L;

    protected String displayName;
    protected ActionHandler actionHandler;
    protected List<String> lores = new ArrayList<>();
    protected ItemStack item;
    protected ItemStack itemAlt;
    protected int slot;
    protected String permission;
    protected boolean permRequired;
    protected ActionMenu menu;
    protected boolean modifiable;
    protected ToggleState toggleState;
    protected ClickAction action;

    public AbstractItem() {

    }

    public AbstractItem(ItemStack item) {

        this.item = item;
    }


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
        if (meta.hasDisplayName()) {
            setText(paramItem.getItemMeta().getDisplayName());
        }
        if (meta.hasLore()) {
            setDescription(paramItem.getItemMeta().getLore());
        }

    }


    /**
     * Instantiates a new Abstract item.
     *
     * @param paramItem   the {@link ItemStack} used as the icon
     * @param paramSlot   the slot inventory position
     * @param paramAction the action to perform when the icon is clicked
     *                    {@link ClickAction}
     */
    public AbstractItem(ItemStack paramItem, int paramSlot, ClickAction paramAction) {

        item = paramItem;
        slot = paramSlot;
        action = paramAction;


        ItemMeta meta;
        try {
            meta = item.getItemMeta();
        }
        catch (Exception ignored) {

            meta = Bukkit.getItemFactory().getItemMeta(Material.AIR);
        }

        try {
            if (meta.hasDisplayName()) {
                setText(paramItem.getItemMeta().getDisplayName());
            }
        }
        catch (Exception ignored) {
        }
        try {
            if (meta.hasLore()) {
                setDescription(paramItem.getItemMeta().getLore());
            }
        }
        catch (Exception ignored) {
        }
    }

    /**
     * Instantiates a new Abstract item.
     *
     * @param paramItem   the {@link ItemStack} used as the icon
     * @param paramSlot   the slot inventory position
     * @param paramAction the action to perform when the icon is clicked
     *                    {@link ClickAction}
     */
    public AbstractItem(ItemStack paramItem, int paramSlot, ClickAction paramAction,
                               ActionHandler actionHandler) {

        item = paramItem;
        slot = paramSlot;
        action = paramAction;
        this.actionHandler = actionHandler;
        ItemMeta meta = item.getItemMeta();
        if (meta.hasDisplayName()) {
            setText(paramItem.getItemMeta().getDisplayName());
        }
        if (meta.hasLore()) {
            setDescription(paramItem.getItemMeta().getLore());
        }
    }

    /**
     * Instantiates a new Abstract item.
     *
     * @param paramItem     the {@link ItemStack} used as the icon
     * @param paramItemAlt  the {@link ItemStack} used if the item has toggled to a alternative state
     * @param paramSlot     the slot inventory position
     * @param paramAction   the action to perform when the icon is clicked
     *                      {@link ClickAction}
     * @param actionHandler the {@link ActionHandler} that deals if the item being clicked.
     */
    public AbstractItem(ItemStack paramItem, ItemStack paramItemAlt, int paramSlot, ClickAction paramAction, ActionHandler actionHandler) {

        item = paramItem;
        itemAlt = paramItemAlt;
        slot = paramSlot;
        action = paramAction;
        this.actionHandler = actionHandler;
        ItemMeta meta = item.getItemMeta();
        if (meta.hasDisplayName()) {
            setText(paramItem.getItemMeta().getDisplayName());
        }
        if (meta.hasLore()) {
            setDescription(paramItem.getItemMeta().getLore());
        }
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
    public AbstractItem(ItemStack paramItem, int paramSlot, ClickAction paramAction, String paramDisplayName) {

        item = paramItem;
        slot = paramSlot;
        action = paramAction;
        displayName = paramDisplayName;
        ItemMeta meta = item.getItemMeta();
        if (meta.hasLore()) {
            setDescription(paramItem.getItemMeta().getLore());
        }
    }

    /**
     * Instantiates a new Abstract item.
     *
     * @param paramItem        the {@link ItemStack} used as the icon
     * @param paramItemAlt     the {@link ItemStack} used if the item has toggled to a alternative state
     * @param paramSlot        the slot inventory position
     * @param paramAction      the action to perform when the icon is clicked
     *                         {@link ClickAction}
     * @param paramDisplayName the icon display name
     */
    public AbstractItem(ItemStack paramItem, ItemStack paramItemAlt, int paramSlot, ClickAction paramAction, String paramDisplayName) {

        item = paramItem;
        itemAlt = paramItemAlt;
        slot = paramSlot;
        action = paramAction;
        displayName = paramDisplayName;
        ItemMeta meta = item.getItemMeta();
        if (meta.hasLore()) {
            setDescription(paramItem.getItemMeta().getLore());
        }
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
    public AbstractItem(ItemStack paramItem, int paramSlot, ClickAction paramAction, String paramDisplayName, List<String> paramLores) {

        item = paramItem;
        slot = paramSlot;
        action = paramAction;
        displayName = paramDisplayName;
        lores = paramLores;
    }

    public ToggleState getToggleState() {
        return toggleState;
    }

    public void setToggleState(ToggleState state) {
        this.toggleState = state;


    }

    public boolean updateItemViewState(ToggleState newState) {
        if (newState.equals(ToggleState.ALTERNATIVE)) {

            menu.addMenuItem(this, this.slot);
            menu.getInventory().setItem(slot, itemAlt);
            return true;
        }
        if (newState.equals(ToggleState.DEFAULT)) {
            menu.addMenuItem(this, this.slot);
            menu.getInventory().setItem(slot, item);
            return true;
        }

        return false;
    }

    public ActionHandler getActionHandler() {

        return actionHandler;
    }

    public void setActionHandler(ActionHandler paramActionHandler) {

        actionHandler = paramActionHandler;
    }

    public ActionMenu getMenu() {

        return menu;
    }

    public void addToMenu(ActionMenu menu) {

        this.menu = menu;
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
    public ClickAction getAction() {

        return action;
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
    public Class getActionHandlerClass() {

        return action.getActionClass();
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
    public void setText(String paramName) {

        Validate.notNull(paramName);
        this.displayName = FormatUtil.colorize(paramName);
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
    public void setDescription(List<String> paramLore) {

        Validate.notNull(paramLore);
        this.lores.addAll(paramLore.stream().map(FormatUtil::colorize).collect(Collectors.toList()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemStack getItem() {

        return item;
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
    public int getIndex() {

        return slot;
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
    public boolean hasPermission(Player player) {

        return player.hasPermission(getPermission());
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
    public void setPermission(String name) {

        Validate.notNull(name);
        this.permission = name;
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

        if (this.menu == actionMenu) {

            menu = null;

        }

    }

    /**
     * Gets if this item can be modified.
     *
     * @return true and it can be modified, false and it can not.
     */
    public boolean isModifiable() { return modifiable; }

    /**
     * Sets if the item can be modified.
     *
     * @param modifiable set true and it can be modified, false and it can not.
     */
    public void setModifiable(boolean modifiable) { this.modifiable = modifiable; }

    public enum ToggleState {

        DEFAULT,
        ALTERNATIVE
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                       .add("action", action)
                       .add("actionHandler", actionHandler)
                       .add("displayName", displayName)
                       .add("item", item)
                       .add("lores", lores.toString())
                       .add("modifiable", modifiable)
                       .add("permission", permission)
                       .add("permRequired", permRequired)
                       .add("slot", slot)
                       .add("toggleState", toggleState)
                       .toString();
    }
}
