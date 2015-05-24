package com.relicum.pvpcore.Utils;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * Name: PermUtil.java Created: 22 May 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class PermUtil {

    private final String ADMIN_ROOT = "duel.admin";
    private final String PLAYER_ROOT = "duel.player";
    private final String KIT_ROOT = "duel.kit";
    private PermissionDefault DEFAULT_PERMISSION = PermissionDefault.OP;
    private TreeMap<String, HashMap<String, Permission>> root = new TreeMap<>();
    private HashMap<String, Permission> admins = new HashMap<>();
    private HashMap<String, Permission> players = new HashMap<>();
    private HashMap<String, Permission> kits = new HashMap<>();
    private Permission ta;
    private Permission pa;
    private Permission ka;
    private List<Permission> admin = new ArrayList<>();
    private List<Permission> player = new ArrayList<>();
    private List<Permission> kit = new ArrayList<>();
    private int count;

    public void registerPermission() {

        this.ta = new Permission("duel.admin", "Root permission node for all admin related permissions", DEFAULT_PERMISSION);
        this.ta.addParent("duel.*", true);

        this.pa = new Permission("duel.player", "Root permission node for all player related permissions", PermissionDefault.TRUE);
        this.pa.addParent("duel.*", true);

        this.ka = new Permission("duel.kit", "Root permission node for all kit related permissions", DEFAULT_PERMISSION);
        this.ka.addParent("duel.*", true);

        root.put(ADMIN_ROOT, admins);
        root.put(PLAYER_ROOT, players);
        root.put(KIT_ROOT, kits);

        Bukkit.getPluginManager().addPermission(ta);
        Bukkit.getPluginManager().addPermission(pa);
        Bukkit.getPluginManager().addPermission(ka);


    }

    public void addAdminPermission(Permission permission) {
        permission.addParent(ta, true);
        admin.add(permission);
    }

    public void addPlayerPermission(Permission permission) {
        permission.addParent(pa, true);
        player.add(permission);
    }

    public void addKitPermission(Permission permission) {
        permission.addParent(ka, true);
        kit.add(permission);
    }

    public boolean registerAll() {


        try {
            for (int i = 0; i < 3; i++) {
                switch (i) {

                    case 0:
                        Bukkit.getPluginManager().addPermission(admin.get(i));
                        break;
                    case 1:
                        Bukkit.getPluginManager().addPermission(player.get(i));
                        break;
                    default:
                        Bukkit.getPluginManager().addPermission(kit.get(i));
                        break;
                }

                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
