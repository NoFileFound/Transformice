package org.transformice.properties.configs;

// Imports
import com.google.gson.reflect.TypeToken;
import java.util.Map;
import org.transformice.libraries.JsonLoader;
import org.transformice.properties.Property;

public final class CaptchaConfig implements Property {
    private Map<String, Map<String, String>> captchaInstance;

    @Override
    public Object getInstance() {
        return this.captchaInstance;
    }

    @Override
    public void loadFile() {
        this.captchaInstance = JsonLoader.loadJson("server/captcha.json", new TypeToken<Map<String, Map<String, String>>>() {}.getType());
        if(this.captchaInstance == null) {
            System.exit(1);
        }
    }

    @Override
    public void saveFile() {

    }
}