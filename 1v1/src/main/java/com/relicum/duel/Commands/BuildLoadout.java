package com.relicum.duel.Commands;

import com.google.common.collect.ImmutableList;
import com.relicum.commands.Annotations.Command;
import com.relicum.duel.Duel;
import com.relicum.pvpcore.Kits.LoadOut;
import com.relicum.pvpcore.Menus.AbstractItem;
import com.relicum.pvpcore.Menus.ActionHandler;
import com.relicum.pvpcore.Menus.ActionResponse;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * BuildLoadOut
 *
 * @author Relicum
 * @version 0.0.1
 */
@Command(aliases = {"createkit"}, desc = "Make game kits, set a name and it clones the items you currently have", perm = "duel.admin.createkit",
                usage = "/noxarena createkit", isSub = true, parent = "noxarena", min = 1, useTab = true)
public class BuildLoadOut extends DuelCmd {

    private List<String> OPTIONS = ImmutableList.of("apply", "rename", "setperm", "setlore", "setdisplay", "seticon", "view", "delete");

    private List<String> kitNames = new ArrayList<>();

    /**
     * Instantiates a new AbstractCommand
     *
     * @param plugin the plugin
     */
    public BuildLoadOut(Duel plugin) {

        super(plugin);
        updateKitNames();

    }

    public List<String> getKitNames() {
        return kitNames;
    }

    public void updateKitNames() {

        kitNames.clear();
        kitNames.addAll(plugin.getKitHandler().getKitNames());
    }

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {

        Player player = (Player) sender;

        if (args[0].equalsIgnoreCase("apply") && args.length == 2) {
            if (!kitNames.contains(args[1])) {
                sendErrorMessage("Unknown Kit: " + args[1]);
                return true;
            }
            LoadOut kit = plugin.getKitHandler().getKit(ChatColor.stripColor(args[1]).replaceAll(" ", ""));
            if (kit != null) {
                player.getInventory().clear();
                player.getInventory().setArmorContents(new ItemStack[4]);

                player.getInventory().setContents(kit.getContents());
                player.getInventory().setArmorContents(kit.getArmor());
                player.addPotionEffects(kit.getEffects());
                kit.applySettings(player);


                new BukkitRunnable() {
                    @Override
                    public void run() {

                        player.updateInventory();
                    }
                }.runTask(plugin);

                sendMessage("Kit " + args[1] + " applied");
                sendMessage("Play Game Settings applied");
            }
            return true;

        }
        else if (args[0].equalsIgnoreCase("rename") && args.length == 3) {
            if (!kitNames.contains(args[1])) {
                sendErrorMessage("Unknown Kit: " + args[1]);
                return true;
            }
            LoadOut data = plugin.getKitHandler().deleteLoadOutFile(args[1]);

            assert data != null;
            data.setLoadoutName(args[2]);
            plugin.getKitHandler().addKit(data);
            updateKitNames();

            sendMessage("Successfully rename kit to &6" + args[2]);

            return true;
        }

        else if (args[0].equalsIgnoreCase("view") && args.length == 2) {
            if (!kitNames.contains(args[1])) {
                sendErrorMessage("Unknown Kit: " + args[1]);
                return true;
            }
            LoadOut data = plugin.getKitHandler().getKit(args[1]);

            if (data != null) {
                plugin.getMenuManager().getKitViewer(data.getDisplayName(), data.getInventory()).openMenu(player);
            }
            else {

                sendErrorMessage("Unable to find Kit");
                return true;
            }


            new BukkitRunnable() {
                @Override
                public void run() {

                    player.getOpenInventory().close();
                }
            }.runTaskLater(plugin, 200l);


            //sendMessage("Successfully rename kit to &6" + args[2]);

            return true;
        }

        else if (args[0].equalsIgnoreCase("delete") && args.length == 2) {
            if (!kitNames.contains(args[1])) {
                sendErrorMessage("Unknown Kit: " + args[1]);
                return true;
            }


            plugin.getMenuManager().getConfirmMenu(new ActionHandler() {
                @Override
                public ActionHandler getExecutor() {
                    return this;
                }

                @Override
                public ActionResponse perform(Player player, AbstractItem icon, InventoryClickEvent event) {
                    event.setCancelled(true);


                    if (icon.getIndex() < 4) {

                        // plugin.getKitHandler().deleteLoadOutFile(args[1]);

                        sendMessage("Kit: " + args[1] + " has been deleted");
                    }
                    else if (icon.getIndex() > 4 && icon.getIndex() < 9)

                    {
                        sendAdminMessage("Kit deletion has been cancelled");
                    }


                    ActionResponse response = new ActionResponse(icon, player);
                    response.setWillClose(true);
                    return response;
                }


            }).openMenu(player);

            //plugin.getMenuManager().getConfirmMenu().openMenuForEditing(player);


            // sendMessage("Successfully deleted kit to &6" + args[1]);

            return true;
        }

        else if (args[0].equalsIgnoreCase("seticon") && args.length == 2) {
            if (!kitNames.contains(args[1])) {
                sendErrorMessage("Unknown Kit: " + args[1]);
                return true;
            }

            ItemStack stack = player.getItemInHand().clone();

            LoadOut data = plugin.getKitHandler().deleteLoadOutFile(args[1]);


            assert data != null;
            data.setMaterial(stack.getType().name());
            plugin.getKitHandler().addKit(data);
            updateKitNames();

            sendMessage("Successfully added menu item for kit to &6" + args[1]);

            return true;
        }

        else if (args[0].equalsIgnoreCase("setperm") && args.length == 3) {
            if (!kitNames.contains(args[1])) {
                sendErrorMessage("Unknown Kit: " + args[1]);
                return true;
            }

            LoadOut data = plugin.getKitHandler().deleteLoadOutFile(args[1]);

            assert data != null;
            data.setPermission("duel.player.kit." + args[2]);
            plugin.getKitHandler().addKit(data);
            updateKitNames();

            sendMessage("Permission updated for &6" + args[1] + "&a to &6" + args[2]);

            return true;
        }

        else if (args[0].equalsIgnoreCase("setdisplay") && args.length == 3) {
            if (!kitNames.contains(args[1])) {
                sendErrorMessage("Unknown Kit: " + args[1]);
                return true;
            }

            LoadOut data = plugin.getKitHandler().deleteLoadOutFile(args[1]);

            assert data != null;
            data.setDisplayName(args[2]);
            plugin.getKitHandler().addKit(data);
            updateKitNames();

            sendMessage("Icon display set for &6" + args[1] + "&a to &6" + args[2]);

            return true;
        }

        else if (args[0].equalsIgnoreCase("setlore") && args.length >= 3) {
            if (!kitNames.contains(args[1])) {
                sendErrorMessage("Unknown Kit: " + args[1]);
                return true;
            }

            LoadOut data = plugin.getKitHandler().deleteLoadOutFile(args[1]);

            String l = "";

            if (args.length == 3) {
                l = args[2];
            }
            else if (args.length > 3) {
                for (int i = 2; i < args.length; i++) {
                    if (i == 2) {
                        l += args[i];
                    }
                    else {
                        l += " " + args[i];
                    }
                }
            }


            assert data != null;
            data.addDescription(l);
            plugin.getKitHandler().addKit(data);
            updateKitNames();

            sendMessage("New Lore added for &6" + args[1]);

            return true;
        }

        String name = "";

        if (args.length == 1) {
            name = args[0];
        }
        else if (args.length > 1) {
            for (int i = 0; i < args.length; i++) {
                if (i == 0) {
                    name += args[i];
                }
                else {
                    name += " " + args[i];
                }
            }
        }


        LoadOut loadOut = new LoadOut(player.getInventory(), player.getActivePotionEffects(), name);

        try {
            plugin.getKitHandler().addKit(loadOut);

            sendMessage("New Kit successfully created called " + name);
            updateKitNames();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            sendErrorMessage("Error: There was a problem adding the kit");
            return true;
        }

    }

    @Override
    public List<String> tabComp(int i, String[] args) {

        if (i == 2) {
            return OPTIONS;
        }
        if (i == 3) {

            return plugin.getKitHandler().getKitNames();
        }

        return Collections.emptyList();
    }
}
