package codes.jcli.advancementrace.advancements;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class AdvancementManager {
    private final Set<NamespacedKey> unlockedAdvancements = new HashSet<>();
    private final AdvancementRepository repository;
    private final AdvancementAwarder awarder;

    public AdvancementManager(AdvancementRepository repository, AdvancementAwarder awarder) {
        this.repository = repository;
        this.awarder = awarder;
    }

    public void rebuildFromPlayerData() {
        Set<NamespacedKey> loaded = repository.loadAllCompletedAdvancements();
        unlockedAdvancements.clear();
        unlockedAdvancements.addAll(loaded);
    }

    public void syncPlayer(Player player) {
        awarder.awardMultipleToPlayer(player, unlockedAdvancements);
    }

    public void awardAdvancementToAll(NamespacedKey advancementKey) {
        if (unlockedAdvancements.add(advancementKey)) {
            awarder.awardToAllOnline(advancementKey);
        }
    }

    public Set<NamespacedKey> getUnlockedAdvancements() {
        return new HashSet<>(unlockedAdvancements); // defensive copy
    }
}
