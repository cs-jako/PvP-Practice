package net.crazy.pvpgame.kits;

import net.crazy.pvpgame.Practice;
import net.crazy.pvplib.library.manager.ItemManager;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BowKit extends Kit {
    public BowKit(Practice instance) {
        super(instance);
    }

    @Override
    public ItemStack getRepresentationItem() {
        return new ItemManager(Material.BOW, 1).setDisplayName("§6Bow").setUnbreakable().build();
    }

    @Override
    public String getName() {
        return "§6Bow";
    }

    @Override
    public String getConfigKey() {
        return "bow";
    }

    @Override
    ItemStack getPrimaryWeapon() {
        return new ItemManager(Material.BOW, 1).setDisplayName("§6Bow")
                .addEnchantment(Enchantment.ARROW_KNOCKBACK, 1)
                .setUnbreakable()
                .build();
    }

    @Override
    ItemStack getSecondaryWeapon() {
        return new ItemManager(Material.ARROW, 1).setDisplayName("§7Arrow")
                .setUnbreakable()
                .build();
    }

    @Override
    ItemStack getRod() {
        return null;
    }

    @Override
    ItemStack getHelmet() {
        return new ItemManager(Material.CHAINMAIL_HELMET, 1).build();
    }

    @Override
    ItemStack getChestplate() {
        return new ItemManager(Material.CHAINMAIL_CHESTPLATE, 1).build();
    }

    @Override
    ItemStack getLeggings() {
        return new ItemManager(Material.CHAINMAIL_LEGGINGS, 1).build();
    }

    @Override
    ItemStack getBoots() {
        return new ItemManager(Material.CHAINMAIL_BOOTS, 1).build();
    }

    @Override
    List<ItemStack> getAdditionalItems() {
        return null;
    }
}
