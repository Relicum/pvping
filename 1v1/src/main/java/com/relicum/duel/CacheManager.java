package com.relicum.duel;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.relicum.duel.Objects.GameInvite;
import com.relicum.pvpcore.Menus.ActionMenu;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Name: CacheManager.java Created: 25 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class CacheManager {

    private Cache<String, ActionMenu> menuCache;

    private Cache<UUID, GameInvite> inviteCache;

    private RemovalListener<String, ActionMenu> listener;


    public CacheManager() {

        listener = notification -> {
            if (notification != null) {


                String key = notification.getKey();

                System.out.println("Menu Removed from cache " + key);
            }
        };
        menuCache = CacheBuilder.newBuilder().expireAfterAccess(30, TimeUnit.SECONDS).removalListener(listener).maximumSize(100).weakValues().build();

    }

    /**
     * Associates {@code value} with {@code key} in this cache. If the cache previously contained a
     * value associated with {@code key}, the old value is replaced by {@code value}.
     * <p>
     * <p>Prefer {@link #get(Object, Callable)} when using the conventional "if cached, return;
     * otherwise create, cache and return" pattern.
     */
    public void putMenu(String key, ActionMenu menu) {

        menuCache.put(key, menu);
    }

    /**
     * Returns the value associated with {@code key} in this cache, or {@code null} if there is no
     * cached value for {@code key}.
     */
    @Nullable
    public ActionMenu getMenu(String key) {

        return menuCache.getIfPresent(key);

    }

    /**
     * Discards any cached value for key {@code key}.
     */
    public void invalidateMenu(String key) {

        menuCache.invalidate(key);
    }

    /**
     * Discards all entries in the cache.
     */
    public void invalidateAll() {

        menuCache.invalidateAll();
    }

    /**
     * Performs any pending maintenance operations needed by the cache. Exactly which activities are
     * performed -- if any -- is implementation-dependent.
     */
    public void cleanUp() {

        menuCache.cleanUp();
    }
}
