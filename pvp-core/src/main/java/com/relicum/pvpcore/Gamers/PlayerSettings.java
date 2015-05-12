package com.relicum.pvpcore.Gamers;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

/**
 * PlayerSettings
 *
 * @author Relicum
 * @version 0.0.1
 */
public class PlayerSettings {

    private float exhaustion;
    private float saturation;
    private double playerHealth;
    private float playerDamage;
    private int foodLevel;
    private int totalExperience;
    private int level;
    private float xp;
    private boolean allowedFlight;
    private boolean isFlying;
    private float flySpeed;
    private float walkSpeed;
    private String displayName;
    private String gameMode;
    private int fireTicks;

    private PlayerSettings() {
    }

    private PlayerSettings(Player player) {

        exhaustion = player.getExhaustion();
        saturation = player.getSaturation();
        playerHealth = player.getHealth();
        foodLevel = player.getFoodLevel();
        totalExperience = player.getTotalExperience();
        level = player.getLevel();
        xp = player.getExp();
        allowedFlight = player.getAllowFlight();
        isFlying = player.isFlying();
        flySpeed = player.getFlySpeed();
        walkSpeed = player.getWalkSpeed();
        gameMode = player.getGameMode().name();
        fireTicks = player.getFireTicks();
        displayName = player.getDisplayName();

    }

    public static PlayerSettings save(Player player) {

        return new PlayerSettings(player);
    }

    public void restore(Player player) {

        player.setExhaustion(exhaustion);
        player.setSaturation(saturation);
        player.setHealth(playerHealth);
        player.setFoodLevel(foodLevel);
        player.setTotalExperience(totalExperience);
        player.setLevel(level);
        player.setExp(xp);
        player.setAllowFlight(allowedFlight);
        player.setFlying(isFlying);
        player.setFlySpeed(flySpeed);
        player.setWalkSpeed(walkSpeed);
        player.setDisplayName(displayName);
        player.setGameMode(GameMode.valueOf(gameMode));
        player.setFireTicks(fireTicks);

    }

}
