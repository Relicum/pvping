package com.relicum.duel.Menus;

import com.relicum.pvpcore.Menus.ActionMenu;

/**
 * Name: PlayerQueueMenu.java Created: 26 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class PlayerQueueMenu extends ActionMenu {


    public PlayerQueueMenu(String title, int rows) {
        super(title, rows);
    }

    public PlayerQueueMenu(String title, int rows, ActionMenu parentMenu) {
        super(title, rows, parentMenu);
    }
}
