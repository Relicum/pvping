package com.relicum.pvpcore.Menus;

import org.bukkit.entity.Player;

/**
 * ActionResponse
 *
 * @author Relicum
 * @version 0.0.1
 */
public class ActionResponse {

    private Player player;
    private AbstractItem icon;
    private boolean nothing = true;
    private boolean goBack = false;
    private boolean close = false;
    private boolean update = false;
    private boolean voidIcon = false;
    private boolean destroy = false;

    public ActionResponse(AbstractItem icon) {

        this.icon = icon;
    }

    public ActionResponse(AbstractItem icon, Player player) {

        this.icon = icon;
        this.player = player;
    }

    public boolean isClose() {

        return close;
    }

    public boolean isDestroy() {

        return destroy;
    }

    public boolean isGoBack() {

        return goBack;
    }

    public AbstractItem getIcon() {

        return icon;
    }

    public boolean isNothing() {

        return nothing;
    }

    public Player getPlayer() {

        return player;
    }

    public boolean isUpdate() {

        return update;
    }

    public boolean isVoidIcon() {

        return voidIcon;
    }

    public void setPlayer(Player player) {

        this.player = player;
    }

    public void setIcon(ActionItem icon) {

        this.icon = icon;
    }

    public void setWillClose(boolean close) {

        this.close = close;
        if (close)
        {
            this.goBack = false;
            this.update = false;
        }
    }

    public void setWillDestroy(boolean destroy) {

        this.destroy = destroy;
    }

    public void setWillGoBack(boolean goBack) {

        this.goBack = goBack;
        if (goBack)
        {
            this.close = false;
            this.update = false;
        }
    }

    public void setDoNothing(boolean nothing) {

        this.nothing = nothing;
    }

    public void setWillUpdate(boolean update) {

        this.update = update;
        if (update)
        {
            this.goBack = false;
            this.close = false;
        }
    }

    public void setIsVoidIcon(boolean voidIcon) {

        this.voidIcon = voidIcon;
    }
}
