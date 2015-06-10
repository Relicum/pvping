package com.relicum.duel.Handlers;

import com.relicum.duel.Duel;
import com.relicum.duel.Menus.PlayerQueueMenu;
import com.relicum.duel.Objects.GameInvite;
import com.relicum.duel.Objects.PvpGame;
import com.relicum.duel.Objects.PvpPlayer;
import com.relicum.pvpcore.FormatUtil;
import com.relicum.pvpcore.Menus.AbstractItem;
import com.relicum.pvpcore.Menus.ActionHandler;
import com.relicum.pvpcore.Menus.ActionResponse;
import com.relicum.pvpcore.Menus.ClickAction;
import com.relicum.duel.Menus.PlayerHeadItem;
import com.relicum.utilities.Items.MLore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Name: SkullHandler.java Created: 28 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class SkullHandler implements Listener {

    private Duel plugin;

    private Map<String, SkullMeta> skullMetaMap;
    private Map<String, PlayerHeadItem> headItemMap;


    public SkullHandler(Duel plugin) {
        this.plugin = plugin;
        this.skullMetaMap = new HashMap<>();
        this.headItemMap = new HashMap<>();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    public SkullMeta makeSkullMeta(String name, String kit) {

        MLore mLore = new MLore("  \n")
                              .then("&61V1 Invite")
                              .blankLine()
                              .then("&aFrom: &e" + name)
                              .newLine()
                              .then("&aKit: &e" + kit)
                              .blankLine()
                              .then("&bRight Click to accept the 1v1")
                              .newLine()
                              .then("&3Left Click to decline the 1v1");

        String display = "&a&l" + name;

        SkullMeta m1 = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
        m1.setOwner(name);
        m1.setDisplayName(FormatUtil.colorize(display));
        m1.setLore(mLore.toLore());

        return m1;
    }

    @EventHandler
    public void onSkullJoin(PlayerJoinEvent event) {

        final String theName = event.getPlayer().getName();
        final String theUuid = event.getPlayer().getUniqueId().toString();

        if (!skullMetaMap.containsKey(theName)) {
            new BukkitRunnable() {
                @Override
                public void run() {


                    transfer(theName, theUuid);


                }
            }.runTaskLater(plugin, 60l);
        }
    }


    public void transfer(String name, String uuid) {

        skullMetaMap.put(name, makeSkullMeta(name, "Default"));
        headItemMap.put(name, new PlayerHeadItem(0, ClickAction.NO_ACTION, skullMetaMap.get(name).clone(), uuid));
    }

    public PlayerHeadItem getHeadItem(String name, int slot, String uuid) {

        return new PlayerHeadItem(slot, ClickAction.NO_ACTION, new ActionHandler() {
            @Override
            public ActionHandler getExecutor() {
                return this;
            }

            @Override
            public ActionResponse perform(Player player, AbstractItem icon, InventoryClickEvent event) {

                PlayerHeadItem item = (PlayerHeadItem) icon;
                GameInvite invite = item.getInvite();
                PlayerQueueMenu menu = (PlayerQueueMenu) item.getMenu();
                ActionResponse response = new ActionResponse(icon, player);

                if (!invite.isActive()) {

                    menu.removeMenuItem(item.getIndex());
                    menu.getInventory().clear(item.getIndex());
                    response.setWillUpdate(true);
                    return response;
                }

                if (player.getUniqueId().toString().equals(invite.getInviteeId())) {

                    //Check other player is online
                    Player player1 = Bukkit.getPlayer(UUID.fromString(invite.getInviterId()));

                    if (player1.isOnline()) {

                        PvpPlayer inviter = plugin.getGameHandler().getPvpPlayer(invite.getInviterId());
                        PvpPlayer invitee = plugin.getGameHandler().getPvpPlayer(invite.getInviteeId());

                        PvpGame game = new PvpGame(plugin,
                                                   plugin.getZoneManager().getPvpZone("highlife", "highlife-0"),
                                                   plugin.getKitHandler().getKit(invite.getUuid()));
                        game.addPlayer(inviter);
                        game.addPlayer(invitee);


                    }

                }

                response.setWillClose(true);
                return response;
            }
        }, skullMetaMap.get(name).clone(), uuid);

    }

    public void clear() {

        skullMetaMap.clear();
        headItemMap.clear();
    }
}
