package com.relicum.duel.Handlers;


import com.massivecraft.massivecore.adapter.relicum.RankArmor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.EnumMap;

/**
 * LobbyArmor creates color armor for the lobby, the color of the armor is based
 * on their Rank
 *
 * @author Relicum
 */
public class LobbyArmor {

    private EnumMap<RankArmor, ItemStack[]> armor = new EnumMap<>(RankArmor.class);

    public LobbyArmor(boolean init) {

        if (init) {
            initArmor();
        }
    }

    public LobbyArmor() {

    }

    private void initArmor() {

        for (RankArmor rankArmor : RankArmor.values()) {

            ItemStack[] stacks = new ItemStack[4];

            ItemStack hel = new ItemStack(Material.LEATHER_HELMET);
            LeatherArmorMeta hm = (LeatherArmorMeta) hel.getItemMeta();
            if (rankArmor.equals(RankArmor.DEV) || rankArmor.equals(RankArmor.OWNER)) {
                hm.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                hm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            hm.setColor(rankArmor.getColor());
            hel.setItemMeta(hm);

            stacks[3] = hel.clone();

            ItemStack ch = new ItemStack(Material.LEATHER_CHESTPLATE);

            LeatherArmorMeta cm = (LeatherArmorMeta) ch.getItemMeta();
            if (rankArmor.equals(RankArmor.DEV) || rankArmor.equals(RankArmor.OWNER)) {
                cm.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                cm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            cm.setColor(rankArmor.getColor());
            ch.setItemMeta(cm);

            stacks[2] = ch.clone();

            ItemStack leg = new ItemStack(Material.LEATHER_LEGGINGS);
            LeatherArmorMeta lm = (LeatherArmorMeta) leg.getItemMeta();
            if (rankArmor.equals(RankArmor.DEV) || rankArmor.equals(RankArmor.OWNER)) {
                lm.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                lm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            lm.setColor(rankArmor.getColor());
            leg.setItemMeta(lm);

            stacks[1] = leg.clone();

            ItemStack bot = new ItemStack(Material.LEATHER_BOOTS);
            LeatherArmorMeta bm = (LeatherArmorMeta) bot.getItemMeta();
            if (rankArmor.equals(RankArmor.DEV) || rankArmor.equals(RankArmor.OWNER)) {
                bm.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                bm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            bm.setColor(rankArmor.getColor());
            bot.setItemMeta(bm);

            stacks[0] = bot.clone();

            armor.put(rankArmor, stacks);

        }

    }

    /**
     * Get the colored lobby amour by rank.
     *
     * @param rank the players {@link RankArmor}
     * @return the armor ItemStack[]
     */
    public ItemStack[] getAmour(RankArmor rank) {

        return armor.get(rank).clone();
    }

}
