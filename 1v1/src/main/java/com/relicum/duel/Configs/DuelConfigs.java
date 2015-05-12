package com.relicum.duel.Configs;

import org.apache.commons.lang.Validate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DuelConfigs stores general and global settings.
 *
 * @author Relicum
 * @version 0.0.1
 */
public class DuelConfigs {

    private boolean enable = true;
    private boolean firstLoad = true;
    private boolean dedicated = false;
    private String lobbyWorld = "world";
    private String gameWorld = "world";
    private boolean wagerGames = false;
    private boolean rankedMatches = false;
    private boolean autoJoin = false;
    private boolean blockCmdsInLobby = true;
    private boolean isBlockCmdsInGame = true;
    private boolean blockGlobalChat = true;
    private boolean allowLobbyChat = true;
    private boolean restrictIngameChat = true;
    private Map<String, Integer> coll = new HashMap<>();

    public DuelConfigs() {

    }


    public Set<Map.Entry<String, Integer>> getCollectionEntry() {
        return coll.entrySet();
    }

    /**
     * Sets collection index.
     *
     * @param name  the name
     * @param index the index
     */
    public void setCollectionIndex(String name, int index) {
        coll.put(name, index);
    }

    /**
     * Gets next increment.
     *
     * @param name the name
     * @return the next increment
     */
    public int getNextIncrement(String name) {
        int num = coll.get(name);
        num++;
        coll.put(name, num);

        return (num) - 1;
    }

    /**
     * Remove collection.
     *
     * @param name the name
     */
    public void removeCollection(String name) {
        coll.remove(name);
    }

    /**
     * Add new collection.
     *
     * @param name the name
     */
    public void addNewCollection(String name) {
        coll.put(name, 0);
    }

    /**
     * Collection exists.
     *
     * @param name the name
     * @return the boolean
     */
    public boolean collectionExists(String name) {
        Validate.notNull(name);
        return coll.containsKey(name);
    }

    public int getCollectionSize() {
        return coll.size();
    }


    /**
     * Gets collection names.
     *
     * @return the collection names
     */
    public List<String> getCollectionNames() {
        return coll.keySet().stream().collect(Collectors.toList());
    }

    /**
     * Gets rankedMatches.
     *
     * @return Value of rankedMatches.
     */
    public boolean isRankedMatches() {
        return rankedMatches;
    }

    /**
     * Gets autoJoin.
     *
     * @return Value of autoJoin.
     */
    public boolean isAutoJoin() {
        return autoJoin;
    }

    /**
     * Sets new wagerGames.
     *
     * @param wagerGames New value of wagerGames.
     */
    public void setWagerGames(boolean wagerGames) {
        this.wagerGames = wagerGames;
    }

    /**
     * Sets new autoJoin.
     *
     * @param autoJoin New value of autoJoin.
     */
    public void setAutoJoin(boolean autoJoin) {
        this.autoJoin = autoJoin;
    }

    /**
     * Sets new isBlockCmdsInGame.
     *
     * @param isBlockCmdsInGame New value of isBlockCmdsInGame.
     */
    public void setIsBlockCmdsInGame(boolean isBlockCmdsInGame) {
        this.isBlockCmdsInGame = isBlockCmdsInGame;
    }

    /**
     * Sets new firstLoad.
     *
     * @param firstLoad New value of firstLoad.
     */
    public void setFirstLoad(boolean firstLoad) {
        this.firstLoad = firstLoad;
    }

    /**
     * Gets gameWorld.
     *
     * @return Value of gameWorld.
     */
    public String getGameWorld() {
        return gameWorld;
    }

    /**
     * Sets new blockGlobalChat.
     *
     * @param blockGlobalChat New value of blockGlobalChat.
     */
    public void setBlockGlobalChat(boolean blockGlobalChat) {
        this.blockGlobalChat = blockGlobalChat;
    }

    /**
     * Gets enable.
     *
     * @return Value of enable.
     */
    public boolean isEnable() {
        return enable;
    }

    /**
     * Sets new dedicated.
     *
     * @param dedicated New value of dedicated.
     */
    public void setDedicated(boolean dedicated) {
        this.dedicated = dedicated;
    }

    /**
     * Sets new lobbyWorld.
     *
     * @param lobbyWorld New value of lobbyWorld.
     */
    public void setLobbyWorld(String lobbyWorld) {
        this.lobbyWorld = lobbyWorld;
    }

    /**
     * Sets new allowLobbyChat.
     *
     * @param allowLobbyChat New value of allowLobbyChat.
     */
    public void setAllowLobbyChat(boolean allowLobbyChat) {
        this.allowLobbyChat = allowLobbyChat;
    }

    /**
     * Gets firstLoad.
     *
     * @return Value of firstLoad.
     */
    public boolean isFirstLoad() {
        return firstLoad;
    }

    /**
     * Sets new gameWorld.
     *
     * @param gameWorld New value of gameWorld.
     */
    public void setGameWorld(String gameWorld) {
        this.gameWorld = gameWorld;
    }

    /**
     * Sets new blockCmdsInLobby.
     *
     * @param blockCmdsInLobby New value of blockCmdsInLobby.
     */
    public void setBlockCmdsInLobby(boolean blockCmdsInLobby) {
        this.blockCmdsInLobby = blockCmdsInLobby;
    }

    /**
     * Gets blockGlobalChat.
     *
     * @return Value of blockGlobalChat.
     */
    public boolean isBlockGlobalChat() {
        return blockGlobalChat;
    }

    /**
     * Gets allowLobbyChat.
     *
     * @return Value of allowLobbyChat.
     */
    public boolean isAllowLobbyChat() {
        return allowLobbyChat;
    }

    /**
     * Sets new enable.
     *
     * @param enable New value of enable.
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    /**
     * Gets lobbyWorld.
     *
     * @return Value of lobbyWorld.
     */
    public String getLobbyWorld() {
        return lobbyWorld;
    }

    /**
     * Gets restrictIngameChat.
     *
     * @return Value of restrictIngameChat.
     */
    public boolean isRestrictIngameChat() {
        return restrictIngameChat;
    }

    /**
     * Gets dedicated.
     *
     * @return Value of dedicated.
     */
    public boolean isDedicated() {
        return dedicated;
    }

    /**
     * Sets new restrictIngameChat.
     *
     * @param restrictIngameChat New value of restrictIngameChat.
     */
    public void setRestrictIngameChat(boolean restrictIngameChat) {
        this.restrictIngameChat = restrictIngameChat;
    }

    /**
     * Gets blockCmdsInLobby.
     *
     * @return Value of blockCmdsInLobby.
     */
    public boolean isBlockCmdsInLobby() {
        return blockCmdsInLobby;
    }

    /**
     * Gets isBlockCmdsInGame.
     *
     * @return Value of isBlockCmdsInGame.
     */
    public boolean isIsBlockCmdsInGame() {
        return isBlockCmdsInGame;
    }

    /**
     * Sets new rankedMatches.
     *
     * @param rankedMatches New value of rankedMatches.
     */
    public void setRankedMatches(boolean rankedMatches) {
        this.rankedMatches = rankedMatches;
    }

    /**
     * Gets wagerGames.
     *
     * @return Value of wagerGames.
     */
    public boolean isWagerGames() {
        return wagerGames;
    }
}
