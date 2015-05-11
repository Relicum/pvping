package com.relicum.duel.Objects;

import org.bukkit.Color;

/**
 * RankArmor maps ranks to color
 *
 * @author Relicum
 * @version 0.0.1
 */
public enum RankArmor {

    OWNER(0xFFFF00),
    DEV(16711680),
    VIP(0xFF0000),
    YOUTUBE(0x800080),
    HELPER(0xC0C0C0),
    EMERALD(0x00FF00),
    DIAMOND(0x00FFFF),
    GOLD(0x808000),
    IRON(0xC0C0C0),
    MEMBER(0x808080);


    private int color;

    RankArmor(int theColor) {
        this.color = theColor;
    }

    public Color getColor() {

        return Color.fromRGB(color);
    }
}
