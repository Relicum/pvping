package com.relicum.duel.Menus;

import com.relicum.duel.Duel;
import com.relicum.pvpcore.Arenas.PvPZone;
import com.relicum.pvpcore.Arenas.ZoneCollection;
import com.relicum.pvpcore.FormatUtil;
import com.relicum.pvpcore.Menus.CloseMenuItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
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

    private Duel plugin;
    private ZoneMainMenu zoneMainMenu;
    private ZoneSelectMenu zoneSelectMenu;

    public MenuManager(Duel plugin) {
        this.plugin = plugin;
    }

    public Duel getPlugin() {
        return plugin;
    }

    public ZoneMainMenu createZoneEdit() {
        zoneMainMenu = new ZoneMainMenu(FormatUtil.colorize("&4Zone Collection Menu"), 1);

        List<String> cnames = plugin.getZoneManager().getCollectionNames();
        int c = 0;
        for (String cname : cnames) {

            CollectionItem item = new CollectionItem("&6&l" + cname, new ItemStack(Material.PAPER), c);
            List<String> lore = Arrays.asList(" ", "&aRight click to open this", "&aZoneCollection to view and edit zones", " ", "&aNumber of Zones: &6999");
            item.setDescriptions(lore);
            zoneMainMenu.addMenuItem(item, c);
            c++;
        }

        zoneMainMenu.addMenuItem(new CloseMenuItem(), 8);

        return zoneMainMenu;
    }

    public ZoneSelectMenu createSelectMenu() {
        List<String> cnames = plugin.getZoneManager().getCollectionNames();

        zoneSelectMenu = new ZoneSelectMenu(FormatUtil.colorize("&4&lZone Edit Selector"), 1);

        for (String cname : cnames) {
            ZoneCollection zc = plugin.getZoneManager().getAllInCollection(cname);
            int c = 0;
            for (Map.Entry<String, PvPZone> entry : zc.getZones()) {
                zoneSelectMenu.addMenuItem(entry.getValue().getOpenMenuItem(c), c);
                c++;
            }

        }
        zoneSelectMenu.addMenuItem(new CloseMenuItem(), 8);
        return zoneSelectMenu;
    }

}
