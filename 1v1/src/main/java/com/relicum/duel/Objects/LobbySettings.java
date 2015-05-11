package com.relicum.duel.Objects;

import com.relicum.duel.Handlers.LobbyArmor;
import com.relicum.pvpcore.Gamers.IPlayerSettings;
import com.relicum.pvpcore.Gamers.PlayerGameSettings;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Name: LobbyPlayerConfigs.java Created: 03 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class LobbySettings implements IPlayerSettings {

    private LobbyArmor armor;
    private PlayerGameSettings settings;
    private List<PotionEffect> potionEffects = new ArrayList<>();
    private ItemStack[] contents;
    private Map<Integer, ItemStack> items = new HashMap<>();

    public LobbySettings() {

    }

    public LobbySettings(LobbyArmor armor, Map<Integer, ItemStack> items, List<PotionEffect> potionEffects, PlayerGameSettings settings) {
        this.armor = armor;
        this.items = items;
        this.potionEffects = potionEffects;
        this.settings = settings;

        contents = new ItemStack[36];
        for (int i = 0; i < 36; i++) {
            contents[i] = new ItemStack(Material.AIR);
        }


        for (Map.Entry<Integer, ItemStack> entry : items.entrySet()) {

            contents[entry.getKey()] = entry.getValue();
        }


    }

    public void setContents(ItemStack[] contents) {
        this.contents = contents;
    }

    public void setArmor(LobbyArmor armor) {
        this.armor = armor;
    }

    public void setItems(Map<Integer, ItemStack> items) {
        this.items = items;
    }

    public void addPotionEffect(PotionEffect effect) {
        this.potionEffects.add(effect);
    }

    public void setPotionEffects(List<PotionEffect> potionEffects) {
        this.potionEffects = potionEffects;
    }

    public void setSettings(PlayerGameSettings settings) {
        this.settings = settings;
    }


    public ItemStack[] getArmor(RankArmor rank) {
        return armor.getAmour(rank);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemStack[] getArmor() {
        return armor.getAmour(RankArmor.MEMBER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemStack[] getContents() {
        return contents;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<PotionEffect> getEffects() {
        return potionEffects;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerGameSettings getSettings() {
        return settings;
    }


}
