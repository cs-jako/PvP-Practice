package net.crazy.pvpgame.events;

import com.github.juliarn.npc.NPC;
import com.github.juliarn.npc.event.PlayerNPCInteractEvent;
import com.github.juliarn.npc.modifier.AnimationModifier;
import net.crazy.pvpgame.Practice;
import net.crazy.pvpgame.kits.Kit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NPCEvents implements Listener {
    private final Practice instance;

    public NPCEvents(Practice instance) {
        this.instance = instance;
        Bukkit.getPluginManager().registerEvents(this, instance);
    }

    /**
     * Called when a player interacts with an NPC
     * Adds / removes the player from the queue
     */
    @EventHandler
    public void onInteraction(PlayerNPCInteractEvent event) {
        Player player = event.getPlayer();
        NPC npc = event.getNPC();

        // Preventing duplicate event firing caused by INTERACT and INTERACT_AT
        if (event.getUseAction().equals(PlayerNPCInteractEvent.EntityUseAction.INTERACT_AT))
            return;

        if (event.getUseAction().equals(PlayerNPCInteractEvent.EntityUseAction.ATTACK))
            npc.animation().queue(AnimationModifier.EntityAnimation.SWING_MAIN_ARM);

        if (player.getItemInHand() == null || player.getItemInHand().getType().equals(Material.AIR))
            return;

        Kit kit = instance.getKit(player.getItemInHand());
        instance.queue.queue(player, kit);
    }
}
