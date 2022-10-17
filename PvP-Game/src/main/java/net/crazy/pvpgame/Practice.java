package net.crazy.pvpgame;

import com.github.juliarn.npc.NPCPool;
import net.crazy.pvpgame.events.*;
import net.crazy.pvpgame.game.*;
import net.crazy.pvpgame.kits.*;
import net.crazy.pvpgame.util.NPCUtil;
import net.crazy.pvplib.library.MCLogger;
import net.crazy.pvplib.library.world.BlockFinder;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Practice extends JavaPlugin {
    public String prefix;
    public World lobby;
    public List<World> loadedWorlds = new ArrayList<>();

    public NPCPool npcPool;
    public NPCUtil npcUtil;

    public Queue queue;

    public ArrayList<Game> runningGames = new ArrayList<>();
    public ArrayList<Kit> kits = new ArrayList<>();

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        // Getting and fixing color codes of the prefix
        this.prefix = getConfig().getString("prefix");
        this.prefix = ChatColor.translateAlternateColorCodes('&', this.prefix);

        // Checking for lobby and game folder
        startupChecks();

        // Creating a safe copy of the lobby
        duplicateDirectory("lobbies/" + getConfig().getString("lobby"), "lobby");
        lobby = Bukkit.createWorld(new WorldCreator("lobby"));
        prepareWorld(lobby, false);
        prepareLobby();
        loadedWorlds.add(lobby);

        // Preparing the npc pool
        this.npcPool = NPCPool.builder(this)
                .spawnDistance(60)
                .actionDistance(30)
                .tabListRemoveTicks(20)
                .build();

        this.npcUtil = new NPCUtil(this);

        queue = new Queue(this);

        // Registering Events
        new NPCEvents(this);
        new LobbyEvents(this);
        new GameEvents(this);

        // Loading kits
        new BowKit(this);
        new RodKit(this);
        new SwordKit(this);

        // Checking the queue
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, queue::check, 10 * 20L, 7 * 20L);
    }

    @Override
    public void onDisable() {
        Bukkit.unloadWorld(lobby, false);
    }

    /**
     * safely duplicates a directory
     * used for creating a backup of the lobby,
     * and for creating the different arenas
     */
    public void duplicateDirectory(String srcPath, String destPath) {
        try {
            FileUtils.copyDirectory(new File(srcPath), new File(destPath));
        } catch (IOException exception) {
            MCLogger.log(MCLogger.LogType.ERROR, exception.getMessage());
        }
    }

    /**
     * Setting default values of the world
     */
    public void prepareWorld(World world, boolean pvp) {
        world.setAutoSave(false);
        world.setGameRuleValue("doMobSpawning", "false");
        world.setPVP(pvp);
    }

    /**
     * Gets the game based on the player
     * @param player    player instance
     * @return          the game or null if the player is not in a game
     */
    public Game getGame(Player player) {
        for (Game game : runningGames) {
            if (game.isPlayer(player))
                return game;
        }
        return null;
    }

    /**
     * Gets the Kit instance based on the item
     * @param item      item used by the player
     * @return          kit or null if the item can't be found in the kit list
     */
    public Kit getKit(ItemStack item) {
        for (Kit kit : kits) {
            if (kit.getRepresentationItem().isSimilar(item))
                return kit;
        }
        return null;
    }

    /**
     * Deletes the lobby folder as the lobby might change on startup
     * Resetting games folder
     */
    private void startupChecks() {
        File lobby = new File("lobby");
        File games = new File("games");

        if (!games.exists())
            games.mkdir();

        try {
            FileUtils.deleteDirectory(lobby);
            FileUtils.cleanDirectory(games);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Location lobbySpawn;
    public ArrayList<Location> npcLocations = new ArrayList<>();

    /**
     * Getting player spawn location
     * Getting NPC spawn location(s)
     */
    private void prepareLobby() {
        BlockFinder finder = new BlockFinder(Material.SIGN_POST, lobby);
        finder.find();

        Bukkit.getScheduler().runTaskLater(this, () -> {
            for (Block block : finder.getBlocks()) {
                if (!(block.getState() instanceof Sign))
                    continue;

                Sign sign = (Sign) block.getState();
                String[] lines = sign.getLines();

                if (!lines[0].startsWith("{{lobby_"))
                    continue;

                if (lines[0].contains("spawn"))
                    lobbySpawn = sign.getLocation();

                if (lines[0].contains("npc"))
                    npcLocations.add(sign.getLocation());

                block.setType(Material.AIR);
            }

            // Creating the "Queue" NPCs at the location marked by a sign
            npcUtil.loadNPCs();
        }, 5 * 20L);
    }
}
