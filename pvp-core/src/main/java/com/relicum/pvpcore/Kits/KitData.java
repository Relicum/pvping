package com.relicum.pvpcore.Kits;

import com.google.common.base.Objects;
import com.relicum.pvpcore.FormatUtil;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * LoadOut //todo needs documenting.
 *
 * @author Relicum
 * @version 0.0.1
 */
public class KitData {

    private String name;
    private List<String> lore = new ArrayList<>();
    private ItemStack icon = new ItemStack(Material.AIR);
    private String permission = "duel.player.loadout";
    private PlayerInventory inventory;
    private Collection<PotionEffect> potionEffects = new ArrayList<>();
    private boolean enabled = false;

    public KitData(String name, Player player) {

        setName(name);
        setInventory(player.getInventory());
        player.getActivePotionEffects().forEach(this::addPotionEffect);

    }

    public PlayerKit getPlayKit() {
        return new PlayerKit(getName(), getContents().clone(), getArmor().clone(), new ArrayList<>(getPotionEffects()));
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        Validate.notNull(name);

        this.name = FormatUtil.colorize(name);
    }

    public PlayerInventory getInventory() {
        return inventory;
    }

    public void setInventory(PlayerInventory inventory) {
        this.inventory = inventory;
    }

    public ItemStack[] getContents() {

        return inventory.getContents().clone();
    }

    public void addPotionEffect(PotionEffect effect) {
        potionEffects.add(effect);
    }

    public boolean hasEffects() {
        return !potionEffects.isEmpty();
    }

    public Collection<PotionEffect> getPotionEffects() {
        return potionEffects;
    }

    public void setPotionEffects(Collection<PotionEffect> potionEffects) {
        this.potionEffects = potionEffects;
    }

    public ItemStack getIcon() {

        return icon;
    }

    public void setIcon(ItemStack icon) {

        this.icon = icon;
    }

    public void updateIcon() {
        ItemMeta meta = icon.getItemMeta().clone();

        meta.setDisplayName(getName());
        meta.setLore(getLore());

        this.icon.setItemMeta(meta);
    }

    public void addLore(String line) {
        Validate.notNull(line);
        this.lore.add(line);
    }

    public boolean hasLore() {
        return !lore.isEmpty();
    }

    public List<String> getLore() {
        return Collections.unmodifiableList(lore);
    }

    public void setLore(List<String> lores) {
        this.lore = new ArrayList<>(lores);
    }

    public String getPermission() {

        return permission;
    }

    public void setPermission(String permission) {

        this.permission = permission;
    }

    public ItemStack[] getArmor() {
        return inventory.getArmorContents().clone();
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                       .add("enabled", enabled)
                       .add("icon", icon)
                       .add("inventory", inventory)
                       .add("lore", lore)
                       .add("name", name)
                       .add("permission", permission)
                       .add("potionEffects", potionEffects)
                       .toString();
    }
}
