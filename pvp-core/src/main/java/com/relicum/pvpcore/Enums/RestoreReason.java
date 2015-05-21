package com.relicum.pvpcore.Enums;

/**
 * RestoreReason reason why a players original inventory and settings will be restored.
 * <p>Void is only a value to return if it has not currently been set, saves returning null.
 *
 * @author Relicum
 * @version 0.0.1
 */
public enum RestoreReason {

    LEAVE_CMD, KICK, BANNED, LOGGED, VOID
}
