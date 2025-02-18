package org.transformice.properties;

// Imports
import com.google.gson.JsonSyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.transformice.Application;

public final class ConfigLoader {
    private static final Map<Class<? extends Property>, Property> properties = new HashMap<>();

    /**
     * Loads every configuration.
     */
    public static void loadConfig() {
        Set<Class<? extends Property>> classes = Application.getReflector().getSubTypesOf(Property.class);
        for (Class<? extends Property> clazz : classes) {
            try {
                Property instance = clazz.getDeclaredConstructor().newInstance();
                instance.loadFile();
                properties.put(clazz, instance);
            } catch (JsonSyntaxException ignored) {
                Application.getLogger().error(String.format("Wrong json syntax in the file %s", clazz.getSimpleName()));
                System.exit(1);
            } catch (Exception ignored) {
                Application.getLogger().error(String.format("Failed to instantiate %s", clazz.getSimpleName()));
            }
        }
    }

    /**
     * Get loaded instance of a specific config class.
     * @param clazz The config class.
     * @return The instance.
     */
    public static <T extends Property> T getProperty(Class<T> clazz) {
        return clazz.cast(properties.get(clazz));
    }
}