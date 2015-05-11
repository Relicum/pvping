package com.relicum.pvpcore.Gamers;

import com.google.common.base.Objects;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

/**
 * PlayerGameSettings
 *
 * @author Relicum
 * @version 0.0.1
 */
public class PlayerGameSettings {

    protected float exhaustion = 0.2f;
    protected float saturation = 5.0f;
    protected double playerHealth = 20.0d;
    protected int foodLevel = 20;
    protected int totalExperience = 0;
    protected int level = 0;
    protected float xp = 0.0f;
    protected boolean allowedFlight = false;
    protected boolean isFlying = false;
    protected float flySpeed = 0.1f;
    protected float walkSpeed = 0.2f;
    protected String displayName = "";
    protected String gameMode = "SURVIVAL";
    protected int fireTicks = 0;

    private PlayerGameSettings() {
    }

    public PlayerGameSettings(boolean allowedFlight, String displayName, float exhaustion, int fireTicks, float flySpeed, int foodLevel, String gameMode, boolean isFlying, int level, double playerHealth, float saturation, int totalExperience, float walkSpeed, float xp) {
        this.allowedFlight = allowedFlight;
        this.displayName = displayName;
        this.exhaustion = exhaustion;
        this.fireTicks = fireTicks;
        this.flySpeed = flySpeed;
        this.foodLevel = foodLevel;
        this.gameMode = gameMode;
        this.isFlying = isFlying;
        this.level = level;
        this.playerHealth = playerHealth;
        this.saturation = saturation;
        this.totalExperience = totalExperience;
        this.walkSpeed = walkSpeed;
        this.xp = xp;
    }


    /**
     * Gets xp.
     *
     * @return Value of xp.
     */
    public float getXp() {
        return xp;
    }

    /**
     * Gets level.
     *
     * @return Value of level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets playerHealth.
     *
     * @return Value of playerHealth.
     */
    public double getPlayerHealth() {
        return playerHealth;
    }

    /**
     * Gets flySpeed.
     *
     * @return Value of flySpeed.
     */
    public float getFlySpeed() {
        return flySpeed;
    }

    /**
     * Gets totalExperience.
     *
     * @return Value of totalExperience.
     */
    public int getTotalExperience() {
        return totalExperience;
    }

    /**
     * Gets walkSpeed.
     *
     * @return Value of walkSpeed.
     */
    public float getWalkSpeed() {
        return walkSpeed;
    }

    /**
     * Gets allowedFlight.
     *
     * @return Value of allowedFlight.
     */
    public boolean isAllowedFlight() {
        return allowedFlight;
    }

    /**
     * Gets exhaustion.
     *
     * @return Value of exhaustion.
     */
    public float getExhaustion() {
        return exhaustion;
    }

    /**
     * Gets gameMode.
     *
     * @return Value of gameMode.
     */
    public GameMode getGameMode() {
        return GameMode.valueOf(gameMode);
    }

    /**
     * Gets foodLevel.
     *
     * @return Value of foodLevel.
     */
    public int getFoodLevel() {
        return foodLevel;
    }

    /**
     * Gets isFlying.
     *
     * @return Value of isFlying.
     */
    public boolean isIsFlying() {
        return isFlying;
    }

    /**
     * Gets displayName.
     *
     * @return Value of displayName.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets saturation.
     *
     * @return Value of saturation.
     */
    public float getSaturation() {
        return saturation;
    }

    /**
     * Gets fireTicks.
     *
     * @return Value of fireTicks.
     */
    public int getFireTicks() {
        return fireTicks;
    }

    public void apply(Player player) {

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
        // player.setDisplayName(displayName);
        player.setGameMode(GameMode.valueOf(gameMode));
        player.setFireTicks(fireTicks);

    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("allowedFlight", allowedFlight)
                .add("exhaustion", exhaustion)
                .add("saturation", saturation)
                .add("playerHealth", playerHealth)
                .add("foodLevel", foodLevel)
                .add("totalExperience", totalExperience)
                .add("level", level)
                .add("xp", xp)
                .add("isFlying", isFlying)
                .add("flySpeed", flySpeed)
                .add("walkSpeed", walkSpeed)
                .add("displayName", displayName)
                .add("gameMode", gameMode)
                .add("fireTicks", fireTicks)
                .toString();
    }
}
