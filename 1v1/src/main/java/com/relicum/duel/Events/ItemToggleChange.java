package com.relicum.duel.Events;

import com.relicum.pvpcore.Menus.AbstractItem;
import com.relicum.pvpcore.Menus.ActionMenu;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * ItemToggleChange
 *
 * @author Relicum
 * @version 0.0.1
 */
public class ItemToggleChange extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final ActionMenu menu;
    private final int slot;
    private final AbstractItem.ToggleState toggleState;
    private boolean cancelled = false;


    public ItemToggleChange(ActionMenu menu, int slot, AbstractItem.ToggleState newState) {

        this.menu = menu;
        this.slot = slot;
        this.toggleState = newState;

    }

    public static HandlerList getHandlerList() {

        return handlers;

    }

    public ActionMenu getMenu() {
        return menu;
    }

    public int getSlot() {
        return slot;
    }

    public AbstractItem.ToggleState getToggleState() {
        return toggleState;
    }

    @Override
    public HandlerList getHandlers() {

        return handlers;

    }

    @Override
    public boolean isCancelled() {

        return cancelled;

    }

    @Override
    public void setCancelled(boolean cancelled) {

        this.cancelled = cancelled;

    }
}
