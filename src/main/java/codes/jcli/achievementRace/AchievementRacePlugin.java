package codes.jcli.achievementRace;

import org.bukkit.plugin.java.JavaPlugin;

public final class AchievementRacePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new AdvancementDoneListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
