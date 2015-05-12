package com.relicum.duel.Objects;

import com.massivecraft.massivecore.adapter.relicum.RankArmor;
import com.relicum.duel.Handlers.LobbyArmor;
import com.relicum.pvpcore.Gamers.IPlayerSettings;
import com.relicum.pvpcore.Gamers.PlayerGameSettings;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

/**
 * LobbyLoadOut stores the inventory and settings players will be set to when in the lobby
 *
 * @author Relicum
 * @version 0.0.1
 */
public class LobbyLoadOut implements IPlayerSettings {

    private LobbyArmor armor;
    private PlayerGameSettings settings;
    private Collection<PotionEffect> potionEffects = new ArrayList<>();
    private ItemStack[] contents;
    private Map<Integer, ItemStack> items = new HashMap<>();
    private List<ItemStack> pots = new ArrayList<>();

    public LobbyLoadOut() {

    }

    public LobbyLoadOut(LobbyArmor armor, Map<Integer, ItemStack> items, Collection<PotionEffect> potionEffects, PlayerGameSettings settings) {
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

        ItemStack p = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta) p.getItemMeta();
        meta.addCustomEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 10, 2, false, false), true);
        p.setItemMeta(meta);

        this.pots.add(p);
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

    public List<ItemStack> getPotions() {
        return pots;
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
