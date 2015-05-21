package com.relicum.duel.Commands;

import com.google.common.collect.ImmutableList;
import com.relicum.commands.Annotations.Command;
import com.relicum.duel.Duel;
import com.relicum.pvpcore.Enums.PlayerState;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

/**
 * Name: DebugCmd.java Created: 20 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
@Command(aliases = {"debug"}, desc = "1v1 Developer debug command", perm = "duel.admin.debug",
                usage = "/noxarena debug", isSub = true, parent = "noxarena", min = 1, useTab = true)
public class DebugCmd extends DuelCmd {


    private final List<String> OPTIONS = ImmutableList.of("display");

    private final List<String> OPTIONS2 = ImmutableList.of("state", "inlobby", "players", "queue");

    /**
     * Instantiates a new Debug cmd.
     *
     * @param plugin the plugin
     */
    public DebugCmd(Duel plugin) {
        super(plugin);
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

                    return true;
                }

                return true;
            }
            return true;
        }

        sendMessage("Default Debug Command");
        return true;
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
