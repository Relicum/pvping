package com.relicum.duel.Menus;

import com.relicum.duel.Duel;
import com.relicum.locations.SpawnPoint;
import com.relicum.pvpcore.Arenas.PvPZone;
import com.relicum.pvpcore.Enums.ArenaType;
import com.relicum.pvpcore.Menus.AbstractItem;
import com.relicum.pvpcore.Menus.ActionHandler;
import com.relicum.pvpcore.Menus.ActionResponse;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Name: SpawnSetHandler.java Created: 06 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class SpawnSetHandler implements ActionHandler {

    @Override
    public SpawnSetHandler getExecutor() {

        return this;
    }

    @Override
    public ActionResponse perform(Player player, AbstractItem icon) {

        ZoneEditMenu am = (ZoneEditMenu) icon.getMenu();
        PvPZone zone = am.getZone();
        Spawns1v1 sp = Spawns1v1.fromTitle(ChatColor.stripColor(icon.getText()));
        if (zone.getArenaType().equals(ArenaType.ARENA1v1))
        {
            sp = Spawns1v1.fromTitle(ChatColor.stripColor(icon.getText()));

            if (sp == (Spawns1v1.PLAYER_ONE))
            {
                am.getZone().addSpawn(Spawns1v1.PLAYER_ONE.getName(), new SpawnPoint(player.getLocation()));
            }
            if (sp == Spawns1v1.PLAYER_TWO)
            {
                am.getZone().addSpawn(Spawns1v1.PLAYER_TWO.getName(), new SpawnPoint(player.getLocation()));
            }
        }

        SpawnPointItem item = (SpawnPointItem) icon;

        if (!item.isState())
        {

            am.removeMenuItem(icon.getIndex());
            am.addMenuItem(item, item.getIndex());
            // player.getOpenInventory().getTopInventory().setItem(item.getIndex(),
            // am.getBooleanStack(true, sp));
            am.getInventory().setItem(item.getIndex(), am.getBooleanStack(true, sp));
            item.setState(true);

            player.sendMessage("You have successfully set " + item.getText());
        }
        else
        {
            player.sendMessage("You have successfully updated " + item.getText());
        }

        Duel.get().getZoneManager().saveZone(am.getZone());

        ActionResponse response = new ActionResponse(item);
        response.setPlayer(player);
        response.setWillUpdate(true);
        // player.updateInventory();

        return response;
    }
}
