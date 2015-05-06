package com.relicum.pvpcore.Menus;

import com.relicum.pvpcore.Tasks.UpdateInventory;
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
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
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

        new BukkitRunnable() {
            int c = 0;

            public void run() {
                c++;
                if (c == 1) {
                    fromMenu.closeMenu(player);
                } else if (c == 2) {
                    toMenu.openMenu(player);
                } else if (c == 3) {
                    UpdateInventory.now(player, MenuAPI.get().getPlugin());
                } else {
                    player.sendMessage("finished switch menu task");
                    cancel();
                }
            }
        }.runTaskTimer(MenuAPI.get().getPlugin(), 1, 1);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMenuItemClicked(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        if ((inventory.getHolder() instanceof Menu) || (inventory.getHolder() instanceof ActionMenu)) {
            event.setCancelled(true);
            if (event.getAction().name().startsWith("DROP")) {
                return;
            }
            if (inventory.getHolder() instanceof Menu) {
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

                return;
            }
            if (inventory.getHolder() instanceof ActionMenu) {
                ActionMenu menu = (ActionMenu) inventory.getHolder();
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

    public static void switchMenu(Player player, ActionMenu fromMenu, ActionMenu toMenu) {

        new BukkitRunnable() {
            int c = 0;

            public void run() {
                c++;
                if (c == 1) {
                    fromMenu.closeMenu(player);
                } else if (c == 2) {
                    toMenu.openMenu(player);
                } else if (c == 3) {
                    UpdateInventory.now(player, MenuAPI.get().getPlugin());
                } else {
                    player.sendMessage("finished switch menu task");
                    cancel();
                }
            }
        }.runTaskTimer(MenuAPI.get().getPlugin(), 1, 1);

    }

    public static void switchMenu(Player player, Menu fromMenu, ActionMenu toMenu) {

        new BukkitRunnable() {
            int c = 0;

            public void run() {
                c++;
                if (c == 1) {
                    fromMenu.closeMenu(player);
                } else if (c == 2) {
                    toMenu.openMenu(player);
                } else if (c == 3) {
                    UpdateInventory.now(player, MenuAPI.get().getPlugin());
                } else {
                    player.sendMessage("finished switch menu task");
                    cancel();
                }
            }
        }.runTaskTimer(MenuAPI.get().getPlugin(), 1, 1);

    }
}
