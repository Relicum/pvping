package com.relicum.pvpcore.Kits;

import com.google.common.base.Objects;
import com.relicum.pvpcore.FormatUtil;
import com.relicum.pvpcore.Gamers.PlayerGameSettings;
import com.relicum.pvpcore.Gamers.PlayerGameSettingsBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * LoadOut //todo needs documenting.
 *
 * @author Relicum
 * @version 0.0.1
 */
public class LoadOut {

    private String loadoutName;
    private PlayerInventory inventory;
    private Collection<PotionEffect> effects = new ArrayList<>();
    private String loadOutPermission = "duel";
    private List<String> description = new LinkedList<>();
    private PlayerGameSettings settings;
    private String material = "";
    private String displayName = "";


    public LoadOut(String loadoutName) {

        this.loadoutName = loadoutName;
    }

    public LoadOut(PlayerInventory inventory, Collection<PotionEffect> effects, String loadoutName) {

        this.inventory = inventory;
        this.effects.addAll(effects);
        this.loadoutName = loadoutName;
        loadOutPermission = "duel.kit." + ChatColor.stripColor(loadoutName);
        this.material = Material.IRON_SWORD.name();
        this.displayName = "&6&l" + loadoutName;
        description.add("&6the first");
        description.add("&6the second");
        ((LinkedList<String>) description).addLast("&bClick to go back");
        settings = PlayerGameSettingsBuilder.builder().setGameMode(GameMode.SURVIVAL.name()).build();


    }

    public String getLoadoutName() {

        return loadoutName;
    }

    public void setLoadoutName(String loadoutName) {
        this.loadoutName = loadoutName;
    }

    public PlayerInventory getInventory() {

        return inventory;
    }

    public ItemStack[] getArmor() {

        return inventory.getArmorContents().clone();
    }

    public ItemStack[] getContents() {

        return inventory.getContents().clone();
    }

    public Collection<PotionEffect> getEffects() {

        return new ArrayList<>(effects);
    }

    public String getPermission() {
        return loadOutPermission;
    }

    public void setPermission(String loadOutPermission) {
        this.loadOutPermission = loadOutPermission;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(LinkedList<String> description) {
        this.description = description;
    }

    public void addDescription(String description) {
        this.description.add(description);
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = FormatUtil.colorize(displayName);
    }

    public Material getMaterial() {
        return Material.valueOf(material);
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public ItemStack getIcon() {

        ItemStack stack = new ItemStack(getMaterial());
        ItemMeta meta = Bukkit.getItemFactory().getItemMeta(getMaterial());
        meta.setDisplayName(getDisplayName());
        meta.setLore(getDescription());
        stack.setItemMeta(meta);

        return stack;
    }

    public void applySettings(Player player) {

        settings.apply(player);

    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                       .add("loadoutName", loadoutName)
                       .add("inventory", inventory.toString())
                       .add("effects", effects.toString())
                       .add("loadOutPermission", loadOutPermission)
                       .add("description", description.toString())
                       .add("settings", settings.toString())
                       .add("material", material)
                       .add("displayName", getDisplayName())
                       .toString();
    }
}
