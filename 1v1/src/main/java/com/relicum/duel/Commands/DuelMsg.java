package com.relicum.duel.Commands;

import com.relicum.commands.Interfaces.Msg;
import com.relicum.duel.Duel;

/**
 * DuelMsg
 *
 * @author Relicum
 * @version 0.0.1
 */
public final class DuelMsg implements Msg {

    private static DuelMsg instance = null;
    private final String thePrefix;
    private final String theColor;
    private final String errorColor;
    private final String theAdminColor;
    private final String theLogPrefix;

    protected DuelMsg() {

        thePrefix = Duel.get().getConfigs().getMessageSettings().getPrefix();
        theColor = Duel.get().getConfigs().getMessageSettings().getMessageColor();
        errorColor = Duel.get().getConfigs().getMessageSettings().getErrorColor();
        theAdminColor = Duel.get().getConfigs().getMessageSettings().getAdminColor();
        theLogPrefix = Duel.get().getConfigs().getMessageSettings().getLogPrefix();
    }

    public static DuelMsg getInstance() {

        if (instance == null) {
            instance = new DuelMsg();
        }
        return instance;
    }

    @Override
    public String getInfoChar() {

        return theColor;
    }

    @Override
    public String getAdminColor() {

        return theAdminColor;
    }

    @Override
    public String getErrorColor() {

        return errorColor;
    }

    @Override
    public String getPrefix() {

        return thePrefix;

    }

    @Override
    public String getLogPrefix() {

        return theLogPrefix;
    }

}
