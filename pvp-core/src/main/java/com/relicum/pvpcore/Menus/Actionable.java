package com.relicum.pvpcore.Menus;

/**
 * Actionable marks if a menu item performs an action {@link ClickAction} when
 * clicked.
 *
 * @author Relicum
 * @version 0.0.1
 */
public interface Actionable {

    /**
     * Used to mark a menu item having a {@link ClickAction} or not.
     *
     * @return true and it has a {@link ClickAction}, false and no action is
     * performed on clicking the menu item.
     */
    boolean hasAction();

    /**
     * Sets the type of action to perform when the item is clicked.
     *
     * @param paramAction the type of item {@link ClickAction}
     */
    void setAction(ClickAction paramAction);

    /**
     * Gets the type of action that is performed when the item is clicked.
     *
     * @return the action {@link ClickAction}
     */
    ClickAction getAction();

    /**
     * Gets {@link ActionHandler} {@link Class} that performs the action.
     *
     * @return the action handler class {@link ActionHandler}
     */
    Class getActionHandlerClass();
}
