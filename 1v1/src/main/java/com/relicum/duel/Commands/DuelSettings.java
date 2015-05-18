package com.relicum.duel.Commands;

import com.relicum.commands.Annotations.Command;
import com.relicum.duel.Duel;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

/**
 * Name: DuelSettings.java Created: 18 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
@Command(aliases = {"settings"}, desc = "Opens menu to set the main plugin settings", perm = "duel.admin.settings", usage = "/noxarena settings", isSub = true, parent = "noxarena")
public class DuelSettings extends DuelCmd {

    /**
     * Instantiates a new AbstractCommand
     *
     * @param plugin the plugin
     */
    public DuelSettings(Duel plugin) {
        super(plugin);
    }


    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {

        if (args.length == 0) {
            sendMessage("Opening Settings Menu");
            plugin.getMenuManager().getDuelSettings().openMenuForEditing((Player) sender);
            return true;
        }

        return true;
    }

    @Override
    public List<String> tabComp(int i, String[] args) {
        return Collections.emptyList();
    }
}
