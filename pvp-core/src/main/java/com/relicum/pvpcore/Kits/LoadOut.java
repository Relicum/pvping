package com.relicum.pvpcore.Kits;

import com.google.common.base.Objects;
import com.relicum.pvpcore.Gamers.PlayerGameSettings;
import com.relicum.pvpcore.Gamers.PlayerGameSettingsBuilder;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
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


    public LoadOut(String loadoutName) {

        this.loadoutName = loadoutName;
    }

    public LoadOut(PlayerInventory inventory, Collection<PotionEffect> effects, String loadoutName) {

        this.inventory = inventory;
        this.effects.addAll(effects);
        this.loadoutName = loadoutName;
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
                       .toString();
    }
}
