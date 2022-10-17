package net.crazy.pvpgame.kits;

import net.crazy.pvpgame.Practice;
import net.crazy.pvplib.library.manager.ItemManager;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AxeKit extends Kit {
    public AxeKit(Practice instance) {
        super(instance);
    }

    @Override
    public ItemStack getRepresentationItem() {
        return new ItemManager(Material.STONE_AXE, 1).setUnbreakable()
                .setDisplayName("§6Axe").build();
    }

    @Override
    public String getName() {
        return "§6Axe";
    }

    @Override
    public String getConfigKey() {
        return "axe";
    }

    @Override
    ItemStack getPrimaryWeapon() {
        return new ItemManager(Material.STONE_AXE, 1).setUnbreakable()
                .addEnchantment(Enchantment.DAMAGE_ALL, 1).build();
    }

    @Override
    ItemStack getSecondaryWeapon() {
        return new ItemManager(Material.BOW, 1).setUnbreakable()
                .build();
    }

    @Override
    ItemStack getRod() {
        return null;
    }

    @Override
    ItemStack getHelmet() {
        return new ItemManager(Material.LEATHER_HELMET, 1).setUnbreakable()
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build();
    }

    @Override
    ItemStack getChestplate() {
        return new ItemManager(Material.CHAINMAIL_CHESTPLATE, 1).setUnbreakable()
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build();
    }

    @Override
    ItemStack getLeggings() {
        return new ItemManager(Material.LEATHER_LEGGINGS, 1).setUnbreakable()
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build();
    }

    @Override
    ItemStack getBoots() {
        return new ItemManager(Material.GOLD_BOOTS, 1).setUnbreakable()
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build();
    }

    @Override
    List<ItemStack> getAdditionalItems() {
        List<ItemStack> items = new ArrayList<>();
        items.add(
                new ItemManager(Material.WEB, 3)
                        .build()
        );

        items.add(
                new ItemManager(Material.ARROW, 5)
                        .build()
        );

        return items;
    }
}
