package net.crazy.pvplib.library.world;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;

public class BlockFinder {
    private final Material material;
    private final World world;
    private ArrayList<Block> blocks = new ArrayList<>();

    /**
     * Class to search the loaded chunks of a world for a specific type of block
     * @param material of the block
     * @param world in which the blocks are located
     */
    public BlockFinder(Material material, World world) {
        this.material = material;
        this.world = world;
    }

    /**
     * Searching for the blocks
     * @implNote This will not fully work in the caves & cliffs update as their y-coordinates can be below 0
     */
    public void find() {
        Chunk[] chunks = world.getLoadedChunks();

        for (Chunk chunk : chunks) {
            for (int x = 0; x <= 15; x++)
                for (int z = 0; z <= 15; z++)
                    for (int y = 0; y <= 127; y++) {
                        Block block = chunk.getBlock(x, y, z);

                        if (block.getType().equals(material))
                            blocks.add(block);
                    }
        }
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }
}
