package com.relicum.pvpcore.Menus;

import com.relicum.pvpcore.FormatUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Name: MenuItem.java Created: 02 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public abstract class MenuItem implements ItemClickHandler {

    private Menu menu;
    private int index;
    private ItemStack icon;
    private String text;
    private List<String> descriptions;

    public MenuItem(ItemStack icon) {

        this(null, icon);
    }

    public MenuItem(String text) {

        this(text, new ItemStack(Material.PAPER));
    }

    public MenuItem(String text, ItemStack icon) {

        this(text, icon, 1);
    }

    public MenuItem(String text, ItemStack icon, int index) {

        this.text = null;
        this.descriptions = new ArrayList<>();
        if (text != null) {
            this.text = FormatUtil.format(text, new Object[0]);
        }
        this.icon = icon;
        this.index = index;
    }

    public MenuItem(String text, ItemStack icon, int index, List<String> desc) {

        this.text = null;
        this.descriptions = new ArrayList<>();
        if (text != null) {
            this.text = FormatUtil.format(text);
        }

        this.icon = icon;
        this.index = index;
    }

    protected void addToMenu(Menu menu) {

        this.menu = menu;
    }

    protected void removeFromMenu(Menu menu) {

        if (this.menu == menu)
            this.menu = null;
    }

    public Menu getMenu() {

        return this.menu;
    }

    public int getIndex() {

        return this.index;
    }

    public ItemStack getIcon() {

        return this.icon;
    }

    public String getText() {

        return this.text;
    }

    public void setDescriptions(List<String> lines) {

        lines.forEach(this::addDescription);

    }

    public void addDescription(String line) {

        this.descriptions.add(FormatUtil.format(line, new Object[0]));
    }

    protected ItemStack getItemStack() {

        ItemStack slot = getIcon().clone();
        ItemMeta meta = slot.getItemMeta();
        if (meta.hasLore()) {
            meta.getLore().addAll(this.descriptions);
        } else {
            meta.setLore(this.descriptions);
        }
        if (getText() != null) {
            meta.setDisplayName(getText());
        }
        slot.setItemMeta(meta);
        return slot;
    }

    public abstract void onClick(Player paramPlayer);
}
