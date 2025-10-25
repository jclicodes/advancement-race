package codes.jcli.achievementRace;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;


public final class AchievementRacePlugin extends JavaPlugin {
    private ScoreboardManager scoreboardManager;
    private AdvancementScoreboard advancementScoreboard;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new AdvancementDoneListener(this), this);

        // setup scoreboard with advancements
        if (this.scoreboardManager == null) {
            this.scoreboardManager = Bukkit.getScoreboardManager();
        }
        this.advancementScoreboard = new AdvancementScoreboard(this.scoreboardManager.getMainScoreboard());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public AdvancementScoreboard getAdvancementScoreboard() {
        return this.advancementScoreboard;
    }
}
