package com.diffusemovie.minigame;

import org.bukkit.ChatColor;

public enum GameState {

    RECRUTING(ChatColor.GREEN + "Recruiting players"),
    COUNTDOWN(ChatColor.YELLOW + "Countdown"),
    LIVE(ChatColor.RED + "Game in progress");

    private String display;
    GameState(String display){
        this.display = display;
    }

    public String getDisplay(){ return display; }
}
