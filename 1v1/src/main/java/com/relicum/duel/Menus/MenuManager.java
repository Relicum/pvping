package com.relicum.duel.Menus;

import com.relicum.duel.Duel;
import com.relicum.pvpcore.Arenas.PvPZone;
import com.relicum.pvpcore.Arenas.ZoneCollection;
import com.relicum.pvpcore.FormatUtil;
import com.relicum.pvpcore.Menus.*;
import com.relicum.pvpcore.Menus.Handlers.CloseMenuHandler;
import com.relicum.pvpcore.Menus.Handlers.TeleportHandler;
import com.relicum.utilities.Items.ItemBuilder;
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
        for (String cname : cnames)
        {
            ItemStack stack = new ItemBuilder(Material.PAPER, 1)
                    .setDisplayName("&6&l" + cname)
                    .setItemLores(
                        Arrays.asList(" ", "&aRight click to open this", "&aZoneCollection to view and edit " + "zones", " " + "", "&aNumber of Zones: &6999"))
                    .build();

            // ItemStack stack = new ItemStack(Material.PAPER, 1);
            // ItemMeta meta = stack.getItemMeta();
            // meta.setDisplayName(FormatUtil.colorize("&6&l" + cname));
            // meta.setLore(Arrays.asList(" ", "&aRight click to open this",
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
        for (Map.Entry<String, PvPZone> entry : zc.getZones())
        {
            List<String> tl = new ArrayList<>();

            for (String s : entry.getValue().getLore())
            {
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

    public void buildEditMenu() {

        int rows = 1;

        zoneEditMenu = new ZoneEditMenu("Default Title", rows);

        SpawnPointItem sp1 = new SpawnPointItem(new ItemBuilder(Material.INK_SACK).setDisplayName("&5Spawn 1").setDurability((short) 10)
                .setItemLores(Arrays.asList(" ", "&aSet Spawn 1")).build(), new ItemBuilder(Material.INK_SACK).setDurability((short) 5)
                .setDisplayName("&5Spawn 1").setItemLores(Arrays.asList(" ", "&aSet Spawn 1")).build(), 1, ClickAction.CONFIG, new CloseMenuHandler());

        SpawnPointItem sp2 = new SpawnPointItem(new ItemBuilder(Material.INK_SACK).setDisplayName("&5Spawn 2").setDurability((short) 5)
                .setItemLores(Arrays.asList(" ", "&aSet Spawn 2 true")).build(), new ItemBuilder(Material.INK_SACK).setDurability((short) 10)
                .setDisplayName("&5Spawn 2").setItemLores(Arrays.asList(" ", "&aSet Spawn 2 false")).build(), 1, ClickAction.CONFIG, new CloseMenuHandler());

        AbstractItem tp = new ActionItem(new ItemStack(Material.DIAMOND_SWORD), 1, ClickAction.TELEPORT, new TeleportHandler(Bukkit.getWorld("world")
                .getSpawnLocation()) {

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

        ((TeleportHandler) tp.getActionHandler().getExecutor()).setLocation(Bukkit.getWorld("world").getSpawnLocation());

        AbstractItem cm = new ActionItem(new ItemStack(Material.GLASS), 1, ClickAction.CLOSE_MENU, new CloseMenuHandler());

        zoneEditMenu.addMenuItem(tp, 1);
        zoneEditMenu.addMenuItem(sp1, 2);
        zoneEditMenu.addMenuItem(sp2, 3);
        zoneEditMenu.addMenuItem(cm, ((rows * 9) - 1));

    }

    public ZoneEditMenu getZoneEditMenu(PvPZone zone) {

        if (zoneEditMenu == null)
            buildEditMenu();

        zoneEditMenu.setZone(zone);

        return zoneEditMenu;
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
