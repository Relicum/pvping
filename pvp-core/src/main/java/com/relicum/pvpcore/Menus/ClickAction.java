package com.relicum.pvpcore.Menus;

import com.relicum.pvpcore.Menus.Handlers.CloseMenuHandler;
import com.relicum.pvpcore.Menus.Handlers.TeleportHandler;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * ClickAction holds all the different actions that can be performed resulting
 * from a {@link org.bukkit.event.inventory.InventoryClickEvent}
 * <p>
 * Each action stored has the relevant {@link Class} that will handle the
 * ItemClick
 *
 * @author Relicum
 * @version 0.0.1
 */
public enum ClickAction {
    BUNGEE(String.class), HEADS(String.class), TELEPORT(TeleportHandler.class), COMMAND(String.class), COMMANDS(String.class), CONFIG(String.class), VALIDATION(String.class), CLOSE_MENU(CloseMenuHandler.class), SWITCH_MENU(String.class), OPEN_MENU(String.class), INFO(String.class), NEXT_PREVIOUS(String.class), SAVE(String.class), UPDATE(String.class), NO_ACTION(String.class);

    private Class clazz;

    private final static Map<Class, ClickAction> actions = new HashMap<>();

    static {
        for (ClickAction click_actions : EnumSet.allOf(ClickAction.class)) {
            actions.put(click_actions.getActionClass(), click_actions);
        }
    }

    ClickAction(Class paramClazz) {

        this.clazz = paramClazz;
    }

    /**
     * Gets the {@link ClickAction} {@link Class}.
     *
     * @return the {@link Class}
     */
    public Class getActionClass() {

        return clazz;
    }

    /**
     * Checks to see if both {@link ActionHandler} and {@link ClickAction} are
     * of the same type.
     *
     * @param paramHandler the instance of {@link ActionHandler} to test.
     * @param paramACTION the {@link ClickAction} to see if paramHandler is of
     *        this type.
     * @return true if they are the same type, false if they aren't
     */
    public static boolean isType(ActionHandler paramHandler, ClickAction paramACTION) {

        return paramACTION.getActionClass().isInstance(paramHandler);
    }

    /**
     * Gets the action {@link ClickAction} for the {@link ActionHandler} being
     * passed in.
     *
     * @param paramHandler the instance of {@link ActionHandler} that you want
     *        the action for.
     * @return the corresponding action {@link ClickAction}
     */
    public static ClickAction getType(ActionHandler<?> paramHandler) {

        for (Map.Entry<Class, ClickAction> entry : actions.entrySet()) {
            if (entry.getValue().getActionClass().isInstance(paramHandler))
                return entry.getValue();
        }

        return null;
    }
}
