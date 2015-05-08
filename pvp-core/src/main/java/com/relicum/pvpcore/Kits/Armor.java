package com.relicum.pvpcore.Kits;

import com.relicum.pvpcore.Enums.ArmorItems;
import com.relicum.pvpcore.Enums.ArmorType;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Armor //todo needs documenting.
 * 
 * @author Relicum
 * @version 0.0.1
 */
@SerializableAs("Armor")
public class Armor implements ConfigurationSerializable {

    private String name;
    private EnumMap<ArmorType, ItemStack> armor = new EnumMap<>(ArmorType.class);

    public Armor(String name, Color color) {

        this.name = name;

        ItemStack hel = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta hm = (LeatherArmorMeta) hel.getItemMeta();
        hm.setColor(color);
        hel.setItemMeta(hm);

        armor.put(ArmorType.HELMET, hel);

        ItemStack ch = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta cm = (LeatherArmorMeta) ch.getItemMeta();
        cm.setColor(color);
        ch.setItemMeta(cm);

        armor.put(ArmorType.CHEST_PLATE, ch);

        ItemStack leg = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta lm = (LeatherArmorMeta) leg.getItemMeta();
        lm.setColor(color);
        leg.setItemMeta(lm);

        armor.put(ArmorType.LEGGINGS, leg);

        ItemStack bot = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta bm = (LeatherArmorMeta) bot.getItemMeta();
        bm.setColor(color);
        bot.setItemMeta(bm);

        armor.put(ArmorType.BOOTS, bot);

    }

    public Armor(String name, ItemStack helmet, ItemStack chest, ItemStack leg, ItemStack boots) {

        this.name = name;
        if (!helmet.getType().equals(Material.AIR))
        {
            setHelmet(helmet);
        }
        if (!chest.getType().equals(Material.AIR))
        {
            setChestPlate(chest);
        }
        if (!leg.getType().equals(Material.AIR))
        {
            setLeggings(leg);
        }
        if (!boots.getType().equals(Material.AIR))
        {
            setBoots(boots);
        }

    }

    public void setHelmet(ItemStack helmet) {

        Material mat = helmet.getType();
        if (ArmorItems.find(mat.name()) && ArmorItems.valueOf(mat.name()).getItem().equals("HELMET"))
            armor.put(ArmorType.HELMET, helmet);
        else
            throw new IllegalArgumentException("Must be an instance of a Helmet");
    }

    public void setChestPlate(ItemStack chestPlate) {

        Material mat = chestPlate.getType();
        if (ArmorItems.find(mat.name()) && ArmorItems.valueOf(mat.name()).getItem().equals("CHESTPLATE"))
            armor.put(ArmorType.CHEST_PLATE, chestPlate);
        else
            throw new IllegalArgumentException("Must be an instance of a Chestplate");
    }

    public void setLeggings(ItemStack leggings) {

        Material mat = leggings.getType();
        if (ArmorItems.find(mat.name()) && ArmorItems.valueOf(mat.name()).getItem().equals("LEGGINGS"))
            armor.put(ArmorType.LEGGINGS, leggings);
        else
            throw new IllegalArgumentException("Must be an instance of a Leggings");
    }

    public void setBoots(ItemStack boots) {

        Material mat = boots.getType();
        if (ArmorItems.find(mat.name()) && ArmorItems.valueOf(mat.name()).getItem().equals("BOOTS"))
            armor.put(ArmorType.BOOTS, boots);
        else
            throw new IllegalArgumentException("Must be an instance of a Boots");
    }

    public ItemStack getArmorPart(ArmorType part) {

        return armor.getOrDefault(part, new ItemStack(Material.AIR));
    }

    public ItemStack[] getArmor() {

        ItemStack[] stacks = new ItemStack[4];

        stacks[0] = armor.getOrDefault(ArmorType.HELMET, new ItemStack(Material.AIR));
        stacks[1] = armor.getOrDefault(ArmorType.CHEST_PLATE, new ItemStack(Material.AIR));
        stacks[2] = armor.getOrDefault(ArmorType.LEGGINGS, new ItemStack(Material.AIR));
        stacks[3] = armor.getOrDefault(ArmorType.BOOTS, new ItemStack(Material.AIR));

        return stacks;

    }

    /**
     * Gets name.
     *
     * @return Value of name.
     */
    public String getName() {

        return name;
    }

    public static Armor deserialize(Map<String, Object> map) {

        Object objName = map.get("name"), objHel = map.get("helmet"), objChes = map.get("chest"), objLeg = map.get("leggings"), objBoot = map.get("boots");

        return new Armor((String) objName, (ItemStack) objHel, (ItemStack) objChes, (ItemStack) objLeg, (ItemStack) objBoot);
    }

    @Override
    public Map<String, Object> serialize() {

        Map<String, Object> map = new HashMap<>();
        map.put("name", getName());
        map.put("helmet", getArmorPart(ArmorType.HELMET));
        map.put("chest", getArmorPart(ArmorType.CHEST_PLATE));
        map.put("leggings", getArmorPart(ArmorType.LEGGINGS));
        map.put("boots", getArmorPart(ArmorType.BOOTS));

        return map;
    }

    @Override
    public String toString() {

        return new org.apache.commons.lang.builder.ToStringBuilder(this).append("armor", armor).append("name", name).toString();
    }
}
