package com.relicum.duel.Commands;

import com.relicum.duel.Duel;
import com.relicum.pvpcore.Annotations.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Name: Hello.java Created: 17 April 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
@Command(aliases = { "hello" }, desc = "Simple test to say hello", perm = "duel.admin.hello", parent = "duel", usage = "/duel hello", isSub = true)
public class Hello extends DuelCmd {

    /**
     * Instantiates a new AbstractCommand
     *
     * @param plugin the plugin
     */
    public Hello(Duel plugin) {

        super(plugin);
    }

    /**
     * Tab comp. Override this if you want to use tab Complete.
     * <p>
     * The first argument you can complete is when length is set to 2 as 1 is
     * the sub command which will auto complete it for you.
     *
     * @param length the current command argument position
     * @return the list of available options for Tab complete.
     */
    @Override
    public List<String> tabComp(int length) {

        return null;
    }

    /**
     * Runs the command
     *
     * @param sender the sender
     * @param label the label
     * @param args the args
     * @return the boolean
     */
    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {

        this.sendMessage("Hello message");
        this.sendAdminMessage("Hello message");
        this.sendErrorMessage("Hello message");
        return true;
    }

}
