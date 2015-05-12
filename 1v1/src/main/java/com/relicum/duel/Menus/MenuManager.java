package com.relicum.duel.Menus;

import com.relicum.duel.Duel;
import com.relicum.pvpcore.Arenas.PvPZone;
import com.relicum.pvpcore.Arenas.ZoneCollection;
import com.relicum.pvpcore.Enums.ArenaState;
import com.relicum.pvpcore.FormatUtil;
import com.relicum.pvpcore.Menus.*;
import com.relicum.pvpcore.Menus.Handlers.CloseMenuHandler;
import com.relicum.utilities.Items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Name: MenuManager.java Created: 02 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class MenuManager {

    private static MenuManager instance;
    private Duel plugin;
    private ZoneMainMenu zoneMainMenu;
    private ZoneSelectMenu zoneSelectMenu;
    private ZoneEditMenu zoneEditMenu;

    public MenuManager(Duel plugin) {

        instance = this;
        this.plugin = plugin;
    }

    public static MenuManager getInstance() {

        return instance;
    }

    public Duel getPlugin() {

        return plugin;
    }

    public ZoneMainMenu createZoneEdit() {

        zoneMainMenu = new ZoneMainMenu(FormatUtil.colorize("&4Zone Collection Menu"), 1);

        List<String> cnames = plugin.getZoneManager().getCollectionNames();
        int c = 0;
        for (String cname : cnames) {
            ItemStack stack = new ItemBuilder(Material.PAPER, 1).setDisplayName("&6&l" + cname)
                    .setItemLores(Arrays.asList(" ",
                            "&aRight click to open this",
                            "&aZoneCollection to view and edit " + "zones",
                            " " + "",
                            "&aNumber of Zones: &6" + plugin.getZoneManager().getCollectionSize(cname))).build();

            // ItemStack stack = new ItemStack(Material.PAPER, 1);
            // ItemMeta meta = stack.getItemMeta();
            // meta.setDisplayName(FormatUtil.colorize("&6&l" +
            // cname));
            // meta.setLore(Arrays.asList(" ",
            // "&aRight click to open this",
            // "&aZoneCollection to view and edit zones", " ",
            // "&aNumber of Zones: &6999"));
            // stack.setItemMeta(meta);

            AbstractItem item = new ActionItem(stack, c, ClickAction.SWITCH_MENU, new CollectionItem(cname));
            item.setText(FormatUtil.colorize("&6&l" + cname));
            zoneMainMenu.addMenuItem(item, c);
            c++;
        }
        AbstractItem cm = new ActionItem(new ItemStack(Material.GLASS), 1, ClickAction.CLOSE_MENU, new CloseMenuHandler());
        zoneMainMenu.setExitOnClickOutside(true);
        zoneMainMenu.addMenuItem(cm, 8);

        return zoneMainMenu;
    }

    public ZoneSelectMenu createSelectMenu(String zone) {

        System.out.println(zone);

        ZoneCollection zc = plugin.getZoneManager().getAllInCollection(ChatColor.stripColor(zone));
        int icons = zc.getTotalZones();
        System.out.println("Number in the zone is " + zc.getTotalZones());
        zoneSelectMenu = new ZoneSelectMenu(FormatUtil.colorize("&4&lZone Edit Selector"), 1);

        int c = 0;
        for (Map.Entry<String, PvPZone> entry : zc.getZones()) {
            List<String> tl = new ArrayList<>();

            for (String s : entry.getValue().getLore()) {
                tl.add(FormatUtil.colorize(s));
            }

            ItemStack stack = new ItemStack(Material.PAPER, 1);
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(FormatUtil.colorize("&6&l" + entry.getKey()));
            meta.setLore(tl);
            stack.setItemMeta(meta);
            AbstractItem it = new ActionItem(stack, 1, ClickAction.SWITCH_MENU, new OpenEditZoneItem(entry.getValue()));

            zoneSelectMenu.addMenuItem(it, c);
            c++;

        }
        AbstractItem cm = new ActionItem(new ItemStack(Material.GLASS), 1, ClickAction.CLOSE_MENU, new CloseMenuHandler());

        zoneSelectMenu.setExitOnClickOutside(true);
        zoneSelectMenu.addMenuItem(cm, (fit(icons) - 1));
        System.out.println("Total in menu is " + zoneSelectMenu.getMenuItems().size());
        return zoneSelectMenu;
    }

    public ZoneEditMenu getZoneEditMenu(String coll, String zone) {

        return getZoneEditMenu(plugin.getZoneManager().getPvpZone(coll, zone));
    }

    public ZoneEditMenu getZoneEditMenu(PvPZone zone) {

        if (zoneEditMenu == null) {
            zoneEditMenu = new ZoneEditMenu(zone, 3);
            zoneEditMenu.setEditing(true);
        } else {
            zoneEditMenu.setZone(zone);
            zoneEditMenu.setEditing(true);
        }

        return zoneEditMenu;
    }

    public ActionMenu getStateMenu(ArenaState current, String coll, String zone, ActionMenu parent) {
        ActionMenu sta = new ActionMenu(FormatUtil.colorize("&6&l" + zone + " state menu"), Slot.ONE.ordinal(), parent);

        for (ArenaState state : ArenaState.values()) {
            if (current == state) {

            }
        }

        return null;
    }

    public int fit(int slots) {

        if (slots < 10)
            return 9;
        if (slots < 19)
            return 18;
        if (slots < 28)
            return 27;
        if (slots < 37)
            return 36;
        if (slots < 46)
            return 45;

        return 54;

    }
}