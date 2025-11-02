package codes.jcli.advancementrace.events;

import codes.jcli.advancementrace.core.AchievementRacePlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
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

        final boolean newAdvancement = this.plugin.getAdvancementManager().awardAdvancementToAll(advancement.getKey());

        if (!newAdvancement) {
            event.message(Component.empty());
            return;
        }

        Player player = event.getPlayer();
        plugin.getAdvancementScoreboard().incrementForPlayer(player);

        Component advancementMessage = event.message();
        if (advancementMessage != null) {
            event.message(
                    advancementMessage
                    .append(Component.space())
                    .append(Component.text("+1 point!", NamedTextColor.DARK_AQUA)
                            .decorate(TextDecoration.BOLD))
            );
        }
    }
}
