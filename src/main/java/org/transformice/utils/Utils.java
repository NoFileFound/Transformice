package org.transformice.utils;

// Imports
import java.util.HashMap;
import java.util.Map;
import org.transformice.Application;

public final class Utils {
    /**
     * Builds a map of language communities and their corresponding flags.
     * @return A {@code Map<String, String>} where the keys are language community names and the values are their associated flag representations.
     */
    public static Map<String, String> buildLanguageMap() {
        Map<String, String> result = new HashMap<>();

        for (Map.Entry<String, String[]> entry : Application.getLanguageInfo().entrySet()) {
            String community = entry.getKey();
            String[] info = entry.getValue();

            String flag = info[1];
            result.put(community, flag);
        }
        return result;
    }

    /**
     * Gets the unix timestamp.
     * @return Seconds.
     */
    public static long getUnixTime() {
        return System.currentTimeMillis() / 1000;
    }
}