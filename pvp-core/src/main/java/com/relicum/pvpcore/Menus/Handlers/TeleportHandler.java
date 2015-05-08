package com.relicum.pvpcore.Menus.Handlers;

import com.relicum.pvpcore.Menus.ActionHandler;
import org.bukkit.Location;

/**
 * Name: TeleportHandler.java Created: 04 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public abstract class TeleportHandler implements ActionHandler {

    private Location location;

    public TeleportHandler(Location location) {

        this.location = location;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TeleportHandler getExecutor() {

        return this;
    }

    public Location getLocation() {

        return location;
    }

    public void setLocation(Location location) {

        this.location = location;
    }
}
