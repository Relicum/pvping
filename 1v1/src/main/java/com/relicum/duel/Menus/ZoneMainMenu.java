package com.relicum.duel.Menus;

import com.relicum.pvpcore.Menus.ActionMenu;

/**
 * Name: ZoneMainMenu.java Created: 02 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class ZoneMainMenu extends ActionMenu {

    public ZoneMainMenu(String title, int rows) {

        super(title, rows);
    }

    public ZoneMainMenu(String title, int rows, ActionMenu parentMenu) {

        super(title, rows, parentMenu);
    }
}
