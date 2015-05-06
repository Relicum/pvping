package com.relicum.duel.Menus;

import com.relicum.duel.Duel;
import com.relicum.pvpcore.Arenas.PvPZone;
import com.relicum.pvpcore.Arenas.ZoneCollection;
import com.relicum.pvpcore.FormatUtil;
import com.relicum.pvpcore.Menus.*;
import com.relicum.pvpcore.Menus.Handlers.CloseMenuHandler;
import com.relicum.pvpcore.Menus.Handlers.TeleportHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
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

            CollectionItem item = new CollectionItem("&6&l" + cname, new ItemStack(Material.PAPER), c);
            List<String> lore = Arrays.asList(" ", "&aRight click to open this", "&aZoneCollection to view and edit zones", " ", "&aNumber of Zones: &6999");
            item.setDescriptions(lore);
            zoneMainMenu.addMenuItem(item, c);
            c++;
        }

        zoneMainMenu.setExitOnClickOutside(true);
        zoneMainMenu.addMenuItem(new CloseMenuItem(), 8);

        return zoneMainMenu;
    }

    public ZoneSelectMenu createSelectMenu(String zone) {
        System.out.println(zone);

        ZoneCollection zc = plugin.getZoneManager().getAllInCollection(ChatColor.stripColor(zone));
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
            meta.setDisplayName("&6&l" + entry.getKey());
            meta.setLore(tl);
            stack.setItemMeta(meta);

            OpenEditZoneItem item = new OpenEditZoneItem("&6&l" + entry.getKey(), stack, c, tl, entry.getValue());
            zoneSelectMenu.addMenuItem(item, c);
            c++;

        }
        zoneSelectMenu.setExitOnClickOutside(true);
        zoneSelectMenu.addMenuItem(new CloseMenuItem(), 8);
        System.out.println("Totsl in menu is " + zoneSelectMenu.getMenuItems().size());
        return zoneSelectMenu;
    }

    public void buildEditMenu() {
        int rows = 1;

        zoneEditMenu = new ZoneEditMenu("Default Title", rows);

        AbstractItem<TeleportHandler> tp = new ActionItem<>(new ItemStack(Material.DIAMOND_SWORD), 1, ClickAction.TELEPORT, new TeleportHandler() {

            @Override
            public ActionResponse perform(Player player, AbstractItem icon) {

                ActionResponse response = new ActionResponse(icon);
                response.setPlayer(player);
                response.setWillClose(true);
                player.playSound(player.getLocation(), Sound.FIREWORK_LARGE_BLAST2, 10.0f, 1.0f);
                player.teleport(getLocation());
                return response;
            }
        });

        tp.getActionHandler().getExecutor().setLocation(Bukkit.getWorld("world").getSpawnLocation());

        ActionItem<CloseMenuHandler> cm = new ActionItem<>(new ItemStack(Material.GLASS), 1, ClickAction.CLOSE_MENU, new CloseMenuHandler());

        zoneEditMenu.addMenuItem(tp, 0);
        zoneEditMenu.addMenuItem(cm, ((rows * 9) - 1));

    }

    public ZoneEditMenu getZoneEditMenu(PvPZone zone) {

        if (zoneEditMenu == null)
            buildEditMenu();

        zoneEditMenu.setZone(zone);

        return zoneEditMenu;
    }
}
