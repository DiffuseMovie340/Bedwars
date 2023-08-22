package com.diffusemovie.minigame.Instance;

import com.diffusemovie.minigame.GameState;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Game {

    private Arena arena;
    private HashMap<UUID, Integer> points;

    //actual game code goes here
    public Game(Arena arena){
        this.arena = arena;
        this.points = new HashMap<>();
    }

    public void start(){
        arena.setState(GameState.LIVE);
        arena.sendMessage(ChatColor.GOLD + "GAME START!" + ChatColor.GREEN + " Break 20 blocks before your opponent does!");

        for (UUID uuid : arena.getPlayers()){
            points.put(uuid, 0);
        }

    }

    public void addPoint(Player player){
        int playerPoints = points.get(player.getUniqueId()) + 1;
        if (playerPoints == 20){
            arena.sendMessage(ChatColor.GOLD + "---------------------------------------------");
            arena.sendMessage(ChatColor.GOLD + "\nCongrats!" + player.getName() + ChatColor.GREEN + "has won!");
            arena.sendMessage(ChatColor.GOLD + "\n---------------------------------------------");

            arena.reset(true);
        }else{
            player.sendMessage("+1");
            points.replace(player.getUniqueId(), playerPoints);
        }
    }
}