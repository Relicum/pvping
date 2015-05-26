package com.relicum.duel.Commands;

import com.google.common.collect.ImmutableList;
import com.relicum.commands.Annotations.Command;
import com.relicum.duel.Duel;
import com.relicum.duel.Menus.CloseItem;
import com.relicum.pvpcore.Enums.PlayerState;
import com.relicum.pvpcore.FormatUtil;
import com.relicum.pvpcore.Menus.ActionMenu;
import com.relicum.pvpcore.Menus.ClickAction;
import com.relicum.pvpcore.Menus.PlayerHeadItem;
import com.relicum.utilities.Items.MLore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Name: DebugCmd.java Created: 20 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
@Command(aliases = {"debug"}, desc = "1v1 Developer debug command", perm = "duel.admin.debug",
                usage = "/noxarena debug", isSub = true, parent = "noxarena", min = 1, useTab = true)
public class DebugCmd extends DuelCmd {


    private final List<String> OPTIONS = ImmutableList.of("display", "game");

    private final List<String> OPTIONS2 = ImmutableList.of("state", "inlobby", "players", "queue", "heads");
    ActionMenu menu;
    private Map<String, SkullMeta> heads;

    /**
     * Instantiates a new Debug cmd.
     *
     * @param plugin the plugin
     */
    public DebugCmd(Duel plugin) {
        super(plugin);
        heads = new HashMap<>();
        new BukkitRunnable() {
            @Override
            public void run() {

                heads.put("Relicum", getSkullMeta("Relicum"));
                heads.put("Darryn25", getSkullMeta("Darryn25"));
                heads.put("PVPINGNINJA", getSkullMeta("PVPINGNINJA"));
                logInfoFormatted("Player SkullMetas Added");
            }
        }.runTaskLater(plugin, 60l);
    }


    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!OPTIONS.contains(args[0])) {
            sendErrorMessage("Invalid argument, try using tab complete");
            return true;
        }

        String str = args[0];
        Player player = (Player) sender;
        String uuid = player.getUniqueId().toString();
        if ("display".equalsIgnoreCase(str)) {
            if (args.length > 1 && OPTIONS2.contains(args[1])) {


                if ("state".equalsIgnoreCase(args[1])) {
                    if (!plugin.getGameHandler().isKnown(uuid)) {
                        sendMessage("You are not known to the system so you can't have a state");

                    }
                    else {
                        PlayerState state = plugin.getGameHandler().getPlayerState(uuid);

                        sendMessage("Your current player state is &a" + state.name());
                    }

                    if (plugin.getLobbyHandler().isInLobby(uuid)) {

                        sendMessage("Your currently showing in the lobby set");
                    }

                    else {
                        sendMessage("Your currently NOT showing in the lobby set");
                    }

                    if (player.hasMetadata(Duel.META_KEY)) {

                        sendMessage("Found some meta data");
                        int r = player.getMetadata(Duel.META_KEY).get(0).asInt();

                        sendMessage("The int for the meta data is " + r);
                    }
                    else {

                        sendAdminMessage("Currently no meta data found");
                    }



                    return true;
                }

                if ("heads".equalsIgnoreCase(args[1])) {

                    if (menu == null) {
                        menu = plugin.getMenuManager().getHeadMenu();

                        PlayerHeadItem phi = new PlayerHeadItem(0, ClickAction.NO_ACTION, heads.get("Relicum"));

                        PlayerHeadItem phi1 = new PlayerHeadItem(1, ClickAction.NO_ACTION, heads.get("Darryn25"));
                        PlayerHeadItem phi2 = new PlayerHeadItem(2, ClickAction.NO_ACTION, heads.get("PVPINGNINJA"));


                        menu.addMenuItem(phi, 0);
                        menu.addMenuItem(phi1, 1);
                        menu.addMenuItem(phi2, 2);
                        menu.addMenuItem(new CloseItem(8), 8);


                        menu.openMenu(player);
                    }
                    else {
                        menu.openMenu(player);
                    }
                    return true;
                }

                return true;
            }
            return true;
        }

        sendMessage("Default Debug Command");
        return true;
    }

    private SkullMeta getSkullMeta(String name) {

        MLore mLore = new MLore("  \n")
                              .then("&bThis is the skull of " + name + " beware can be dangerous")
                              .blankLine()
                              .then("&aRight Click to challenge them to a 1v1");

        String display = "&a&l" + name + " Skull";

        SkullMeta m1 = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
        m1.setOwner(name);
        m1.setDisplayName(FormatUtil.colorize(display));
        m1.setLore(mLore.toLore());

        return m1;
    }

    @Override
    public List<String> tabComp(int i, String[] args) {

        if (i == 2) {
            return OPTIONS;
        }

        else if (i == 3) {
            return OPTIONS2;
        }


        return Collections.emptyList();
    }
}
