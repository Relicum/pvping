package com.relicum.pvpcore.Menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import java.util.HashMap;
import java.util.Map;

/**
 * Name: Menu.java Created: 02 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class ActionMenu implements InventoryHolder {
    protected Map<Integer, AbstractItem> items;
    protected Inventory inventory;
    protected String title;
    protected int rows;
    protected boolean exitOnClickOutside;
    protected MenuCloseBehaviour menuCloseBehaviour;
    protected boolean bypassMenuCloseBehaviour;
    protected ActionMenu parentMenu;

    public ActionMenu(String title, int rows) {
        this(title, rows, null);
    }

    public ActionMenu(String title, int rows, ActionMenu parentMenu) {
        this.items = new HashMap<>();
        this.exitOnClickOutside = true;
        this.bypassMenuCloseBehaviour = false;
        this.title = title;
        this.rows = rows;
        this.parentMenu = parentMenu;
    }

    public void setParentMenu(ActionMenu parentMenu) {
        this.parentMenu = parentMenu;
    }

    public void setMenuCloseBehaviour(MenuCloseBehaviour menuCloseBehaviour) {
        this.menuCloseBehaviour = menuCloseBehaviour;
    }

    public MenuCloseBehaviour getMenuCloseBehaviour() {
        return this.menuCloseBehaviour;
    }

    public void setBypassMenuCloseBehaviour(boolean bypassMenuCloseBehaviour) {
        this.bypassMenuCloseBehaviour = bypassMenuCloseBehaviour;
    }

    public boolean bypassMenuCloseBehaviour() {
        return this.bypassMenuCloseBehaviour;
    }

    public void setExitOnClickOutside(boolean exit) {
        this.exitOnClickOutside = exit;
    }

    public Map<Integer, AbstractItem> getMenuItems() {
        return this.items;
    }

    public boolean addMenuItem(AbstractItem item, int x, int y) {
        return addMenuItem(item, y * 9 + x);
    }

    public boolean addMenuItem(AbstractItem item, int index) {
        ItemStack slot = getInventory().getItem(index);
        if ((slot != null) && (slot.getType() != Material.AIR)) {
            return false;
        }
        getInventory().setItem(index, item.getIcon());
        this.items.put(index, item);
        item.addToMenu(this);
        return true;
    }

    public boolean removeMenuItem(int x, int y) {
        return removeMenuItem(y * 9 + x);
    }

    public boolean removeMenuItem(int index) {
        ItemStack slot = getInventory().getItem(index);
        if ((slot == null) || (slot.getType().equals(Material.AIR))) {
            return false;
        }
        getInventory().clear(index);
        this.items.remove(index).removeFromMenu(this);
        return true;
    }

    protected void selectMenuItem(Player player, int index) {
        if (this.items.containsKey(index)) {
            AbstractItem item = this.items.get(index);
            item.onClick(player);
        }
    }

    public void openMenu(Player player) {
        if (getInventory().getViewers().contains(player)) {
            throw new IllegalArgumentException(String.valueOf(player.getName()) + " is already viewing " + getInventory().getTitle());
        }
        player.openInventory(getInventory());
    }

    public void closeMenu(Player player) {
        if (getInventory().getViewers().contains(player)) {
            getInventory().getViewers().remove(player);
            player.closeInventory();
        }
    }

    public ActionMenu getParent() {
        return this.parentMenu;
    }

    public void switchMenu(Player player, ActionMenu toMenu) {

        MenuAPI.switchMenu(player, this, toMenu);
    }

    public Inventory getInventory() {
        if (this.inventory == null) {
            this.inventory = Bukkit.createInventory(this, this.rows * 9, this.title);
        }
        return this.inventory;
    }

    public boolean exitOnClickOutside() {
        return this.exitOnClickOutside;
    }

    protected ActionMenu clone() {
        ActionMenu clone = new ActionMenu(this.title, this.rows);
        clone.setExitOnClickOutside(this.exitOnClickOutside);
        clone.setMenuCloseBehaviour(this.menuCloseBehaviour);
        for (Integer index : this.items.keySet()) {
            addMenuItem(this.items.get(index), index);
        }
        return clone;
    }

    public void updateMenu() {
        getInventory().getViewers().stream().filter(entity -> (entity instanceof Player)).forEach(entity -> {
            Player player = (Player) entity;
            player.updateInventory();
        });
    }
}
