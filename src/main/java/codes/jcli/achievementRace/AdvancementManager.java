package codes.jcli.achievementRace;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AdvancementManager {
    private final Set<NamespacedKey> unlockedAdvancements = new HashSet<>();
    private final AchievementRacePlugin plugin;
    private final Gson gson;

    public AdvancementManager(AchievementRacePlugin plugin) {
        this.plugin = plugin;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(
                        new TypeToken<Map<String, AdvancementPojo>>() {}.getType(),
                        new AdvancementFileDeserializer()
                )
                .create();
    }

    public void rebuildFromPlayerData() throws IllegalStateException {
        unlockedAdvancements.clear();

        final World defaultWorld = Bukkit.getWorld("world");
        if (defaultWorld == null) {
            throw new IllegalStateException("Couldn't find player advancements. World has not been created yet!");
        }

        final File advancementsDir = new File(defaultWorld.getWorldFolder(), "advancements");
        final File[] playerAdvancementFiles = advancementsDir.listFiles((file, name) -> name.endsWith(".json"));
        if (!advancementsDir.exists() || playerAdvancementFiles == null) {
            // leave hash set empty if no advancements have been earned
            return;
        }

        for (File playerFile : playerAdvancementFiles) {
            try (Reader reader = new FileReader(playerFile)) {
                Type type = new TypeToken<Map<String, AdvancementPojo>>() {}.getType();
                Map<String, AdvancementPojo> advancements = this.gson.fromJson(reader, type);
                for (Map.Entry<String, AdvancementPojo> advancementEntry : advancements.entrySet()) {
                    String advancementId = advancementEntry.getKey();
                    AdvancementPojo advancement = advancementEntry.getValue();
                    if (advancement.done) {
                        unlockedAdvancements.add(NamespacedKey.fromString(advancementId));
                    }
                }
            } catch (IOException ex) {
                plugin.getLogger().warning(
                        "Failed to parse " + playerFile.getName() + ": " + ex.getMessage()
                );
            }
        }
    }

    public void syncPlayer(Player player) {
        for (NamespacedKey key : unlockedAdvancements) {
            awardAdvancementToPlayer(player, key);
        }
    }

    public void awardAdvancementToAll(NamespacedKey advancementKey) {
        if (unlockedAdvancements.add(advancementKey)) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                awardAdvancementToPlayer(player, advancementKey);
            }
        }
    }

    private void awardAdvancementToPlayer(Player player, NamespacedKey advancementKey) {
        Advancement advancement = Bukkit.getAdvancement(advancementKey);
        if (advancement == null) return;

        AdvancementProgress progress = player.getAdvancementProgress(advancement);
        if (!progress.isDone()) {
            for (String criteria : progress.getRemainingCriteria()) {
                progress.awardCriteria(criteria);
            }
        }

    }
}

