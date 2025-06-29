package org.transformice.libraries;

// Imports
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.transformice.Application;

public final class JsonLoader {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Loads JSON file and deserialize into a given class.
     * @param fileName The file to load.
     * @param clazz The class.
     * @return Class object deserialized by given json file or null if that json file does not exist.
     */
    public static <T> T loadJson(String fileName, Class<T> clazz) {
        try {
            return gson.fromJson(new String(Files.readAllBytes(Paths.get("config/"+fileName))), clazz);
        } catch (IOException ignored) {
            Application.getLogger().error(String.format("Unable to load the configuration file %s.", fileName));
            return null;
        }
    }

    /**
     * Loads JSON file and deserialize into a given class.
     * @param fileName The file to load.
     * @param type The Type.
     * @return Class object deserialized by given json file or null if that json file does not exist.
     */
    public static <T> T loadJson(String fileName, Type type) {
        try {
            return gson.fromJson(new String(Files.readAllBytes(Paths.get("config/" + fileName))), type);
        } catch (IOException ignored) {
            Application.getLogger().error(String.format("Unable to load the configuration file %s.", fileName));
            return null;
        }
    }

    /**
     * Saves Java object as JSON file.
     * @param fileName The file to save.
     * @param object The object to save.
     */
    public static <T> void saveJson(String fileName, T object) {
        try (Writer writer = new FileWriter(Paths.get("config/" + fileName).toFile())) {
            gson.toJson(object, writer);
        } catch (IOException ignored) {
            Application.getLogger().error(String.format("Unable to save the configuration file %s.", fileName));
        }
    }
}