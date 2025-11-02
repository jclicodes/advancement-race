package codes.jcli.advancementrace.events;

import codes.jcli.advancementrace.core.AchievementRacePlugin;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class AdvancementDoneListener implements Listener {
    private final AchievementRacePlugin plugin;

    public AdvancementDoneListener(AchievementRacePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAdvancementDone(PlayerAdvancementDoneEvent event) {
        Advancement advancement = event.getAdvancement();
        boolean isCountableAdvancement = advancement.getDisplay() != null && advancement.getParent() != null;

        // ignore root advancements, recipes, etc.
        if (!isCountableAdvancement) return;

        plugin.getLogger().info(String.format("Awarding advancement %s to all players...", advancement.getKey().toString()));
        final boolean newAdvancement = this.plugin.getAdvancementManager().awardAdvancementToAll(advancement.getKey());

        if (newAdvancement) {
            // update scoreboard conditionally
            Player player = event.getPlayer();
            plugin.getLogger().info("Advancement " + advancement.getDisplay().toString() + " for " + player.getName() + " is countable!");
            plugin.getAdvancementScoreboard().incrementForPlayer(player);
        }
    }
}
