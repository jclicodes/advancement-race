package codes.jcli.achievementRace;

import org.bukkit.advancement.Advancement;
import org.bukkit.event.EventHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class AdvancementDoneListener implements Listener {

    @EventHandler
    public void onAdvancementDone(PlayerAdvancementDoneEvent event) {
        Advancement advancement = event.getAdvancement();
        if (advancement.getDisplay() != null && advancement.getParent() != null) {
            String player = event.getPlayer().getName();
            String key = advancement.getKey().asString();

            Bukkit.getLogger().info("[AdvTest] " + player + " completed: " + key);
            event.getPlayer().sendMessage("[AdvTest] You completed: " + key);
        }
    }
}
