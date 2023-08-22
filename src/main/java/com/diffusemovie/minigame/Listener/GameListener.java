package com.diffusemovie.minigame.Listener;

import com.diffusemovie.minigame.GameState;
import com.diffusemovie.minigame.Instance.Arena;
import com.diffusemovie.minigame.Minigame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;


public class GameListener implements Listener {
    private Minigame minigame;

    public GameListener(Minigame minigame){
        this.minigame = minigame;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){

        Arena arena = minigame.getArenaManager().getArena(e.getPlayer());
        if (arena != null){
            if (arena.getState().equals(GameState.LIVE)){
                arena.getGame().addPoint(e.getPlayer());
            }
        }
    }
}
