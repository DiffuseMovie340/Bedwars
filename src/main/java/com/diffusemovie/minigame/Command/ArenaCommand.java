package com.diffusemovie.minigame.Command;

import com.diffusemovie.minigame.GameState;
import com.diffusemovie.minigame.Instance.Arena;
import com.diffusemovie.minigame.Minigame;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCommand implements CommandExecutor {
    private Minigame minigame;

    public ArenaCommand(Minigame minigame){
        this.minigame = minigame;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

       if (sender instanceof Player){
           Player player = (Player) sender;

           if (args.length == 1){

               if (args[0].equalsIgnoreCase("list")){

                   minigame.getArenaManager().sendGameMessage(ChatColor.BLUE, "These are the arenas:", player);
                   for (Arena arena : minigame.getArenaManager().getArenas()){
                        player.sendMessage(ChatColor.AQUA + "-" + arena.getID() + " »» " + arena.getState().getDisplay());
                   }

               }else if(args[0].equalsIgnoreCase("leave")){
                    Arena arena = minigame.getArenaManager().getArena(player);
                    if (arena != null){
                        minigame.getArenaManager().sendGameMessage(ChatColor.YELLOW, "You have left the arena", player);
                        arena.removePlayer(player);
                    }else{
                        minigame.getArenaManager().sendGameMessage(ChatColor.RED, "You are not in an arena!", player);
                    }
               }else{

                   minigame.getArenaManager().sendGameMessage(ChatColor.RED, "------", player);
                   minigame.getArenaManager().sendGameMessage(ChatColor.RED, "Invalid use - use one of these options:" , player);
                   minigame.getArenaManager().sendGameMessage(ChatColor.RED, "/arena list", player);
                   minigame.getArenaManager().sendGameMessage(ChatColor.RED, "/arena leave", player);
                   minigame.getArenaManager().sendGameMessage(ChatColor.RED, "/arena join <id>", player);
                   minigame.getArenaManager().sendGameMessage(ChatColor.RED, "-------", player);
                   return false;

               }

           }else if (args.length == 2){

               if (args[0].equalsIgnoreCase("join")){

                    if (minigame.getArenaManager().getArena(player) != null){
                        minigame.getArenaManager().sendGameMessage(ChatColor.RED, "You are already in an arena.", player);
                        return false;
                    }else{

                        int id;
                        try{
                            id = Integer.parseInt(args[1]);
                        }catch(NumberFormatException e){
                            minigame.getArenaManager().sendGameMessage(ChatColor.RED, "Invalid arena ID.", player);
                            return false;
                        }

                        if (id >= 0 && id < minigame.getArenaManager().getArenas().size()){
                            Arena arena = minigame.getArenaManager().getArena(id);
                            if (arena.getState() == GameState.RECRUTING || arena.getState() == GameState.COUNTDOWN){
                                minigame.getArenaManager().sendGameMessage(ChatColor.GREEN, "You have joined arena " + id + ".", player);
                                arena.addPlayer(player);
                            }else{
                                minigame.getArenaManager().sendGameMessage(ChatColor.RED, "This arena is unavailable! Try joining another.", player);
                            }
                        }else{
                            minigame.getArenaManager().sendGameMessage(ChatColor.RED, "Invalid arena ID.", player);
                        }

                    }
               }else{

                   minigame.getArenaManager().sendGameMessage(ChatColor.RED, "-------------------------------------------------------", player);
                   minigame.getArenaManager().sendGameMessage(ChatColor.RED, "Invalid use - use one of these options:" , player);
                   minigame.getArenaManager().sendGameMessage(ChatColor.RED, "/arena list", player);
                   minigame.getArenaManager().sendGameMessage(ChatColor.RED, "/arena leave", player);
                   minigame.getArenaManager().sendGameMessage(ChatColor.RED, "/arena join <id>", player);
                   minigame.getArenaManager().sendGameMessage(ChatColor.RED, "-------------------------------------------------------", player);
                   return false;

               }

           }
       }
        return false;
    }
}
