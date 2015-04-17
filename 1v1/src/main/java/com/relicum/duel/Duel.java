package com.relicum.duel;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Name: Duel.java Created: 17 April 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class Duel extends JavaPlugin
{
    
    private static Duel instance;
    
    
    public void onEnable()
    {
        
        instance = this;
        
    }
    
    public void onDisable()
    {
        
        
    }
    
    /**
     * Utility method for getting a plugins Main JavaPlugin Class
     *
     * @return Duel a static instance of the main plugin Class
     */
    public static Duel getInstance()
    {
        
        return instance;
        
    }
    
}
