package com.relicum.pvpcore.Menus;

import org.bukkit.entity.Player;

/**
 * Name: ActionHandler.java Created: 14 April 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public interface ActionHandler<T extends ActionHandler> {

    T getExecutor();

    ActionResponse perform(Player player, AbstractItem<T> icon);
}
