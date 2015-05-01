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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Name: ZoneCreator.java Created: 01 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
@Command(aliases = { "create" }, desc = "Create new Zones and Zone Collections", perm = "duel.admin.create", usage = "/noxarena create", isSub = true, parent = "noxarena", min = 2, useTab = true)
public class ZoneCreator extends DuelCmd {

    private List<String> OPTIONS = ImmutableList.of("collection", "zone");

    private Map<String, Integer> zones;

    /**
     * Instantiates a new AbstractCommand
     *
     * @param plugin the plugin
     */
    public ZoneCreator(Duel plugin) {
        super(plugin);
        loadNames();

    }

    public void loadNames() {

        zones = new HashMap<>();
        for (Map.Entry<String, Object> entry : plugin.getConfig().getConfigurationSection("zone.name").getValues(false).entrySet()) {
            zones.put(entry.getKey(), (Integer) entry.getValue());
        }

    }

    public void saveNames() {
        plugin.getConfig().createSection("zone.name", zones);
        plugin.saveConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, String s, String[] args) {

        Player player = (Player) sender;

        if (!OPTIONS.contains(args[0])) {
            sendErrorMessage("Invalid argument, try using tab complete");
            return true;
        }

        if (args[0].equalsIgnoreCase("collection")) {

            if (zones.containsKey(args[1])) {
                sendErrorMessage("Error: the name " + args[1] + " is already registered");
                return true;
            }

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

            if (!zones.containsKey(args[1])) {
                sendErrorMessage("Error: unable to locate a ZoneCollection called " + args[1]);
                return true;
            }

            try {
                plugin.getZoneManager().registerZone(args[1], new PvPZone(ArenaType.ARENA1v1, args[1], zones.get(args[1])));
            } catch (InvalidZoneException e) {
                sendErrorMessage("Error: " + e.getMessage());
                e.printStackTrace();
                return true;
            }
            Integer ti = zones.get(args[1]);
            ti++;
            zones.put(args[1], ti);
            saveNames();
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
        if (i == 3) {
            return zones.keySet().stream().collect(Collectors.toList());
        }

        return Collections.emptyList();
    }
}