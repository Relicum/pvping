package com.relicum.duel.Menus;

import com.relicum.duel.Configs.DuelConfigs;
import com.relicum.duel.Duel;
import com.relicum.pvpcore.FormatUtil;
import com.relicum.pvpcore.Menus.AbstractItem;
import com.relicum.pvpcore.Menus.ActionHandler;
import com.relicum.pvpcore.Menus.ActionMenu;
import com.relicum.pvpcore.Menus.ActionResponse;
import com.relicum.pvpcore.Menus.BStack;
import com.relicum.pvpcore.Menus.BooleanItem;
import com.relicum.pvpcore.Menus.ClickAction;
import com.relicum.utilities.Items.MLore;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Name: DuelSettingsMenu.java Created: 18 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class DuelSettingsMenu extends ActionMenu {

    private DuelConfigs configs;

    public DuelSettingsMenu(String title, int rows, DuelConfigs configs) {
        super(title, rows);
        this.configs = configs;

        setEditing(true);
        initItems();
    }

    /**
     * Gets configs.
     *
     * @return the {@link DuelConfigs}
     */
    public DuelConfigs getConfigs() {
        return configs;
    }

    public void initItems() {
        setEnabledItem();
        setClosedItem();
    }


    /**
     * Save {@link DuelConfigs}.
     */
    public void saveConfigs() {
        Duel.get().saveConfigs();
    }

    public void registerItems(AbstractItem item) {

        addMenuItem(item, item.getIndex());
    }

    public void setEnabledItem() {

        BStack ens = new BStack(FormatUtil.colorize("&a&l1v1 Enabled"), new MLore(" \n").then("&4Click to disable the").append("&4plugin completely").toLore());
        BStack ds = new BStack(FormatUtil.colorize("&4&l1v1 Disabled"), new MLore(" \n").then("&aClick to enable the plugin").toLore());

        BooleanItem it = new BooleanItem(ens, ds, 0, configs.isEnable(), ClickAction.CONFIG, new ActionHandler() {
            @Override
            public ActionHandler getExecutor() {
                return this;
            }

            public ActionResponse perform(Player player, AbstractItem icon, InventoryClickEvent event) {

                event.setCancelled(true);
                BooleanItem b = (BooleanItem) icon;

                if (getConfigs().isEnable()) {
                    getConfigs().setEnable(false);
                    b.setActive(false);

                    icon.setIcon(b.updateIcon());
                    removeMenuItem(icon.getIndex());
                    addMenuItem(icon, icon.getIndex());
                    getInventory().setItem(icon.getIndex(), icon.getItem());
                    player.sendMessage(ChatColor.GOLD + "You have now disabled the plugin");
                }
                else {
                    getConfigs().setEnable(true);
                    b.setActive(true);

                    icon.setIcon(b.updateIcon());
                    removeMenuItem(icon.getIndex());
                    addMenuItem(icon, icon.getIndex());
                    getInventory().setItem(icon.getIndex(), icon.getItem());
                    player.sendMessage(ChatColor.GOLD + "You have now enabled the plugin");
                }
                saveConfigs();
                ActionResponse response = new ActionResponse(icon, player);
                response.setWillUpdate(true);
                return response;
            }
        });

        registerItems(it);
    }

    public void setClosedItem() {

        AbstractItem cl = new CloseItem((rows * 9) - 1);

        registerItems(cl);
    }
}
