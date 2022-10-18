package net.crazy.pvpgame.util;

import com.github.juliarn.npc.NPC;
import com.github.juliarn.npc.event.PlayerNPCShowEvent;
import com.github.juliarn.npc.modifier.MetadataModifier;
import com.github.juliarn.npc.profile.Profile;
import net.crazy.pvpgame.Practice;
import net.crazy.pvplib.library.MCLogger;
import net.crazy.pvplib.library.files.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Handles the default settings of NPCs
 */
public class NPCUtil implements Listener {
    private final Practice instance;
    private final ConfigManager configManager;

    public NPCUtil(Practice instance) {
        this.instance = instance;

        String filePath = instance.lobby.getWorldFolder().getPath();
        configManager = new ConfigManager(filePath + "/config.yml");

        if (!configManager.fileExists()) {
            MCLogger.log(MCLogger.LogType.ERROR, "Please create a config.yml file in the base folder of the lobby.");
            MCLogger.log(MCLogger.LogType.ERROR, "In path: " + instance.getConfig().getString("lobby"));
            MCLogger.log(MCLogger.LogType.ERROR, "If you need help setting it up, please read the wiki.");
            MCLogger.log(MCLogger.LogType.ERROR, "Without the config, most features won't work.");
            return;
        }

        this.npcName = configManager.getString("npc-name");
        this.npcName = ChatColor.translateAlternateColorCodes('&', this.npcName);

        Bukkit.getPluginManager().registerEvents(this, instance);
    }

    private final ArrayList<String> npcSkins = new ArrayList<>();
    private String npcName;

    /**
     * Loads the NPCs to their designated locations
     */
    public void loadNPCs() {
        npcSkins.addAll(configManager.getStringList("npc-skins"));

        // iteration is used to get a new skin from the config
        int iteration = 0;
        for (Location location : instance.npcLocations) {
            if (iteration == npcSkins.size())
                iteration = 0;

            // Creates the npc at its location with its settings
            NPC.builder()
                    .profile(createProfile(npcSkins.get(iteration), this.npcName))
                    .location(location.add(0.5, 0, 0.5))
                    .lookAtPlayer(true)
                    .imitatePlayer(false)
                    .build(instance.npcPool);

            iteration++;
        }
    }

    /**
     * Creates a profile with the skin of a player by using the uuid
     * Assigns a random UUID
     * @param uuid          The uuid of the player whom skin will be used
     * @param displayName   The name of the npc
     * @return              NPC-Profile based on the settings
     */
    private Profile createProfile(String uuid, String displayName) {
        Profile profile = new Profile(UUID.fromString(uuid));
        profile.complete();

        profile.setName(displayName);
        profile.setUniqueId(UUID.randomUUID());

        return profile;
    }

    /**
     * Enabling the Skin layers when the npc is shown to a player
     */
    @EventHandler
    public void onNPCShow(PlayerNPCShowEvent event) {
        NPC npc = event.getNPC();

        event.send(
                npc.metadata()
                        .queue(MetadataModifier.EntityMetadata.SKIN_LAYERS, true)
        );
    }
}
