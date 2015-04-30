package com.relicum.pvpcore.Kits;

import com.google.common.collect.Maps;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;
import java.util.HashMap;
import java.util.Map;

/**
 * LoadOut //todo needs documenting.
 *
 * @author Relicum
 * @version 0.0.1
 */
@SerializableAs("LoadOut")
public class LoadOut implements ConfigurationSerializable {

    private String name;
    private Armor armor;
    private Map<Integer, ItemStack> items = Maps.newHashMap();
    private ItemStack icon;
    private String permission;

    public LoadOut(String name) {

        this.name = name;
    }

    public LoadOut(String name, Armor armor, ItemStack icon, Map<Integer, ItemStack> items, String perm) {

        this.name = name;
        this.armor = armor;
        this.icon = icon;
        this.items.putAll(items);
        this.permission = perm;
    }

    public String getName() {

        return name;
    }

    public ItemStack getIcon() {

        return icon;
    }

    public String getPermission() {

        return permission;
    }

    public void setPermission(String permission) {

        this.permission = permission;
    }

    public void setIcon(ItemStack icon) {

        this.icon = icon;
    }

    public void addItem(int slot, ItemStack stack) {

        this.items.put(slot, stack);
    }

    public ItemStack getStackBySlot(int slot) {

        return items.get(slot);
    }

    public ItemStack[] getItems() {

        ItemStack[] hotbar = new ItemStack[9];

        for (int i = 0; i < 9; i++) {
            if (items.containsKey(i))
                hotbar[i] = items.get(i);
            else
                hotbar[i] = new ItemStack(Material.AIR);

        }

        return hotbar;
    }

    public ItemStack[] getArmor() {

        return armor.getArmor();
    }

    public void setArmor(Armor armor) {

        this.armor = armor;
    }

    public static LoadOut deserialize(Map<String, Object> map) {

        Object objName = map.get("name"), objIcon = map.get("icon"), objArmor = map.get("armor"), objItems = map.get("items"), objPerm = map.get("perm");

        return new LoadOut((String) objName, (Armor) objArmor, (ItemStack) objIcon, (Map<Integer, ItemStack>) objItems, (String) objPerm);
    }

    @Override
    public Map<String, Object> serialize() {

        Map<String, Object> map = new HashMap<>();
        map.put("name", getName());
        map.put("icon", getIcon());
        map.put("armor", armor);
        map.put("items", items);
        map.put("perm", getPermission());

        return map;
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this).append("permission", permission).append("name", name).append("items", items).append("icon", icon)
                                        .append("armor", armor).toString();
    }
}
