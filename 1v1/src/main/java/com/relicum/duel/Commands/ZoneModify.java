package com.relicum.duel.Commands;

import com.google.common.collect.ImmutableList;
import com.relicum.commands.Annotations.Command;
import com.relicum.duel.Duel;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.Collections;
import java.util.List;

/**
 * ZoneModify
 *
 * @author Relicum
 * @version 0.0.1
 */
@Command(aliases = { "modify" }, desc = "Modify and edit Zone settings", perm = "duel.admin.modify", usage = "/noxarena modify", isSub = true, parent = "noxarena", useTab = true, min = 1)
public class ZoneModify extends DuelCmd {

    private List<String> OPTIONS = ImmutableList.of("openmenu", "zone", "set");

    /**
     * Instantiates a new AbstractCommand
     *
     * @param plugin the plugin
     */
    public ZoneModify(Duel plugin) {
        super(plugin);
    }

    @Override
    public List<String> tabComp(int i, String[] args) {

        if (i == 2)
            return OPTIONS;

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
            sendMessage("Trying to open Zone Main Menu");
            plugin.getMenuManager().createZoneEdit().openMenu((Player) sender);
            return true;
        }

        return true;
    }
}
