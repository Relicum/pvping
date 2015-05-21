package com.relicum.duel.Commands;

import com.massivecraft.massivecore.adapter.relicum.RankArmor;
import com.relicum.commands.Annotations.Command;
import com.relicum.duel.Duel;
import com.relicum.duel.Events.PlayerJoinLobbyEvent;
import com.relicum.locations.SpawnPoint;
import com.relicum.pvpcore.Enums.JoinCause;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

/**
 * Name: Join.java Created: 28 April 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
@Command(aliases = {"join"}, desc = "Used to join the 1v1 arena", perm = "duel.player.join", usage = "/1v1 join", isSub = true, parent = "1v1")
public class Join extends DuelCmd {

    /**
     * Instantiates a new AbstractCommand
     *
     * @param plugin the plugin
     */
    public Join(Duel plugin) {

        super(plugin);
    }


    @Override
    public boolean onCommand(CommandSender sender, String s, String[] args) {


        PlayerJoinLobbyEvent event = new PlayerJoinLobbyEvent((Player) sender, new SpawnPoint(((Player) sender).getLocation()), RankArmor.DEV, JoinCause
                                                                                                                                                       .COMMAND);
        plugin.getServer().getPluginManager().callEvent(event);

        return true;

    }

    @Override
    public List<String> tabComp(int i, String[] strings) {

        return Collections.emptyList();
    }
}
