package com.relicum.duel.Commands;

import com.google.common.collect.ImmutableList;
import com.relicum.commands.Annotations.Command;
import com.relicum.duel.Duel;
import com.relicum.pvpcore.Arenas.DuplicateZoneException;
import com.relicum.pvpcore.Arenas.InvalidZoneException;
import com.relicum.pvpcore.Arenas.PvPZone;
import com.relicum.pvpcore.Enums.ArenaType;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.bukkit.command.CommandSender;
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
@SuppressFBWarnings({ "CLI_CONSTANT_LIST_INDEX" })
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

    public final void loadNames() {

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

        if (!OPTIONS.contains(args[0])) {
            sendErrorMessage("Invalid argument, try using tab complete");
            return true;
        }
        String str = args[1];
        if ("collection".equals(str)) {
            String str2 = args[1];
            for (String z : zones.keySet()) {
                if (z.equals(str2)) {
                    sendErrorMessage("Error: the name " + str + " is already registered");
                    return true;
                }
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

            zones.put(args[1], zones.get(args[1]) + 1);
            saveNames();
            sendMessage("Successfully registered a new zone under collection " + args[1]);
            return true;
        }

        return false;
    }

    @Override
    public List<String> tabComp(int i, String[] args) {

        if (i == 2) {
            return OPTIONS;
        }
        if (i == 3) {

            return zones.keySet().stream().collect(Collectors.toList());
        }

        return Collections.emptyList();
    }
}
