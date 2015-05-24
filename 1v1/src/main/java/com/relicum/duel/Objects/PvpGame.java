package com.relicum.duel.Objects;

import com.relicum.duel.Duel;
import com.relicum.pvpcore.Arenas.PvPZone;
import com.relicum.pvpcore.Game.Game;
import com.relicum.pvpcore.Kits.LoadOut;

/**
 * Name: PvpGame.java Created: 24 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class PvpGame extends Game<Duel> {

    public PvpGame(Duel plugin, PvPZone pZone, LoadOut loadOut) {
        super(plugin, pZone, loadOut);
    }
}
