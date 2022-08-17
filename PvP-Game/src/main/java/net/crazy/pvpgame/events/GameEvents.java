package net.crazy.pvpgame.events;

import net.crazy.pvpgame.Practice;
import net.crazy.pvpgame.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GameEvents implements Listener {
    private final Practice instance;

    public GameEvents(Practice instance) {
        this.instance = instance;
        Bukkit.getPluginManager().registerEvents(this, instance);
    }

    /**
     * Called when a player quits the server
     * If the player was a participant in a game, it will be stopped
     * @param event     PlayerQuitEvent instance
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Game game = instance.getGame(player);

        if (game == null)
            return;

        game.sendMessage("§cThe other player left the game!");
        game.stop();
    }

    /**
     * Called when a player dies
     * Ends the game when one of the players dies
     * @param event     PlayerDeathEvent instance
     */
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);
        event.setKeepInventory(true);
        event.setKeepLevel(true);

        Player player = event.getEntity();
        Game game = instance.getGame(player);

        if (game == null) {
            player.spigot().respawn();
            player.teleport(instance.lobbySpawn);
            return;
        }

        Player killer = player.getKiller();
        game.sendMessage("§6" + killer.getDisplayName() + "§a won the game! gg!");

        for (Player p : game.getPlayers())
            p.teleport(instance.lobbySpawn);

        game.stop();
    }
}
