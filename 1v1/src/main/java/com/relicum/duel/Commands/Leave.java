package com.relicum.duel.Commands;

import com.relicum.commands.Annotations.Command;
import com.relicum.duel.Duel;
import com.relicum.pvpcore.Enums.EndReason;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

/**
 * Name: Leave.java Created: 17 April 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
@Command(aliases = {"leave"}, desc = "Leave the 1v1 arena", perm = "duel.player.leave", parent = "1v1", usage = "/1v1 leave", isSub = true)
public class Leave extends DuelCmd {

    /**
     * Instantiates a new AbstractCommand
     *
     * @param plugin the plugin
     */
    public Leave(Duel plugin) {

        super(plugin);
    }

    /**
     * Tab comp. Override this if you want to use tab Complete.
     * <p>
     * The first argument you can complete is when length is set to 2 as 1 is
     * the sub command which will auto complete it for you.
     *
     * @param length  the current command argument position
     * @param strings list of current args
     * @return the list of available options for Tab complete.
     */
    @Override
    public List<String> tabComp(int i, String[] strings) {

        return Collections.emptyList();
    }

    /**
     * Runs the command
     *
     * @param sender the sender
     * @param label  the label
     * @param args   the args
     * @return the boolean
     */
    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {

        if (!plugin.getGameHandler().removeAndDestroy((Player) sender, EndReason.LEAVE_CMD)) {

            sendErrorMessage("Error: removing you from 1v1");
            return true;

        }
        else {

            sendMessage("You have left the Noxious 1V1's");

        }

        return true;

    }

}
