package com.relicum.pvpcore.Gamers;

public class PlayerGameSettingsBuilder {
    private boolean allowedFlight = false;
    private String displayName = "";
    private float exhaustion = 0.2f;
    private int fireTicks = -20;
    private float flySpeed = 0.1f;
    private int foodLevel = 20;
    private String gameMode = "SURVIVAL";
    private boolean isFlying = false;
    private int level = 0;
    private double playerHealth = 20.0;
    private float saturation = 5.0f;
    private int totalExperience = 0;
    private float walkSpeed = 0.2f;
    private float xp = 0.0f;

    public static PlayerGameSettingsBuilder builder() {

        return new PlayerGameSettingsBuilder();
    }

    public PlayerGameSettingsBuilder setAllowedFlight(boolean allowedFlight) {

        this.allowedFlight = allowedFlight;
        return this;
    }

    public PlayerGameSettingsBuilder setDisplayName(String displayName) {

        this.displayName = displayName;
        return this;
    }

    public PlayerGameSettingsBuilder setExhaustion(float exhaustion) {

        this.exhaustion = exhaustion;
        return this;
    }

    public PlayerGameSettingsBuilder setFireTicks(int fireTicks) {

        this.fireTicks = fireTicks;
        return this;
    }

    public PlayerGameSettingsBuilder setFlySpeed(float flySpeed) {

        this.flySpeed = flySpeed;
        return this;
    }

    public PlayerGameSettingsBuilder setFoodLevel(int foodLevel) {

        this.foodLevel = foodLevel;
        return this;
    }

    public PlayerGameSettingsBuilder setGameMode(String gameMode) {

        this.gameMode = gameMode;
        return this;
    }

    public PlayerGameSettingsBuilder setIsFlying(boolean isFlying) {

        this.isFlying = isFlying;
        return this;
    }

    public PlayerGameSettingsBuilder setLevel(int level) {

        this.level = level;
        return this;
    }

    public PlayerGameSettingsBuilder setPlayerHealth(double playerHealth) {

        this.playerHealth = playerHealth;
        return this;
    }

    public PlayerGameSettingsBuilder setSaturation(float saturation) {

        this.saturation = saturation;
        return this;
    }

    public PlayerGameSettingsBuilder setTotalExperience(int totalExperience) {

        this.totalExperience = totalExperience;
        return this;
    }

    public PlayerGameSettingsBuilder setWalkSpeed(float walkSpeed) {

        this.walkSpeed = walkSpeed;
        return this;
    }

    public PlayerGameSettingsBuilder setXp(float xp) {

        this.xp = xp;
        return this;
    }

    public PlayerGameSettings build() {

        return new PlayerGameSettings(allowedFlight, displayName, exhaustion, fireTicks, flySpeed, foodLevel, gameMode, isFlying, level, playerHealth,
                                      saturation, totalExperience, walkSpeed, xp);
    }
}
