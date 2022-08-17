package net.crazy.pvpgame.game;

import net.crazy.pvpgame.Practice;
import net.crazy.pvpgame.kits.Kit;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Queue {
    private final Practice instance;

    private final HashMap<Kit, List<UUID>> queue = new HashMap<>();

    public Queue(Practice instance) {
        this.instance = instance;
    }

    /**
     * Adds / removes a player from the kit-queue
     * @param player    player to add / remove from the queue
     * @param kit       Kit selected by the player
     */
    public void queue(Player player, Kit kit) {
        if (kitQueueContains(kit, player)) {
            removeFromKitQueue(kit, player);
            player.playSound(player.getLocation(), Sound.NOTE_BASS, 1f, 1f);
            player.sendMessage(instance.prefix + "§cYou've left the queue of " + kit.getName() + "§c!");
            return;
        }

        addToKitQueue(kit, player);
        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 1f);
        player.sendMessage(instance.prefix + "§aYou've joined the queue of " + kit.getName() + "§a!");
    }

    /**
     * Starts a game if there are enough players in the kit queue
     */
    public void check() {
        for (Kit kit : queue.keySet()) {
            List<UUID> uuids = queue.get(kit);

            if (uuids.size() < 2)
                return;

            Player p1 = Bukkit.getPlayer(uuids.get(0));
            Player p2 = Bukkit.getPlayer(uuids.get(1));
            removeFromQueue(p1);
            removeFromQueue(p2);

            Game game = new Game(instance, kit, p1, p2);
            Bukkit.getScheduler().runTaskLater(instance, game::start, 5 * 20L);
        }
    }

    /**
     * Initializes the kit in the queue HashMap
     * @param kit   kit to initialize
     */
    public void initializeKit(Kit kit) {
        queue.put(kit, new ArrayList<>());
    }

    /**
     * Removes a player from every queue
     * @param player    player to remove
     */
    public void removeFromQueue(Player player) {
        for (Kit kit : queue.keySet())
            queue.get(kit).remove(player.getUniqueId());
    }

    /**
     * Checks if a player is in a specific kit queue
     * @param kit       selected kit
     * @param player    player to check
     * @return  true if the kit-queue contains the player
     */
    private boolean kitQueueContains(Kit kit, Player player) {
        return queue.get(kit).contains(player.getUniqueId());
    }

    /**
     * Adds a player to a specific kit queue
     * @param kit   selected kit
     * @param player    player to add
     */
    private void addToKitQueue(Kit kit, Player player) {
        queue.get(kit).add(player.getUniqueId());
    }

    /**
     * Removes a player from a specific kit queue
     * @param kit   selected kit
     * @param player    player to remove
     */
    private void removeFromKitQueue(Kit kit, Player player) {
        queue.get(kit).remove(player.getUniqueId());
    }
}
