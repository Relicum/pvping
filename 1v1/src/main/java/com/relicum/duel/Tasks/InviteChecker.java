package com.relicum.duel.Tasks;

import com.relicum.duel.Handlers.InviteCache;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Name: InviteChecker.java Created: 25 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class InviteChecker extends BukkitRunnable {

    private InviteCache handler;

    public InviteChecker(InviteCache handler) {
        this.handler = handler;
    }

    @Override
    public void run() {

        handler.checkInviteValidity();
    }
}
