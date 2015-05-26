package com.relicum.duel.Handlers;

import com.relicum.pvpcore.Enums.PlayerState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * LobbyGameLink is a simple idea to allow other objects run methods directly in another object.
 * <p>This is currently purely an idea and is very much WIP
 *
 * @author Relicum
 * @version 0.0.1
 */
public abstract class LobbyGameLink {

    private GameHandler host;

    private Inner inner;
    private List<Object> visitors;
    private boolean active = false;

    public LobbyGameLink(GameHandler host) {
        this.host = host;
        this.visitors = new ArrayList<>();
    }

    public Inner getInner() {
        return inner;
    }

    @Nullable
    private Inner grantAccess(Object obj) {

        if (obj instanceof LobbyHandler) {
            visitors.add(obj);
            active = true;


            inner = new Inner(obj) {
                @Override
                public PlayerState getPlayerState(String uuid) {

                    return LobbyGameLink.this.getPlayerState(uuid);

                }

                @Override
                public void setPlayerState(String uuid, PlayerState state) {
                    LobbyGameLink.this.setPlayerState(uuid, state);
                }
            };

            return inner;
        }
        return null;
    }

    @Nullable
    public Inner requestAccess(Object obj) {

        if (!(obj instanceof LobbyHandler)) {
            return null;
        }

        if (visitors.contains(obj)) {
            return null;
        }

        else {
            return grantAccess(obj);
        }

    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean a) {
        if (!a) {
            inner.close();
            System.out.println("LobbyGameLink Closed");
        }
        active = a;
    }

    private LobbyGameLink getInstance() {
        return this;
    }


    protected abstract PlayerState getPlayerState(String uuid);

    protected abstract void setPlayerState(String uuid, PlayerState state);


    protected abstract class Inner {

        private Object handler;


        public Inner(Object handler) {
            active = true;
        }

        private LobbyHandler getHandler() {
            return (LobbyHandler) handler;
        }

        private void close() {
            visitors.clear();
            visitors = null;
            handler = null;

        }

        public abstract PlayerState getPlayerState(String uuid);

        public abstract void setPlayerState(String uuid, PlayerState state);


    }
}
