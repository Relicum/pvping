package com.relicum.duel.Commands;

import com.google.common.collect.ImmutableList;
import com.relicum.commands.Annotations.Command;
import com.relicum.duel.Duel;
import com.relicum.pvpcore.Arenas.DuplicateZoneException;
import com.relicum.pvpcore.Arenas.InvalidZoneException;
import com.relicum.pvpcore.Arenas.PvPZone;
import com.relicum.pvpcore.Enums.ArenaType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.Collections;
import java.util.List;

/**
 * Name: ZoneCreator.java Created: 01 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
@Command(aliases = { "create" }, desc = "Create new Zones and Zone Collections", perm = "duel.admin.create", usage = "/noxarena create", isSub = true, parent = "noxarena", min = 2, useTab = true)
public class ZoneCreator extends DuelCmd {

    private List<String> OPTIONS = ImmutableList.of("collection", "zone");

    /**
     * Instantiates a new AbstractCommand
     *
     * @param plugin the plugin
     */
    public ZoneCreator(Duel plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, String s, String[] args) {

        Player player = (Player) sender;

        if (!OPTIONS.contains(args[0])) {
            sendErrorMessage("Invalid argument, try using tab complete");
            return true;
        }

        if (args[0].equalsIgnoreCase("collection")) {
            try {
                plugin.getZoneManager().registerCollection(args[1]);
                sendMessage("Successfully registered new ZoneCollection &6" + args[1]);
                return true;
            } catch (DuplicateZoneException e) {
                sendErrorMessage(e.getMessage());
                e.printStackTrace();
                return true;
            }
        }

        if (args[0].equalsIgnoreCase("zone")) {

            try {
                plugin.getZoneManager().registerZone(args[1], new PvPZone(ArenaType.ARENA1v1, args[1]));
            } catch (InvalidZoneException e) {
                sendErrorMessage("Error: " + e.getMessage());
                e.printStackTrace();
                return true;
            }
            sendMessage("Successfully registered a new zone under collection " + args[1]);
            return true;
        }

        return false;
    }

    @Override
    public List<String> tabComp(int i) {

        if (i == 2) {
            return OPTIONS;
        }

        return Collections.emptyList();
    }
}
