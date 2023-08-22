package com.diffusemovie.minigame;

import com.diffusemovie.minigame.Command.ArenaCommand;
import com.diffusemovie.minigame.Listener.ConnectListener;
import com.diffusemovie.minigame.Listener.GameListener;
import com.diffusemovie.minigame.Manager.ArenaManager;
import com.diffusemovie.minigame.Manager.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Minigame extends JavaPlugin {

    private ArenaManager arenaManager;

    @Override
    public void onEnable() {
        ConfigManager.setupConfig(this);
        arenaManager = new ArenaManager(this);

        Bukkit.getPluginManager().registerEvents(new ConnectListener(this), this);
        Bukkit.getPluginManager().registerEvents(new GameListener(this), this);

        getCommand("arena").setExecutor(new ArenaCommand(this));
    }
    public ArenaManager getArenaManager() { return arenaManager; }

}
