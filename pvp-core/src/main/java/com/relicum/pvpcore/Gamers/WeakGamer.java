package com.relicum.pvpcore.Gamers;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.lang.ref.WeakReference;
import java.util.UUID;

/**
 * WeakGamer is an abstract class that stores and referencing the {@link Player}
 * using a {@link WeakReference}
 * <p>
 * Extend this class and add all other game related fields and properties.
 * <p>
 * This class already deals with the creation of and handling of the
 * {@link WeakReference}
 *
 * @author Relicum
 * @version 0.0.1
 */
public abstract class WeakGamer<T extends JavaPlugin> {

    protected T plugin;
    protected WeakReference<Player> player;
    protected UUID uuid;

    /**
     * Instantiates a new WeakGamer.
     *
     * @param paramPlayer the param player
     * @param paramPlugin the param plugin
     */
    public WeakGamer(Player paramPlayer, T paramPlugin) {

        setPlayer(paramPlayer);
        this.plugin = paramPlugin;

    }

    /**
     * Set the players {@link UUID} and create a new {@link WeakReference} for
     * the player.
     *
     * @param paramPlayer the {@link Player}
     */
    protected void setPlayer(Player paramPlayer) {

        this.uuid = paramPlayer.getUniqueId();
        this.player = new WeakReference<>(paramPlayer);
    }

    /**
     * Attempts to get the referenced player, if the player has gone offline or
     * the player has been GC'd this will return null.
     * <p>
     * If the reference is null it will first attempt to re create the reference
     * but if the player is not on line it will still return null.
     *
     * @return the referenced {@link Player} object.
     */
    public Player getPlayer() {

        if (player.get() == null)
            return lookupPlayer();
        else
            return player.get();

    }

    /**
     * Tries to re create the {@link WeakReference} player object, if it fails
     * it will return null.
     *
     * @return the referenced {@link Player} object.
     */
    protected Player lookupPlayer() {

        if (uuid == null)
            return null;

        Player pl = null;

        try {
            pl = getPlugin().getServer().getPlayer(uuid);
        } catch (Exception ignored) {
        }

        if (pl == null)
            return null;

        else {
            setPlayer(pl);
            return pl;
        }

    }

    /**
     * Clears the {@link WeakReference} to the player object.
     * <p>
     * Important that this method is called when finished with this object.
     */
    public void clearRef() {

        player.clear();
        uuid = null;
    }

    /**
     * Gets plugin.
     *
     * @return the plugin
     */
    public T getPlugin() {

        return plugin;
    }

    /**
     * Gets the {@link Player} {@link UUID}.
     *
     * @return the players uuid
     */
    public UUID getUuid() {

        return uuid;
    }

}
