package net.crazy.pvpgame.kits;


import net.crazy.pvpgame.Practice;
import net.crazy.pvplib.library.manager.ItemManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;

public abstract class Kit {
    public final Practice instance;

    public Kit(Practice instance) {
        this.instance = instance;
        instance.kits.add(this);
        instance.queue.initializeKit(this);
    }

    /**
     * Loads the kit inventory for the player
     * @param players   players to receive the inventory
     */
    public void loadInventory(Player... players) {
        for (Player player : players) {
            PlayerInventory inventory = player.getInventory();
            inventory.clear();

            inventory.setItem(0, getPrimaryWeapon() != null ? getPrimaryWeapon() : air);
            inventory.setItem(1, getSecondaryWeapon() != null ? getSecondaryWeapon() : air);
            inventory.setItem(2, getRod() != null ? getRod() : air);

            inventory.setHelmet(getHelmet());
            inventory.setChestplate(getChestplate());
            inventory.setLeggings(getLeggings());
            inventory.setBoots(getBoots());

            ItemStack[] items = new ItemStack[getAdditionalItems().size()];
            getAdditionalItems().toArray(items);
            inventory.addItem(items);

            player.updateInventory();
        }
    }

    public abstract ItemStack getRepresentationItem();
    public abstract String getName();

    abstract ItemStack getPrimaryWeapon();
    abstract ItemStack getSecondaryWeapon();
    abstract ItemStack getRod();

    abstract ItemStack getHelmet();
    abstract ItemStack getChestplate();
    abstract ItemStack getLeggings();
    abstract ItemStack getBoots();

    abstract List<ItemStack> getAdditionalItems();

    private final ItemStack air = new ItemManager(Material.AIR, 1).build();
}
