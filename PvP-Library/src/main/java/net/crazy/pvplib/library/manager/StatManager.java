package net.crazy.pvplib.library.manager;

import lombok.Getter;
import lombok.Setter;
import net.crazy.pvplib.library.MCLogger;
import net.crazy.pvplib.library.files.FileManager;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Class handling the Stat per Player
 */
public class StatManager {
    @Getter
    private final Player player;
    private final FileManager fileManager;
    private JSONObject config;

    private JSONObject data;

    private JSONObject stats;

    @Getter
    @Setter
    private int kills;

    @Getter
    @Setter
    private int deaths;

    @Getter
    @Setter
    private int wins;

    @Getter
    @Setter
    private int losses;

    @Getter
    @Setter
    private int gamesPlayed;



    public StatManager(Player player) {
        this.player = player;
        this.fileManager = new FileManager("plugins//PvP-Practice//stats//" + player.getUniqueId() + ".json");

        if (!this.fileManager.fileExists()) {
            fileManager.createFile();
            fileManager.write(getDefaultStats().toString());
        }

        this.config = fileManager.getJSON();
        this.data = config.getJSONObject("data");
        this.stats = config.getJSONObject("stats");

        this.kills = stats.getInt("kills");
        this.deaths = stats.getInt("deaths");
        this.wins = stats.getInt("wins");
        this.losses = stats.getInt("losses");
        this.gamesPlayed = stats.getInt("games_played");
    }


    public void update() {
        this.config.clear();
        this.config.put("data", data);

        JSONObject stats = new JSONObject();
        stats.put("kills", this.kills);
        stats.put("deaths", this.deaths);
        stats.put("wins", this.wins);
        stats.put("losses", this.losses);
        stats.put("games_played", this.gamesPlayed);
        this.config.put("stats", stats);

        this.fileManager.write(stats.toString());
    }

    /**
     * Creating the default stats of a player
     *
     * @return default stats
     */
    private JSONObject getDefaultStats() {
        JSONObject config = new JSONObject();

        JSONObject data = new JSONObject();
        data.put("uuid", player.getUniqueId().toString());
        data.put("first_join", Calendar.getInstance().getTime());
        config.put("data", data);

        JSONObject stats = new JSONObject();
        stats.put("kills", 0);
        stats.put("deaths", 0);
        stats.put("wins", 0);
        stats.put("losses", 0);
        stats.put("games_played", 0);
        config.put("stats", stats);

        return config;
    }
}
