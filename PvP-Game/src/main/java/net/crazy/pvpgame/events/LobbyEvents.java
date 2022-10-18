package net.crazy.pvpgame.events;

import net.crazy.pvpgame.Practice;
import net.crazy.pvpgame.kits.Kit;
import net.crazy.pvplib.library.manager.StatManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffect;

public class LobbyEvents implements Listener {
    private final Practice instance;

    public LobbyEvents(Practice instance) {
        this.instance = instance;
        Bukkit.getPluginManager().registerEvents(this, instance);
    }

    /**
     * Called when a player joins the server
     * Teleports said player to the lobby spawn and setting the GameMode
     *
     * @param event PlayerJoinEvent instance
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        Player player = event.getPlayer();

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.updateInventory();

        player.setGameMode(GameMode.ADVENTURE);
        player.teleport(instance.lobbySpawn);

        instance.playerStats.put(player, new StatManager(player));
    }

    /**
     * Preventing damage in non-arena worlds
     *
     * @param event EntityDamageEvent instance
     */
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();

        if (!player.getWorld().equals(instance.lobby))
            return;

        event.setCancelled(true);
    }

    /**
     * Resetting the inventory when players are teleported to the lobby
     *
     * @param event PlayerTeleportEvent instance
     */
    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if (!event.getTo().getWorld().equals(instance.lobby))
            return;

        Player player = event.getPlayer();

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        for (Kit kit : instance.kits)
            player.getInventory().addItem(kit.getRepresentationItem());
        player.updateInventory();

        player.setWalkSpeed(0.2f);

        for (PotionEffect potionEffect : player.getActivePotionEffects())
            player.removePotionEffect(potionEffect.getType());
    }

    /**
     * Preventing players from having to eat
     *
     * @param event FoodLevelChangeEvent instance
     */
    @EventHandler
    public void handleSaturation(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    /**
     * Handles the basics of a player quitting
     *
     * @param event PlayerQuitEvent instance
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        instance.queue.removeFromQueue(event.getPlayer());
        instance.playerStats.remove(event.getPlayer());
        event.setQuitMessage(null);
    }

    /**
     * Preventing dropping items
     *
     * @param event PlayerDropItemEvent instance
     */
    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (!event.getPlayer().getWorld().equals(instance.lobby))
            return;

        event.setCancelled(true);
    }

    /**
     * Preventing block breaking
     *
     * @param event BlockBreakEvent instance
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!event.getPlayer().getWorld().equals(instance.lobby))
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!event.getPlayer().getWorld().equals(instance.lobby))
            return;

        event.setCancelled(true);
    }

    /**
     * Preventing the message of achievements
     *
     * @param event PlayerAchievementAwardedEvent instance
     */
    @EventHandler
    public void onAchievement(PlayerAchievementAwardedEvent event) {
        event.setCancelled(true);
    }
}
