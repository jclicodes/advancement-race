package codes.jcli.advancementrace.core;

import codes.jcli.advancementrace.advancements.AdvancementAwarder;
import codes.jcli.advancementrace.advancements.AdvancementManager;
import codes.jcli.advancementrace.advancements.AdvancementRepository;
import codes.jcli.advancementrace.events.AdvancementDoneListener;
import codes.jcli.advancementrace.events.PlayerJoinListener;
import codes.jcli.advancementrace.ui.AdvancementScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.ScoreboardManager;


public final class AchievementRacePlugin extends JavaPlugin {
    private ScoreboardManager scoreboardManager;
    private AdvancementManager advancementManager;
    private AdvancementScoreboard advancementScoreboard;

    @Override
    public void onEnable() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new AdvancementDoneListener(this), this);
        pluginManager.registerEvents(new PlayerJoinListener(this), this);

        if (this.scoreboardManager == null) {
            this.scoreboardManager = Bukkit.getScoreboardManager();
        }
        this.advancementScoreboard = new AdvancementScoreboard(this.scoreboardManager.getMainScoreboard());

        World world = Bukkit.getWorld("world");
        if (world == null) {
            getLogger().severe("World not loaded!");
            return;
        }

        AdvancementRepository repository = new AdvancementRepository(world);
        AdvancementAwarder awarder = new AdvancementAwarder();
        this.advancementManager = new AdvancementManager(repository, awarder);

        advancementManager.rebuildFromPlayerData();
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
