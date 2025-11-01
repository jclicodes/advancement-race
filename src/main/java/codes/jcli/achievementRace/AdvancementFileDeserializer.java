package codes.jcli.achievementRace;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class AdvancementFileDeserializer implements JsonDeserializer<Map<String, AdvancementPojo>> {
    @Override
    public Map<String, AdvancementPojo> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final Map<String, AdvancementPojo> result = new HashMap<>();
        final JsonObject root = jsonElement.getAsJsonObject();

        for (Map.Entry<String, JsonElement> entry : root.entrySet()) {
            JsonElement value = entry.getValue();
            // skip DataVersion and any other non-advancement primitives
            if (!jsonElement.isJsonObject()) continue;
            AdvancementPojo advancement = jsonDeserializationContext.deserialize(value, AdvancementPojo.class);
            if (advancement != null) result.put(entry.getKey(), advancement);
        }
        return result;
    }
}
