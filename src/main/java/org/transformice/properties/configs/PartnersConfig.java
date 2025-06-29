package org.transformice.properties.configs;

// Imports
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.google.gson.reflect.TypeToken;
import org.transformice.libraries.JsonLoader;
import org.transformice.properties.Property;

public final class PartnersConfig implements Property {
    private List<Map<String, String>> partnersInstance;

    @Override
    public Object getInstance() {
        return this.partnersInstance;
    }

    @Override
    public void loadFile() {
        this.partnersInstance = JsonLoader.loadJson("server/partners.json", new TypeToken<List<Map<String, String>>>() {}.getType());
        if (this.partnersInstance == null) {
            this.partnersInstance = new ArrayList<>();
        }
    }

    @Override
    public void saveFile() {

    }
}