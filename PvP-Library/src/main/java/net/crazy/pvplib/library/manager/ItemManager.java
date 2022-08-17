package net.crazy.pvplib.library.manager;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Easily create items with names, enchantments, and much more
 */
public class ItemManager {
    private ItemStack item;
    private ItemMeta meta;
    private final List<String> lore = new ArrayList<>();

    public ItemManager(ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
    }

    public ItemManager(Material material, int amount) {
        this.item = new ItemStack(material, amount, (short) 0);
        this.meta = item.getItemMeta();
    }

    /**
     * Makes the Item unbreakable in the meta
     * @return      Updated instance of the ItemManager
     */
    public ItemManager setUnbreakable() {
        this.meta.spigot().setUnbreakable(true);
        return this;
    }

    /**
     * Adds a new lore line to the item
     * @param line  Lore line to be displayed
     * @return      Updated instance of the ItemManager
     */
    public ItemManager addLoreLine(String line) {
        this.lore.add(line);
        return this;
    }

    /**
     * Sets the display name of the item
     * @param name      Name to be displayed
     * @return          Updated instance of the ItemManager
     */
    public ItemManager setDisplayName(String name) {
        this.meta.setDisplayName(name);
        return this;
    }

    /**
     * Adds an enchantment to the item
     * @param enchantment   Enchantment to be added
     * @param level         Level of the enchantment
     * @return              Updated instance of the ItemManager
     */
    public ItemManager addEnchantment(Enchantment enchantment, int level) {
        this.meta.addEnchant(enchantment, level, true);
        return this;
    }

    /**
     * Creates an ItemStack based on the configuration
     * @return      Configured ItemStack
     */
    public ItemStack build() {
        if (!this.lore.isEmpty())
            this.meta.setLore(this.lore);

        this.item.setItemMeta(this.meta);
        return this.item;
    }
}
