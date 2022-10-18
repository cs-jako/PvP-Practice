package net.crazy.pvpgame.game;

import net.crazy.pvpgame.Practice;
import net.crazy.pvpgame.kits.Kit;
import net.crazy.pvplib.library.manager.StatManager;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a duel game
 */
public class Game {
    private final Practice instance;
    private final Kit kit;
    private final Player[] players;
    private Arena arena;

    public Game(Practice instance, Kit kit, Player... players) {
        this.instance = instance;
        this.kit = kit;
        this.players = players;

        selectArena();
    }

    /**
     * Starts the game by teleporting the players and preventing any movement
     * After a countdown of 5 seconds, the game will start
     */
    public void start() {
        instance.runningGames.add(this);
        for (Player player : players) {
            player.setWalkSpeed(0);
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 30 * 20, -100));
        }

        teleportPlayers();
        kit.loadInventory(players);
        countdown();
    }

    private int taskId;

    /**
     * Starts a 5 seconds countdown - at the end of which, the players will be able to move
     */
    private void countdown() {
        AtomicInteger secs = new AtomicInteger(5);
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, () -> {
            switch (secs.get()) {
                case 5:
                case 4:
                case 3:
                case 2:
                    sendMessage("§aGame starts in §6" + secs.get() + "§a seconds.");
                    break;
                case 1:
                    sendMessage("§aGame starts in §6" + secs.get() + "§a second.");
                    break;
                case 0:
                    sendMessage("§aGame starts now!");

                    for (Player player : players) {
                        player.removePotionEffect(PotionEffectType.JUMP);
                        player.setWalkSpeed(0.2f);
                    }

                    stopRunnable();
                    break;
            }
            secs.getAndDecrement();
        }, 0L, 20L);
    }

    private void stopRunnable() {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    /**
     * Teleports the players to their spawn points based on the map
     */
    private void teleportPlayers() {
        for (int i = 0; i < 2; i++) {
            players[i].teleport(arena.getLocations().get(i));
        }
    }

    /**
     * Selects a random arena, backups the folder
     */
    private void selectArena() {
        List<String> arenaPaths = instance.getConfig().getStringList("arenas");
        int i;

        if (arenaPaths.size() == 1)
            i = 0;
        else
            i = new Random().nextInt(arenaPaths.size() - 1);

        String basePath = "arenas/" + arenaPaths.get(i);
        String path = "games/arena_" + UUID.randomUUID().toString().split("-")[0];

        instance.duplicateDirectory(basePath, path);
        this.arena = new Arena(instance, path);
    }

    /**
     * Stops the game and teleports the player back to the lobby.
     */
    public void stop(Player winner) {
        instance.runningGames.remove(this);
        for (Player player : players) {
            StatManager statManager = instance.playerStats.get(player);
            statManager.setGamesPlayed(statManager.getGamesPlayed() + 1);

            if (player == winner) {
                statManager.setWins(statManager.getWins() + 1);
                statManager.setKills(statManager.getKills() + 1);
            } else {
                statManager.setLosses(statManager.getLosses() + 1);
                statManager.setDeaths(statManager.getDeaths() + 1);
            }

            statManager.update();

            if (!player.isOnline())
                continue;

            player.teleport(instance.lobbySpawn);
        }

        Bukkit.unloadWorld(arena.getArena(), false);

        try {
            FileUtils.deleteDirectory(new File(arena.getPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Easy access method to send a message to both participants
     *
     * @param message message to send to the participants
     */
    public void sendMessage(String message) {
        for (Player player : players) {
            // Preventing possible NPE in GameEvents
            if (!player.isOnline())
                continue;

            player.sendMessage(instance.prefix + message);
        }
    }

    public boolean isPlayer(Player player) {
        return players[0] == player || players[1] == player;
    }

    /**
     * Gets the opponent of a player
     * @param player known player
     * @return opponent
     */
    public Player getOpponent(Player player) {
        for (Player p : players)
            if (p != player)
                return p;

        return null;
    }

    public Player[] getPlayers() {
        return players;
    }
}
