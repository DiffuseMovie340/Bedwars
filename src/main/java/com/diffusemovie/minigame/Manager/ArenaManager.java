package com.diffusemovie.minigame.Manager;

import com.diffusemovie.minigame.Instance.Arena;
import com.diffusemovie.minigame.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ArenaManager {

    private List<Arena> arenas = new ArrayList<>();

    public ArenaManager(Minigame minigame){
        FileConfiguration config = minigame.getConfig();
        for (String str : config.getConfigurationSection("arenas.").getKeys(false)){
            arenas.add(new Arena(minigame, Integer.parseInt(str), new Location(
                    Bukkit.getWorld(config.getString("arenas." + str + ".world")),
                    config.getDouble("arenas." + str + ".x"),
                    config.getDouble("arenas." + str + ".y"),
                    config.getDouble("arenas." + str + ".z"),
                    (float) config.getDouble("arenas." + str + ".yaw"),
                    (float) config.getDouble("arenas." + str + ".pitch")
            )));
        }
    }

    public List<Arena> getArenas(){ return arenas; }

    public Arena getArena(Player player){
        for (Arena arena : arenas){
            if (arena.getPlayers().contains(player.getUniqueId())) {
                return arena;
            }
        }
        return null;
    }

    public Arena getArena(int id){
        for (Arena arena : arenas){
            if (arena.getID() == id){
                return arena;
            }
        }
        return null;
    }

    public void sendGameMessage(ChatColor color, String message, Player player){
        player.sendMessage(ChatColor.GOLD + "Arena" + ChatColor.GRAY + " > " + color +  message);
    }
}

