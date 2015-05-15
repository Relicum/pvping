package com.relicum.duel.Commands;

import com.google.common.collect.ImmutableList;
import com.relicum.commands.Annotations.Command;
import com.relicum.duel.Duel;
import com.relicum.duel.Menus.SpawnHotBar;
import com.relicum.pvpcore.Menus.Spawns1v1;
import com.relicum.locations.SpawnPoint;
import com.relicum.pvpcore.Arenas.PvPZone;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ZoneModify
 *
 * @author Relicum
 * @version 0.0.1
 */
@Command(aliases = {"modify"}, desc = "Modify and edit Zone settings", perm = "duel.admin.modify", usage = "/noxarena modify", isSub = true, parent =
                                                                                                                                                     "noxarena",
                useTab = true, min = 1)
public class ZoneModify extends DuelCmd {

    private List<String> OPTIONS = ImmutableList.of("openmenu", "zone", "set");
    private List<String> OPTIONS5;
    private List<String> OPTIONS6;

    /**
     * Instantiates a new AbstractCommand
     *
     * @param plugin the plugin
     */
    public ZoneModify(Duel plugin) {

        super(plugin);
        OPTIONS5 = ImmutableList.of("setspawn", "set", "enable", "disable", "remove");
        OPTIONS6 = new ArrayList<>();
        for (Spawns1v1 sp : Spawns1v1.values()) {
            OPTIONS6.add(sp.getName());
        }

    }

    @Override
    public List<String> tabComp(int i, String[] args) {

        if (i == 2) {
            return OPTIONS;
        }
        if (i == 3) {
            return plugin.getZoneManager().getCollectionNames();
        }
        if (i == 4) {
            return plugin.getZoneManager().getZoneNames(args[2]);
        }
        if (i == 5) {
            return OPTIONS5;
        }
        if (i == 6) {
            return OPTIONS6;
        }

        return Collections.emptyList();
    }

    @Override
    public boolean onCommand(CommandSender sender, String s, String[] args) {

        if (!OPTIONS.contains(args[0])) {
            sendErrorMessage("Invalid argument, try using tab complete");
            return true;
        }

        String str = args[0];

        if ("openmenu".equals(str)) {
            if (args.length == 1) {
                sendMessage("Trying to open Zone Main Menu");
                plugin.getMenuManager().createZoneEdit().openMenu((Player) sender);
                return true;
            }
            else if (args.length == 2) {
                if (!plugin.getZoneManager().containsCollection(args[1])) {
                    sendErrorMessage("Error: invalid collection name " + args[1]);
                    return true;
                }
                sendMessage("Opening Zone Select Menu for " + args[1]);
                plugin.getMenuManager().createSelectMenu(args[1]).openMenu((Player) sender);
                return true;
            }
            else if (args.length == 3) {
                if (!plugin.getZoneManager().containsCollection(args[1])) {
                    sendErrorMessage("Error: invalid collection name " + args[1]);
                    return true;
                }

                if (!plugin.getZoneManager().containsZone(args[1], args[2])) {
                    sendErrorMessage("Error: invalid zone name " + args[2]);
                    return true;
                }
                sendMessage("Opening Zone Edit Menu for " + args[2]);
                plugin.getMenuManager().getZoneEditMenu(args[1], args[2]).openMenu((Player) sender);
                return true;

            }
        }

        if ("set".equals(str)) {
            if (args.length == 3) {
                if (!plugin.getZoneManager().containsCollection(args[1])) {
                    sendErrorMessage("Error: invalid collection name " + args[1]);
                    return true;
                }

                if (!plugin.getZoneManager().containsZone(args[1], args[2])) {
                    sendErrorMessage("Error: invalid zone name " + args[2]);
                    return true;
                }

                PvPZone pvPZone = plugin.getZoneManager().getPvpZone(args[1], args[2]);

                Player player = (Player) sender;

                SpawnHotBar bar = new SpawnHotBar(pvPZone, player, plugin);

                // player.getInventory().setItem(0,bar.getItem(Slot.ZERO));
                // player.getInventory().setItem(1,bar.getItem(Slot.ONE));
                bar.saveInventory();
                player.getInventory().setContents(bar.getItems());
                player.updateInventory();
                sendMessage("Items have been placed in your hot bar");

                return true;

            }
        }

        if ("zone".equals(str)) {
            if (args.length == 5) {
                if (!plugin.getZoneManager().containsCollection(args[1])) {
                    sendErrorMessage("Error: invalid collection name " + args[1]);
                    return true;
                }

                if (!plugin.getZoneManager().containsZone(args[1], args[2])) {
                    sendErrorMessage("Error: invalid zone name " + args[2]);
                    return true;
                }

                if (!OPTIONS5.contains(args[3])) {
                    sendErrorMessage("Error: invalid option " + args[3]);
                    return true;
                }
                if (args[3].equals("setspawn")) {
                    if (!OPTIONS6.contains(args[4])) {
                        sendErrorMessage("Error: invalid spawn type " + args[4]);
                        return true;
                    }

                    PvPZone pvPZone = plugin.getZoneManager().getPvpZone(args[1], args[2]);

                    Spawns1v1 spawns1v1 = Spawns1v1.fromName(args[4]);
                    Player player = (Player) sender;
                    if (spawns1v1 == Spawns1v1.PLAYER_ONE || spawns1v1 == Spawns1v1.PLAYER_TWO) {
                        pvPZone.addSpawn(spawns1v1.getName(), new SpawnPoint(player.getLocation()));
                    }
                    else if (spawns1v1 == Spawns1v1.SPECTATOR) {
                        pvPZone.setSpecSpawn(new SpawnPoint(player.getLocation()));
                    }
                    else if (spawns1v1 == Spawns1v1.END) {
                        pvPZone.setEndSpawn(new SpawnPoint(player.getLocation()));
                    }
                    else {
                        sendErrorMessage("Error unknown spawn type");
                        return true;
                    }

                    plugin.getZoneManager().saveZone(pvPZone);

                    sendMessage("Spawn successfully created for "
                                        + args[4] + " for collection " + args[1] + " zone " + args[2]
                    );
                    return true;
                }

            }

            sendErrorMessage("Error invalid number of arguments");
            return true;
        }

        return true;
    }
}
