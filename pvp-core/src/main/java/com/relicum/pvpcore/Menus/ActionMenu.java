package com.relicum.pvpcore.Menus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Name: Menu.java Created: 02 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class ActionMenu implements InventoryHolder {

    private static final long serialVersionUID = 1L;

    protected Map<Integer, AbstractItem> items;
    protected Inventory inventory;
    protected String title;
    protected int rows;
    protected boolean exitOnClickOutside;
    protected MenuCloseBehaviour menuCloseBehaviour;
    protected boolean bypassMenuCloseBehaviour;
    protected transient ActionMenu parentMenu;
    private transient boolean modifiable = false;
    private transient boolean editing = false;
    private boolean altered = false;


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

    public MenuCloseBehaviour getMenuCloseBehaviour() {

        return this.menuCloseBehaviour;
    }

    public void setMenuCloseBehaviour(MenuCloseBehaviour menuCloseBehaviour) {

        this.menuCloseBehaviour = menuCloseBehaviour;
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

    public int getSize() {

        return rows * 9;
    }

    public boolean addMenuItem(AbstractItem item, int x, int y) {

        return addMenuItem(item, y * 9 + x);
    }

    public boolean addMenuItem(AbstractItem item, int index) {

        ItemStack slot = getInventory().getItem(index);
        if ((slot != null) && (slot.getType() != Material.AIR)) {
            return false;
        }
        getInventory().setItem(index, item.getItem());
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

    /**
     * Replace menu item.
     * <p>Removes item from inventory at specified index and removes it from the internal map.
     * <p>Then adds the item to the inventory at the specified index and adds it to the internal map.
     *
     * @param index the index
     * @param item  the item
     * @return true if the item was successfully added, false if there was a error.
     */
    public boolean replaceMenuItem(int index, AbstractItem item) {

        if (removeMenuItem(index)) {

            System.out.println("Item Successfully removed from menu");

            if (addMenuItem(item, index)) {

                System.out.println("Item successfully replaced");
                return true;
            }
            else {
                System.out.println("Error replacing item");
                return false;
            }
        }
        else {
            System.out.println("Error removing item from menu");
            return false;
        }

    }

    protected void selectMenuItem(Player player, int index) {

        if (this.items.containsKey(index)) {
            AbstractItem item = this.items.get(index);
            item.onClick(player);
        }
    }

    public void onInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        if (event.getSlotType().equals(InventoryType.SlotType.OUTSIDE) && exitOnClickOutside()) {
            event.setCancelled(true);
            player.playSound(player.getLocation(), Sound.CHEST_CLOSE, 10.0f, 1.0f);
            closeMenu(player);
            return;
        }

        if (isEditing()) {

            editHandler(event);
            return;
        }

        int index = event.getRawSlot();
        if (index > getSize()) {
            event.setCancelled(true);
            closeMenu(player);
            return;
        }


        if (!items.containsKey(index)) {
            event.setCancelled(true);
            return;
        }

        AbstractItem item = items.get(index);

        if (!item.hasAction()) {
            event.setCancelled(true);
            return;
        }

        if (item.getAction().equals(ClickAction.CLOSE_MENU)) {
            event.setCancelled(true);
            player.playSound(player.getLocation(), Sound.CHEST_CLOSE, 10.0f, 1.0f);
            closeMenu(player);
            return;
        }

        ActionResponse response = item.getActionHandler().perform(player, item);

        if (response.isUpdate()) {
            updateMenu();
        }

        if (response.isClose()) {
            event.setCancelled(true);
            response.getPlayer().sendMessage(ChatColor.GREEN + "Closing from Action Response !");
            item.getMenu().closeMenu(response.getPlayer());
            return;
        }
        if (response.isNothing()) {
            event.setCancelled(true);
            response.getPlayer().sendMessage(ChatColor.GREEN + "Ok will do nothing");
            return;
        }

    }


    public void editHandler(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        AbstractItem item;

        int index = event.getRawSlot();
        if (!items.containsKey(index)) {

            if (index < getSize()) {
                event.setCancelled(true);
                return;
            }
            else {
                event.setCancelled(true);
                closeMenu(player);
                return;
            }
        }
        else {

            item = items.get(index);
        }

        if (item.getAction() == ClickAction.VALIDATION) {

            if (index < 4) {
                player.playSound(player.getEyeLocation(), Sound.LEVEL_UP, 10.0f, 1.0f);
                player.sendMessage(ChatColor.GREEN + "Confirmation given");
            }
            else {
                player.playSound(player.getEyeLocation(), Sound.ZOMBIE_PIG_ANGRY, 10.0f, 1.0f);
                player.sendMessage(ChatColor.DARK_RED + "Confirmation cancelled");
            }

            item.getMenu().closeMenu(player);

            return;
        }
        ActionResponse response = item.getActionHandler().perform(player, item, event);

//        if (!response.isModifiable()) {
//
//            if (!event.isCancelled())
//                event.setCancelled(true);
//
//            player.sendMessage("Error: the menu is not set to modifiable, set that first.");
//
//            return;
//        }

        if (response.isClose()) {
            item.getMenu().closeMenu(response.getPlayer());
            return;
        }

        if (response.isToggle()) {


        }

        if (response.isUpdate()) {
            updateMenu();
            return;
        }

        player.sendMessage(ChatColor.GREEN + "You are in the editing handler");
    }

    public void openMenu(Player player) {

        if (getInventory().getViewers().contains(player)) {
            throw new IllegalArgumentException(String.valueOf(player.getName()) + " is already viewing " + getInventory().getTitle());
        }
        player.openInventory(getInventory());
    }

    public void openMenuForEditing(Player player) {

        if (getInventory().getViewers().contains(player)) {
            throw new IllegalArgumentException(String.valueOf(player.getName()) + " is already viewing " + getInventory().getTitle());
        }
        this.setEditing(true);
        player.openInventory(getInventory());
    }

    public void closeMenu(Player player) {

        this.setEditing(false);
        if (getInventory().getViewers().contains(player)) {
            getInventory().getViewers().remove(player);
            player.closeInventory();
        }
    }

    public ActionMenu getParent() {

        return this.parentMenu;
    }

    public void switchMenu(Player player, @Nullable ActionMenu toMenu) {

        MenuAPI.switchMenu(player, this, toMenu);
    }

    public Inventory getInventory() {

        if (this.inventory == null) {
            this.inventory = Bukkit.createInventory(this, this.rows * 9, this.title);

        }
        return this.inventory;
    }

    /**
     * Is the menu altered if so re construct the inventory.
     *
     * @return true if it has been altered, false if it has not.
     */
    public boolean isAltered() {

        return altered;
    }

    /**
     * Set if the menu has been altered, this will force the inventory to be re
     * constructed.
     *
     * @param altered set true if the menu has been altered and requires the
     *                inventory to be re constructed.
     */
    public void setAltered(boolean altered) {

        this.altered = altered;
    }

    public boolean isEditing() {

        return editing;
    }

    public void setEditing(boolean editing) {

        this.editing = editing;
    }

    public boolean exitOnClickOutside() {

        return this.exitOnClickOutside;
    }

    /**
     * Gets if this menu can be modified.
     *
     * @return true and it can be modified, false and it can not.
     */
    public boolean isModifiable() { return modifiable; }

    /**
     * Sets if the menu can be modified.
     *
     * @param modifiable set true and it can be modified, false and it can not.
     */
    public void setModifiable(boolean modifiable) { this.modifiable = modifiable; }

    protected ActionMenu clone() {

        ActionMenu clone = new ActionMenu(this.title, this.rows);
        clone.setExitOnClickOutside(this.exitOnClickOutside);
        clone.setMenuCloseBehaviour(this.menuCloseBehaviour);
        for (Map.Entry<Integer, AbstractItem> index : this.items.entrySet()) {
            addMenuItem(index.getValue(), index.getKey());
        }
        return clone;
    }

    public void updateMenu() {

        getInventory()
                .getViewers()
                .stream()
                .filter(entity -> (entity instanceof Player))
                .map(entity -> (Player) entity)
                .forEach(Player::updateInventory);
    }
}
