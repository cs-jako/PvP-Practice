package net.crazy.pvpgame.kits;

import net.crazy.pvpgame.Practice;
import net.crazy.pvplib.library.manager.ItemManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class RodKit extends Kit {
    public RodKit(Practice instance) {
        super(instance);
    }

    @Override
    public ItemStack getRepresentationItem() {
        return new ItemManager(Material.FISHING_ROD, 1)
                .setDisplayName("§6Rod").setUnbreakable().build();
    }

    @Override
    public String getName() {
        return "§6Rod";
    }

    @Override
    ItemStack getPrimaryWeapon() {
        return new ItemManager(Material.IRON_SWORD, 1).setUnbreakable().build();
    }

    @Override
    ItemStack getSecondaryWeapon() {
        return null;
    }

    @Override
    ItemStack getRod() {
        return new ItemManager(Material.FISHING_ROD, 1).build();
    }

    @Override
    ItemStack getHelmet() {
        return new ItemManager(Material.IRON_HELMET, 1).build();
    }

    @Override
    ItemStack getChestplate() {
        return new ItemManager(Material.IRON_CHESTPLATE, 1).build();
    }

    @Override
    ItemStack getLeggings() {
        return new ItemManager(Material.IRON_LEGGINGS, 1).build();
    }

    @Override
    ItemStack getBoots() {
        return new ItemManager(Material.IRON_BOOTS, 1).build();
    }

    @Override
    List<ItemStack> getAdditionalItems() {
        return null;
    }
}
