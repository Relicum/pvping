package com.relicum.pvpcore.Kits;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
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
    private ItemStack[] armor;
    private ItemStack[] contents;
    private ItemStack icon = new ItemStack(Material.AIR);
    private String permission = "duel.player.loadout";

    public LoadOut(String name) {

        this.name = name;
    }

    public LoadOut(String name, ItemStack[] armor, ItemStack[] content, ItemStack icon, String perm) {

        this.name = name;
        this.armor = new ItemStack[4];
        this.armor = armor;
        this.icon = icon;

        this.contents = new ItemStack[36];
        for (int i = 0; i < 36; i++) {
            this.contents[i] = new ItemStack(Material.AIR);
        }

        for (int i = 0; i < content.length; i++) {
            this.contents[i] = content[i];
        }


        this.permission = perm;
    }

    public String getName() {

        return name;
    }

    public void setContents(ItemStack[] content) {
        this.contents = new ItemStack[content.length];
        this.contents = content;
    }

    public ItemStack[] getContents() {
        return contents;
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


    public ItemStack[] getArmor() {

        return armor;
    }

    public void setArmor(Armor armor) {

        this.armor = armor.getArmor();
    }

    public static LoadOut deserialize(Map<String, Object> map) {

        Object objName = map.get("name"), objIcon = map.get("icon"), objArmor = map.get("armor"), objContents = map.get("contents"), objPerm = map.get("perm");

        List<ItemStack> a = (List<ItemStack>) objArmor;
        a.toArray(new ItemStack[4]);
        List<ItemStack> c = (List<ItemStack>) objContents;
        c.toArray(new ItemStack[c.size()]);

        return new LoadOut((String) objName, a.toArray(new ItemStack[4]), c.toArray(new ItemStack[c.size()]), (ItemStack) objIcon, (String) objPerm);
    }

    @Override
    public Map<String, Object> serialize() {

        Map<String, Object> map = new HashMap<>();
        map.put("name", getName());
        map.put("icon", getIcon());
        map.put("armor", getArmor());
        map.put("contents", getContents());
        map.put("perm", getPermission());

        return map;
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this).append("permission", permission).append("name", name).append("contents", contents).append("icon", icon)
                .append("armor", armor).toString();
    }
}
