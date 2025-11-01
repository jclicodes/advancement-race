package codes.jcli.achievementRace;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;


public final class AchievementRacePlugin extends JavaPlugin {
    private ScoreboardManager scoreboardManager;
    private AdvancementManager advancementManager;
    private AdvancementScoreboard advancementScoreboard;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new AdvancementDoneListener(this), this);

        if (this.scoreboardManager == null) {
            this.scoreboardManager = Bukkit.getScoreboardManager();
        }
        this.advancementScoreboard = new AdvancementScoreboard(this.scoreboardManager.getMainScoreboard());

        this.advancementManager = new AdvancementManager(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public AdvancementManager getAdvancementManager() {
        return this.advancementManager;
    }

    public AdvancementScoreboard getAdvancementScoreboard() {
        return this.advancementScoreboard;
    }
}
