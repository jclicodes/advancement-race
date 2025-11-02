package codes.jcli.advancementrace.advancements;

import codes.jcli.advancementrace.model.AdvancementPojo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import org.bukkit.NamespacedKey;
import org.bukkit.World;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AdvancementRepository {

    private static final Type ADVANCEMENT_MAP_TYPE =
            new TypeToken<Map<String, AdvancementPojo>>() {}.getType();

    private final Gson gson;
    private final File advancementsDirectory;

    public AdvancementRepository(World world) {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(
                        new TypeToken<Map<String, AdvancementPojo>>() {}.getType(),
                        new AdvancementFileDeserializer()
                )
                .create();
        this.advancementsDirectory = new File(world.getWorldFolder(), "advancements");
    }

    public Set<NamespacedKey> loadAllCompletedAdvancements() {
        Set<NamespacedKey> result = new HashSet<>();
        File[] files = getPlayerAdvancementFiles();

        if (files == null) return result;

        for (File file : files) {
            result.addAll(loadCompletedAdvancementsFromFile(file));
        }

        return result;
    }

    private Set<NamespacedKey> loadCompletedAdvancementsFromFile(File file) {
        Set<NamespacedKey> result = new HashSet<>();

        try (Reader reader = new FileReader(file)) {
            Map<String, AdvancementPojo> advancements =
                    gson.fromJson(reader, ADVANCEMENT_MAP_TYPE);

            if (advancements != null) {
                extractCompletedAdvancements(advancements, result);
            }
        } catch (IOException | JsonParseException ex) {
            System.err.println("Failed to parse " + file.getName() + ": " + ex.getMessage());
        }

        return result;
    }

    private void extractCompletedAdvancements(
            Map<String, AdvancementPojo> advancements,
            Set<NamespacedKey> out
    ) {
        for (Map.Entry<String, AdvancementPojo> entry : advancements.entrySet()) {
            AdvancementPojo pojo = entry.getValue();
            if (pojo == null || !pojo.done) continue;

            try {
                NamespacedKey key = NamespacedKey.fromString(entry.getKey());
                if (key != null) out.add(key);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private File[] getPlayerAdvancementFiles() {
        if (!advancementsDirectory.exists()) return null;
        return advancementsDirectory.listFiles((dir, name) -> name.endsWith(".json"));
    }
}
