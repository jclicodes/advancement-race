package codes.jcli.advancementrace.events;

import codes.jcli.advancementrace.core.AchievementRacePlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final AchievementRacePlugin plugin;

    public PlayerJoinListener(AchievementRacePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.plugin.getLogger().info("Player " + event.getPlayer().getName() + " joined the game");
        Player player = event.getPlayer();

        this.plugin.getAdvancementManager().syncPlayer(player);
        this.plugin.getLogger().info("Synced!");
    }
}
