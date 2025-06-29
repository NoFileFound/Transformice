package org.transformice.properties.configs;

// Imports
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.Map;
import org.transformice.libraries.JsonLoader;
import org.transformice.properties.Property;

public final class JapanExpoConfig implements Property {
    private Map<String, Code> japanExpoInstance;

    @Override
    public Object getInstance() {
        return this.japanExpoInstance;
    }

    @Override
    public void loadFile() {
        this.japanExpoInstance = JsonLoader.loadJson("client/codes.json", new TypeToken<Map<String, Code>>() {}.getType());
    }

    @Override
    public void saveFile() {
        JsonLoader.saveJson("server/codes.json", this.japanExpoInstance);
    }

    public static class Code {
        public List<Map<String, Integer>> prize;
    }
}