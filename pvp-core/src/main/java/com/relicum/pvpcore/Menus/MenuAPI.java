package com.relicum.pvpcore.Menus;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class MenuAPI<T extends JavaPlugin> implements Listener {

    private static MenuAPI INSTANCE = null;
    private T plugin;

    public MenuAPI(T plugin) {

        this.plugin = plugin;
        INSTANCE = this;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public static MenuAPI get() {

        return INSTANCE;
    }

    public static void switchMenu(Player player, ActionMenu fromMenu, ActionMenu toMenu) {

        fromMenu.closeMenu(player);

        new BukkitRunnable() {

            int c = 0;

            public void run() {

                c++;
                if (c == 1) {
                    toMenu.openMenu(player);
                }
                else if (c == 2) {
                    player.updateInventory();
                    player.sendMessage("finished switch menu task");
                    cancel();
                }
            }
        }.runTaskTimer(MenuAPI.get().getPlugin(), 1, 1);

    }

    public T getPlugin() {

        return plugin;
    }

    public ItemStack[] getRow() {

        ItemStack[] tmp = new ItemStack[8];

        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = new ItemStack(Material.AIR);
        }

        return tmp;
    }

    public ActionMenu createMenu(String title, int rows) {

        return new ActionMenu(title, rows);
    }

    public ActionMenu cloneMenu(ActionMenu menu) {

        return menu.clone();
    }

    public void removeMenu(ActionMenu menu) {

        for (HumanEntity viewer : menu.getInventory()
                                          .getViewers()) {
            if ((viewer instanceof Player)) {
                menu.closeMenu((Player) viewer);
            }
            else {
                viewer.closeInventory();
            }
        }
    }

    // todo remove the tmp dupe code in this method
    @EventHandler(priority = EventPriority.LOWEST)
    public void onMenuItemClicked(InventoryClickEvent event) {

        Inventory inventory = event.getInventory();
        if (inventory.getHolder() instanceof ActionMenu) {

            if (!((ActionMenu) inventory.getHolder()).isModifiable()) {
                plugin.getLogger().warning("Action :" + event.getAction().name());

                event.setCancelled(true);
            }

            ((ActionMenu) event.getInventory().getHolder()).onInventoryClick(event);

        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMenuClosed(InventoryCloseEvent event) {

        if ((event.getPlayer() instanceof Player)) {

            Inventory inventory = event.getInventory();

            if ((inventory.getHolder() instanceof ActionMenu)) {

                ActionMenu menu = (ActionMenu) inventory.getHolder();
                MenuCloseBehaviour menuCloseBehaviour = menu.getMenuCloseBehaviour();

                if (menuCloseBehaviour != null) {

                    menuCloseBehaviour.onClose((Player) event.getPlayer(), menu, menu.bypassMenuCloseBehaviour());
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerLogoutCloseMenu(PlayerQuitEvent event) {

        if ((event.getPlayer().getOpenInventory() == null) || (!(event.getPlayer().getOpenInventory().getTopInventory().getHolder() instanceof ActionMenu))) {
            return;
        }
        ActionMenu menu = (ActionMenu) event.getPlayer().getOpenInventory().getTopInventory().getHolder();
        menu.setBypassMenuCloseBehaviour(true);
        menu.setMenuCloseBehaviour(null);
        event.getPlayer().closeInventory();
    }

}
