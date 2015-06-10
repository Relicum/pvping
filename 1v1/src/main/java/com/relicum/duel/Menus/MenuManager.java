package com.relicum.duel.Menus;

import com.relicum.duel.Duel;
import com.relicum.duel.Events.ItemToggleChange;
import com.relicum.pvpcore.Arenas.PvPZone;
import com.relicum.pvpcore.Arenas.ZoneCollection;
import com.relicum.pvpcore.Enums.ArenaState;
import com.relicum.pvpcore.FormatUtil;
import com.relicum.pvpcore.Menus.AbstractItem;
import com.relicum.pvpcore.Menus.ActionHandler;
import com.relicum.pvpcore.Menus.ActionItem;
import com.relicum.pvpcore.Menus.ActionMenu;
import com.relicum.pvpcore.Menus.ClickAction;
import com.relicum.pvpcore.Menus.Handlers.CloseMenuHandler;
import com.relicum.pvpcore.Menus.MenuAPI;
import com.relicum.pvpcore.Menus.Slot;
import com.relicum.utilities.Items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Name: MenuManager.java Created: 02 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class MenuManager implements Listener {

    private static MenuManager instance;
    private Duel plugin;
    private ZoneMainMenu zoneMainMenu;
    private ZoneSelectMenu zoneSelectMenu;
    private ZoneEditMenu zoneEditMenu;

    public MenuManager(Duel plugin) {

        instance = this;
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public static MenuManager getInstance() {

        return instance;
    }

    public void itemViewStateChange(ItemToggleChange event) {

        ActionMenu menu = event.getMenu();
        ActionItem item = (ActionItem) menu.getMenuItems().get(event.getSlot());

        item.setToggleState(event.getToggleState());

        if (item.getToggleState().equals(AbstractItem.ToggleState.DEFAULT)) {


        }
    }

    public PlayerQueueMenu getInvitesMenu() {

        return new PlayerQueueMenu(FormatUtil.colorize("&6&lPlayer Invitation Menu"), 1);
    }

    public Duel getPlugin() {

        return plugin;
    }

    public ActionMenu getKitModifyMenu(int rows) {

        return MenuAPI.get().createMenu(FormatUtil.colorize("&a&lModify Kits Menu"), toSlot(rows));
    }

    public ActionMenu getHeadMenu() {

        return MenuAPI.get().createMenu(FormatUtil.colorize("&a&lPlayer Heads"), 1);

    }


    public ActionMenu getConfirmMenu(ActionHandler handler) {


        ActionMenu confirm = MenuAPI.get().createMenu(FormatUtil.colorize("&a&lCONFIRMATION NEEDED"), 1);

        for (int i = 0; i < 9; i++) {

            if (i < 4) {
                confirm.addMenuItem(new ActionItem
                                            (new ItemBuilder(Material.STAINED_GLASS_PANE)
                                                     .setDurability((short) 5)
                                                     .setDisplayName("&a&lRight click to confirm")
                                                     .build(), i, ClickAction.CONFIG, handler), i);
            }


            else if (i > 4) {

                confirm.addMenuItem(new ActionItem
                                            (new ItemBuilder(Material.STAINED_GLASS_PANE)
                                                     .setDurability((short) 14)
                                                     .setDisplayName("&4&lRight click to cancel")
                                                     .build(), i, ClickAction.CONFIG, handler), i);

            }
        }

        confirm.setEditing(true);

        return confirm;
    }

    public ActionMenu getKitViewer(String title, PlayerInventory inventory) {

        ActionMenu view = MenuAPI.get().createMenu(FormatUtil.colorize("&6&lKit Preview: &3&l" + title), 6);

        ItemStack[] a = inventory.getArmorContents().clone();
        ItemStack[] c = inventory.getContents().clone();

        System.out.println("con = " + c.length + " and arm = " + a.length);

        int e = 0;

        for (int row = 36; row > 0; row -= 9) {

            for (int col = 9; col > 0; col--) {

                view.addMenuItem(new ActionItem(c[row - col], e, ClickAction.NO_ACTION), e);

                e++;
            }
        }
        int d = 0;
        for (int i = 36; i < 54; i++) {

            if (i < 45) {


            }

            if (i > 44 && i < 49) {

                view.addMenuItem(new ActionItem(a[d], i, ClickAction.NO_ACTION), i);
                d++;
            }
            else {
                view.addMenuItem(new ActionItem(new ItemStack(Material.AIR), i, ClickAction.NO_ACTION), i);
            }

        }

        view.setModifiable(true);


        return view;

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
                                                                  "&aNumber of Zones: &6" + plugin.getZoneManager().getCollectionSize(cname)
                                                    )
                                      ).build();

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

    /**
     * Get new {@link DuelSettingsMenu}
     *
     * @return the {@link DuelSettingsMenu}
     */
    public DuelSettingsMenu getDuelSettings() {

        return new DuelSettingsMenu(FormatUtil.colorize("&6&l1v1 Settings Menu"), 4, Duel.get().getConfigs());
    }

    public ZoneSelectMenu createSelectMenu(String zone) {

        System.out.println(zone);

        ZoneCollection zc = plugin.getZoneManager().getAllInCollection(ChatColor.stripColor(zone));
        int icons = zc.getTotalZones();
        System.out.println("Number in the zone is " + zc.getTotalZones());
        zoneSelectMenu = new ZoneSelectMenu(FormatUtil.colorize("&4&lZone Edit Selector"), 1);

        int c = 0;
        for (Map.Entry<String, PvPZone> entry : zc.getZones()) {
            List<String> tl = entry.getValue().getLore().stream().map(FormatUtil::colorize).collect(Collectors.toList());

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
        }
        else {
            zoneEditMenu.setZone(zone);
            zoneEditMenu.setEditing(true);
        }

        return zoneEditMenu;
    }

    @Nullable
    public ActionMenu getStateMenu(ArenaState current, String coll, String zone, ActionMenu parent) {

        ActionMenu sta = new ActionMenu(FormatUtil.colorize("&6&l" + zone + " state menu"), Slot.ONE.ordinal(), parent);

        for (ArenaState state : ArenaState.values()) {
            if (current == state) {

            }
        }

        return null;
    }

    public int toSlot(int num) {

        return (fit(num) / 9);
    }

    public int fit(int slots) {

        if (slots < 10) {
            return 9;
        }
        if (slots < 19) {
            return 18;
        }
        if (slots < 28) {
            return 27;
        }
        if (slots < 37) {
            return 36;
        }
        if (slots < 46) {
            return 45;
        }

        return 54;

    }
}
