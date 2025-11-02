package codes.jcli.advancementrace.advancements;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;

import java.util.Set;

public class AdvancementAwarder {
    public void awardToPlayer(Player player, NamespacedKey advancementKey) {
        Advancement advancement = Bukkit.getAdvancement(advancementKey);
        if (advancement == null) return;

        AdvancementProgress progress = player.getAdvancementProgress(advancement);
        if (progress.isDone()) return;

        for (String criteria : progress.getRemainingCriteria()) {
            progress.awardCriteria(criteria);
        }
    }

    public void awardToAllOnline(NamespacedKey advancementKey) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            awardToPlayer(player, advancementKey);
        }
    }

    public void awardMultipleToPlayer(Player player, Set<NamespacedKey> keys) {
        for (NamespacedKey key : keys) {
            awardToPlayer(player, key);
        }
    }
}
