package net.crazy.pvpgame.game;

import net.crazy.pvpgame.Practice;
import net.crazy.pvplib.library.world.BlockFinder;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.util.Vector;

import java.util.ArrayList;

/**
 * Represents the arena of a running game
 */
public class Arena {
    private final Practice instance;
    private final World arena;

    private final ArrayList<Location> locations = new ArrayList<>();
    private final String path;

    public Arena(Practice instance, String path) {
        this.instance = instance;
        this.arena = Bukkit.createWorld(new WorldCreator(path));
        this.path = path;

        prepareArena();
    }

    /**
     * Loads the configuration (signs) of the arena
     */
    private void prepareArena() {
        BlockFinder finder = new BlockFinder(Material.SIGN_POST, arena);
        finder.find();

        Bukkit.getScheduler().runTaskLater(instance, () -> {
            for (Block block : finder.getBlocks()) {
                if (!(block.getState() instanceof Sign))
                    continue;

                Sign sign = (Sign) block.getState();
                String[] lines = sign.getLines();

                if (!lines[0].startsWith("{{arena"))
                    continue;

                if (lines[1].contains("spawn"))
                    locations.add(sign.getLocation());

                block.setType(Material.AIR);
            }

            updateLocations(locations.get(0), locations.get(1));
        }, 5 * 20L);
    }

    /**
     * Formats the locations to face each other
     * @param loc1  first spawn location
     * @param loc2  second spawn location
     */
    private void updateLocations(Location loc1, Location loc2) {
        Vector vec1 = loc1.toVector().subtract(loc2.toVector());
        Vector vec2 = loc2.toVector().subtract(loc1.toVector());

        locations.clear();

        loc1.setDirection(vec2);
        loc2.setDirection(vec1);

        locations.add(loc1);
        locations.add(loc2);
    }

    public World getArena() {
        return arena;
    }

    public String getPath() {
        return path;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }
}
