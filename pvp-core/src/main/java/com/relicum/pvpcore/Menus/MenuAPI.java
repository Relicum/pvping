package com.relicum.pvpcore.Menus;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class MenuAPI<T extends JavaPlugin> implements Listener {
    private static MenuAPI INSTANCE = null;
    private T plugin;

    public MenuAPI(T plugin) {
        this.plugin = plugin;
        INSTANCE = this;
    }

    public static MenuAPI get() {
        return INSTANCE;
    }

    public T getPlugin() {
        return plugin;
    }

    public Menu createMenu(String title, int rows) {
        return new Menu(title, rows);
    }

    public Menu cloneMenu(Menu menu) {
        return menu.clone();
    }

    public void removeMenu(Menu menu) {
        for (HumanEntity viewer : menu.getInventory().getViewers())
            if ((viewer instanceof Player)) {
                menu.closeMenu((Player) viewer);
            } else
                viewer.closeInventory();
    }

    public static void switchMenu(final Player player, Menu fromMenu, Menu toMenu) {
        fromMenu.closeMenu(player);
        new BukkitRunnable() {
            public void run() {
                toMenu.openMenu(player);
            }
        }.runTask(MenuAPI.get().getPlugin());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMenuItemClicked(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        if ((inventory.getHolder() instanceof Menu)) {
            event.setCancelled(true);
            if (event.getAction().name().startsWith("DROP")) {
                return;
            }
            Menu menu = (Menu) inventory.getHolder();
            if ((event.getWhoClicked() instanceof Player)) {
                Player player = (Player) event.getWhoClicked();
                if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) {
                    if (menu.exitOnClickOutside())
                        menu.closeMenu(player);
                } else {
                    int index = event.getRawSlot();
                    if (index < inventory.getSize()) {
                        menu.selectMenuItem(player, index);
                    } else if (menu.exitOnClickOutside())
                        menu.closeMenu(player);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMenuClosed(InventoryCloseEvent event) {
        if ((event.getPlayer() instanceof Player)) {
            Inventory inventory = event.getInventory();
            if ((inventory.getHolder() instanceof Menu)) {
                Menu menu = (Menu) inventory.getHolder();
                MenuCloseBehaviour menuCloseBehaviour = menu.getMenuCloseBehaviour();
                if (menuCloseBehaviour != null)
                    menuCloseBehaviour.onClose((Player) event.getPlayer(), menu, menu.bypassMenuCloseBehaviour());
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerLogoutCloseMenu(PlayerQuitEvent event) {
        if ((event.getPlayer().getOpenInventory() == null) || (!(event.getPlayer().getOpenInventory().getTopInventory().getHolder() instanceof Menu))) {
            return;
        }
        Menu menu = (Menu) event.getPlayer().getOpenInventory().getTopInventory().getHolder();
        menu.setBypassMenuCloseBehaviour(true);
        menu.setMenuCloseBehaviour(null);
        event.getPlayer().closeInventory();
    }
}
