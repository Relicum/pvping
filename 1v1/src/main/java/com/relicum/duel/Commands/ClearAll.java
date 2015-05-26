package com.relicum.duel.Commands;

import com.relicum.commands.Annotations.Command;
import com.relicum.duel.Duel;
import com.relicum.pvpcore.Tasks.UpdateInventory;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Collections;
import java.util.List;

/**
 * Name: ClearAll.java Created: 24 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
@Command(aliases = {"clear"}, desc = "Clear current inventory and effects the player has", perm = "duel.admin.clear", usage = "/noxarena clear", isSub = true, parent = "noxarena")
public class ClearAll extends DuelCmd {


    /**
     * Instantiates a new AbstractCommand
     *
     * @param plugin the plugin
     */
    public ClearAll(Duel plugin) {
        super(plugin);
    }

    @Override
    public List<String> tabComp(int i, String[] args) {
        return Collections.emptyList();
    }

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {

        Player player = (Player) sender;
        player.getInventory().setArmorContents(new ItemStack[4]);
        player.getInventory().clear();

        for (PotionEffect effect : player.getActivePotionEffects()) {

            player.removePotionEffect(effect.getType());
        }

        sendMessage("Inventory clear and effects removed");

        UpdateInventory.now(player, plugin);

        return true;
    }
}
