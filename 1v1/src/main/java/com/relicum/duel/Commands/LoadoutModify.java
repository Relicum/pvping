package com.relicum.duel.Commands;

import com.relicum.commands.Annotations.Command;
import com.relicum.duel.Duel;
import com.relicum.duel.Menus.CloseItem;
import com.relicum.duel.Menus.DuelSettingsMenu;
import com.relicum.pvpcore.FormatUtil;
import com.relicum.pvpcore.Kits.LoadOut;
import com.relicum.pvpcore.Menus.AbstractItem;
import com.relicum.pvpcore.Menus.ActionHandler;
import com.relicum.pvpcore.Menus.ActionItem;
import com.relicum.pvpcore.Menus.ActionMenu;
import com.relicum.pvpcore.Menus.ActionResponse;
import com.relicum.pvpcore.Menus.ClickAction;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Name: LoadoutModify.java Created: 25 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
@Command(aliases = {"modifykit"}, desc = "Modify game kits, set a name and it clones the items you currently have", perm = "duel.admin.modifykit",
                usage = "/noxarena modifykit", isSub = true, parent = "noxarena", min = 0, useTab = true)
public class LoadoutModify extends DuelCmd {

    private final String KIT_KEY = "modifykit";
    private List<String> kitNames = new ArrayList<>();

    /**
     * Instantiates a new AbstractCommand
     *
     * @param plugin the plugin
     */
    public LoadoutModify(Duel plugin) {
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

        if (args.length == 0) {

            ActionMenu menu = plugin.getCacheManager().getMenu(KIT_KEY);

            if (menu != null && !menu.isAltered()) {
                sendAdminMessage("Using cached menu");
                menu.setModifiable(true);
                menu.setEditing(true);

                menu.openMenuForEditing((Player) sender);

                return true;

            }

            if (menu == null) {
                int total = plugin.getKitHandler().getNumberOfKits();
                menu = plugin.getMenuManager().getKitModifyMenu(total + 1);

                int c = 0;

                for (Map.Entry<UUID, LoadOut> entry : plugin.getKitHandler().getAllKits()) {
                    LoadOut data = entry.getValue();
                    ActionItem item = new ActionItem(data.getIcon(), c, ClickAction.CONFIG, new ActionHandler() {
                        @Override
                        public ActionHandler getExecutor() {
                            return this;
                        }

                        @Override
                        public ActionResponse perform(Player player, AbstractItem icon, InventoryClickEvent event) {
                            event.setCancelled(true);

                            player.playSound(player.getLocation(), Sound.CLICK, 10.0f, 1.0f);

                            DuelSettingsMenu d = new DuelSettingsMenu(FormatUtil.colorize("&6&lKit: " + icon.getText()), 1, Duel.get().getConfigs());


                            plugin.getCacheManager().putMenu("duel", d);

                            icon.getMenu().switchMenu(player, plugin.getCacheManager().getMenu("duel"));
                            sendAdminMessage("Kit is " + icon.getText());

                            ActionResponse response = new ActionResponse(icon, player);
                            response.setWillClose(true);
                            return response;
                        }
                    });

                    item.setDescription(data.getDescription());
                    item.setText(data.getDisplayName());

                    menu.addMenuItem(item, c);

                    c++;
                }

                menu.addMenuItem(new CloseItem(menu.getSize() - 1), menu.getSize() - 1);
            }
            menu.setModifiable(true);
            menu.setEditing(true);
            plugin.getCacheManager().putMenu(KIT_KEY, menu);
            sendAdminMessage("Loading new and Caching will expire after 30 seconds");
            menu.openMenuForEditing((Player) sender);

            return true;
        }

        return true;
    }


    @Override
    public List<String> tabComp(int i, String[] args) {
        return Collections.emptyList();
    }
}
