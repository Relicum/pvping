package com.relicum.duel.Objects;


import com.massivecraft.massivecore.adapter.relicum.RankArmor;
import com.relicum.duel.Handlers.LobbyArmor;
import com.relicum.pvpcore.Gamers.IPlayerSettings;
import com.relicum.pvpcore.Gamers.PlayerGameSettings;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LobbyLoadOut stores the inventory and settings players will be set to when in the lobby
 *
 * @author Relicum
 * @version 0.0.1
 */
public class LobbyLoadOut implements IPlayerSettings {

    private transient LobbyArmor armor;
    private PlayerGameSettings settings;
    private Collection<PotionEffect> potionEffects = new ArrayList<>();
    private ItemStack[] contents;
    private Map<Integer, ItemStack> items = new HashMap<>();
    private List<ItemStack> pots = new ArrayList<>();

    public LobbyLoadOut() {

        armor = new LobbyArmor(true);

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

    /**
     * Sets the contents of the players inventory will in the lobby.
     *
     * @param items the map of {@link ItemStack} that makes up the inventory.
     */
    public void setItems(Map<Integer, ItemStack> items) {

        this.items = items;
    }

    /**
     * Add {@link PotionEffect} to the Loadout.
     *
     * @param effect the {@link PotionEffect} to add.
     */
    public void addPotionEffect(PotionEffect effect) {

        Validate.notNull(effect);
        Validate.isTrue(!containsPotionEffect(effect), "The loadout already contains the effect :" + effect.getType().getName());
        potionEffects.add(effect);

    }

    /**
     * Checks to see if the specified {@link PotionEffect} has already been added to the loadout.
     *
     * @param pe the {@link PotionEffect} to test for.
     * @return true if the {@link PotionEffect} has already been added, false if not.
     */
    public boolean containsPotionEffect(PotionEffect pe) {

        return potionEffects.contains(pe);
    }


    public void setPotions(List<ItemStack> potionEffects) {

        this.pots = potionEffects;
    }

    /**
     * Get the {@link LobbyArmor} based on the players rank decided in {@link RankArmor} that they wear in the lobby.
     *
     * @param rank the players rank {@link RankArmor}
     * @return the set of armor as an {@link ItemStack[]}
     */
    public ItemStack[] getArmor(RankArmor rank) {

        return armor.getAmour(rank);
    }

    /**
     * Init potions, should be called directed after deserialization.
     */
    public void initPotions() {

        for (ItemStack pot : pots) {
            PotionMeta meta = (PotionMeta) pot.getItemMeta();

            this.potionEffects.add(meta.getCustomEffects().get(0));

        }
    }


    /**
     * Get the {@link LobbyArmor} that standard members will wear.
     *
     * @return the set of armor as an {@link ItemStack[]}
     */
    @Override
    public ItemStack[] getArmor() {

        return armor.getAmour(RankArmor.MEMBER);
    }

    /**
     * Sets all the different armor sets {@link LobbyArmor}.
     *
     * @param armor the {@link LobbyArmor}
     */
    public void setArmor(LobbyArmor armor) {

        this.armor = armor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemStack[] getContents() {

        return contents.clone();
    }

    /**
     * Sets the contents of the players inventory they will wear in the lobby.
     *
     * @param contents the {@link ItemStack} that makes up the inventory.
     */
    public void setContents(ItemStack[] contents) {

        this.contents = contents;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<PotionEffect> getEffects() {

        return new ArrayList<>(potionEffects);
    }

    /**
     * Sets a collection of {@link PotionEffect} that is applied to the player in the lobby.
     *
     * @param potionEffects the {@link Collection} of {@link PotionEffect}
     */
    public void setPotionEffects(Collection<PotionEffect> potionEffects) {

        this.potionEffects = potionEffects;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerGameSettings getSettings() {

        return settings;
    }

    /**
     * Sets the player settings that are applied to the user while in the lobby.
     *
     * @param settings the players settings that get applied when joining the lobby, {@link PlayerGameSettings}
     */
    public void setSettings(PlayerGameSettings settings) {

        this.settings = settings;
    }


}
