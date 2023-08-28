package com.diffusemovie.minigame.Instance;

import com.diffusemovie.minigame.GameState;
import com.diffusemovie.minigame.Manager.ConfigManager;
import com.diffusemovie.minigame.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Arena {

    private Minigame minigame;

    private int id;
    private Location spawn;

    private Countdown countdown;
    private GameState state;
    private Game game;

    private List<UUID> players;

    public Arena(Minigame minigame, int id, Location spawn){
        this.minigame = minigame;

        this.id = id;
        this.spawn = spawn;

        this.state = GameState.RECRUTING;
        this.players = new ArrayList<>();

        this.countdown = new Countdown(minigame, this);
        this.game = new Game(this);
    }

    /* Game */

    public void start(){
        game.start();
    }

    public void reset(boolean kickPlayers){
        if (kickPlayers){
            Location lobby = ConfigManager.getLobbySpawn();
            for (UUID uuid : players){
                Bukkit.getPlayer(uuid).teleport(lobby);
            }
            players.clear();
        }
        sendTitle("", "");

        state = GameState.RECRUTING;
        countdown.cancel();
        countdown = new Countdown(minigame, this);
        game = new Game(this);
    }
    /* Toolkit */

    public void sendMessage(String message){
        for (UUID uuid : players){
            Bukkit.getPlayer(uuid).sendMessage(message);
        }
    }

    public void sendTitle(String title, String subtitle){
        for (UUID uuid : players){
            Bukkit.getPlayer(uuid).sendTitle(title, subtitle);
        }
    }



    /* Player management */

    public void addPlayer(Player player){
        players.add(player.getUniqueId());
        player.teleport(spawn);

        if (state.equals(GameState.RECRUTING) && players.size() >= ConfigManager.getRequiredPlayers()){
            countdown.start();
        }
    }
    public void removePlayer(Player player){
        players.remove(player.getUniqueId());
        player.teleport(ConfigManager.getLobbySpawn());
        player.sendTitle(ChatColor.RED + "You left the game!", "", 10, 40, 10);

        if (state == GameState.COUNTDOWN && players.size() < ConfigManager.getRequiredPlayers()){
            sendMessage(ChatColor.RED + "||||||||||||||||||||||||Countdown Ended||||||||||||||||||||||||");
            sendMessage(ChatColor.RED + "\n  Reason: Someone has left, and there aren't enough players.");
            sendMessage(ChatColor.RED + "\n|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
            reset(false);
        }

        if (state == GameState.LIVE && players.size() < ConfigManager.getRequiredPlayers()){
            sendMessage(ChatColor.RED + "||||||||||||||||||||||||Game Ended||||||||||||||||||||||||");
            sendMessage(ChatColor.RED + "\n  Reason: Someone has left, and there aren't enough players.");
            sendMessage(ChatColor.RED + "\n||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
            reset(false);
        }

    }

    /* INFO */

    public int getID(){ return id; }
    public GameState getState(){ return state; }
    public List<UUID> getPlayers() {return players; }

    public Game getGame(){ return game; }

    public void setState(GameState state){
        this.state = state;
    }
}
