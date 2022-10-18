package net.crazy.pvpgame.kits;

import net.crazy.pvpgame.Practice;
import net.crazy.pvplib.library.manager.ItemManager;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SkyWarsKit extends Kit {
    public SkyWarsKit(Practice instance) {
        super(instance);
    }

    @Override
    public ItemStack getRepresentationItem() {
        return new ItemManager(Material.DIAMOND_SWORD, 1).setUnbreakable()
                .setDisplayName("§6SkyWars").build();
    }

    @Override
    public String getName() {
        return "§6SkyWars";
    }

    @Override
    public String getConfigKey() {
        return "skywars";
    }

    @Override
    ItemStack getPrimaryWeapon() {
        return new ItemManager(Material.DIAMOND_SWORD, 1)
                .addEnchantment(Enchantment.DAMAGE_ALL, 1)
                .build();
    }

    @Override
    ItemStack getSecondaryWeapon() {
        return null;
    }

    @Override
    ItemStack getRod() {
        return null;
    }

    @Override
    ItemStack getHelmet() {
        return new ItemManager(Material.DIAMOND_HELMET, 1)
                .addEnchantment(Enchantment.DURABILITY, 1).build();
    }

    @Override
    ItemStack getChestplate() {
        return new ItemManager(Material.DIAMOND_CHESTPLATE, 1)
                .addEnchantment(Enchantment.DURABILITY, 1).build();
    }

    @Override
    ItemStack getLeggings() {
        return new ItemManager(Material.DIAMOND_LEGGINGS, 1)
                .addEnchantment(Enchantment.DURABILITY, 1).build();
    }

    @Override
    ItemStack getBoots() {
        return new ItemManager(Material.DIAMOND_BOOTS, 1)
                .addEnchantment(Enchantment.DURABILITY, 1).build();
    }

    @Override
    List<ItemStack> getAdditionalItems() {
        List<ItemStack> items = new ArrayList<>();
        items.add(
                new ItemManager(Material.WATER_BUCKET, 1).build()
        );
        items.add(
                new ItemManager(Material.LAVA_BUCKET, 1).build()
        );
        items.add(
                new ItemManager(Material.STONE, 64).build()
        );
        items.add(
                new ItemManager(Material.DIAMOND_PICKAXE, 1)
                        .addEnchantment(Enchantment.DIG_SPEED, 1)
                        .addEnchantment(Enchantment.DURABILITY, 1)
                        .build()
        );
        return items;
    }
}
