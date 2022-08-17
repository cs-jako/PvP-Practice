package net.crazy.pvplib.library.player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionBarManager {
    private final Player player;
    private String message;

    public ActionBarManager(Player player, String message) {
        this.player = player;
        this.message = message;
        send();
    }

    private void send() {
        this.message = this.message.replace("_", "");
        this.message = ChatColor.translateAlternateColorCodes('&', this.message);

        IChatBaseComponent component = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + this.message + "\"}");
        PacketPlayOutChat bar = new PacketPlayOutChat(component, (byte) 2);
        (((CraftPlayer) player).getHandle()).playerConnection.sendPacket(bar);
    }
}
