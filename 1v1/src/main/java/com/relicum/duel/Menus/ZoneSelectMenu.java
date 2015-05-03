package com.relicum.duel.Menus;

import com.relicum.pvpcore.Menus.Menu;

/**
 * Name: ZoneSelectMenu.java Created: 02 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class ZoneSelectMenu extends Menu {

    public ZoneSelectMenu(String title, int rows) {
        super(title, rows);
    }

    public ZoneSelectMenu(String title, int rows, String zoneName) {
        super(title, rows);
    }

    public ZoneSelectMenu(String title, int rows, String zoneName, Menu parentMenu) {
        super(title, rows, parentMenu);
    }
}
