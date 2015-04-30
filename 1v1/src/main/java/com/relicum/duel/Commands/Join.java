package com.relicum.duel.Commands;

import com.relicum.commands.Annotations.Command;
import com.relicum.duel.Duel;
import com.relicum.duel.Objects.PvpPlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Name: Join.java Created: 28 April 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
@Command(aliases = { "join" }, desc = "Used to join the 1v1 arena", perm = "duel.player.join", usage = "/1v1 join", isSub = true, parent = "1v1")
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
    public boolean onCommand(CommandSender commandSender, String s, String[] strings) {

        Player player = (Player) sender;

        Collection<PotionEffect> effects = player.getActivePotionEffects();

        if (effects.size() == 0)
            sendMessage("The player has no Potion effects");

        else {
            for (PotionEffect effect : effects) {
                sendMessage("Name: " + effect.getType().getName());
                sendMessage("Duration: " + effect.getDuration());
                sendMessage("Amplifier: " + effect.getAmplifier());
                sendMessage("Ambient: " + effect.isAmbient());
                sendMessage("HasParticles: " + effect.hasParticles());
                sendMessage(" ");

            }

        }

        if (plugin.player == null) {
            plugin.player = new PvpPlayer(player, plugin);
            sendMessage("You have joined");
            return true;
        }
        if (plugin.player.getUuid().equals(player.getUniqueId())) {
            sendErrorMessage("You are already joined");
            return true;
        } else {
            sendMessage("Sorry its currently full");
            return true;
        }

    }

    @Override
    public List<String> tabComp(int i) {
        return Collections.emptyList();
    }
}
