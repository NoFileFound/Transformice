package org.transformice.properties.configs;

import com.google.gson.reflect.TypeToken;
import org.transformice.libraries.JsonLoader;
import org.transformice.properties.Property;

// Imports
import java.util.List;

public final class BadStringsConfig implements Property {
    private List<String> badStrings;

    @Override
    public Object getInstance() {
        return this.badStrings;
    }

    @Override
    public void loadFile() {
        this.badStrings = JsonLoader.loadJson("client/bad_words.json", new TypeToken<List<String>>() {}.getType());
    }

    @Override
    public void saveFile() {
        JsonLoader.saveJson("client/bad_words.json", this.badStrings);
    }
}