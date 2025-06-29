package org.transformice.utils;

// Imports
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Deflater;
import org.transformice.Application;
import org.transformice.libraries.SrcRandom;

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
     * Compressed the bytes using zlib.
     * @param data The given bytes.
     * @return The compressed bytes.
     */
    public static byte[] compressZlib(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        deflater.finish();
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            outputStream.write(buffer, 0, deflater.deflate(buffer));
        }

        return outputStream.toByteArray();
    }

    /**
     * Gets the community id from the country language.
     * @param language The country language.
     * @return The community iso2 code.
     */
    public static String getCommunityFromLanguage(String language) {
        return switch (language) {
            case "AF" -> "ZA";
            case "MS" -> "MY";
            case "BI" -> "VU";
            case "BS" -> "BA";
            case "CA" -> "AD";
            case "NY" -> "MW";
            case "DA" -> "DK";
            case "ET" -> "EE";
            case "NA" -> "NR";
            case "EN" -> "GB";
            case "SM" -> "WS";
            case "KL" -> "GL";
            case "RN" -> "BI";
            case "SW" -> "KE";
            case "LB" -> "LU";
            case "QU" -> "BO";
            case "ST" -> "LS";
            case "TN" -> "BW";
            case "SQ" -> "AL";
            case "SS" -> "SZ";
            case "SL" -> "SI";
            case "SV" -> "SE";
            case "TL" -> "PH";
            case "VI" -> "VN";
            case "TK" -> "TM";
            case "WO" -> "SN";
            case "YO" -> "NG";
            case "CS" -> "CZ";
            case "EL" -> "GR";
            case "BE" -> "BY";
            case "KY" -> "KG";
            case "SR" -> "RS";
            case "TG" -> "TJ";
            case "UK" -> "UA";
            case "KK" -> "KZ";
            case "HY" -> "AM";
            case "HE" -> "IL";
            case "UR" -> "PK";
            case "AR" -> "IAR";
            case "FA" -> "IR";
            case "DV" -> "MV";
            case "NE" -> "NP";
            case "HI" -> "IN";
            case "BN" -> "BD";
            case "TA" -> "LK";
            case "LO" -> "LA";
            case "DZ" -> "BT";
            case "MY" -> "MM";
            case "KA" -> "GE";
            case "TI" -> "ER";
            case "AM" -> "ET";
            case "KM" -> "KH";
            case "ZH" -> "HK";
            case "JA" -> "JP";
            case "KO" -> "KR";
            default -> language;
        };
    }

    /**
     * Gets the unix timestamp.
     * @return Seconds.
     */
    public static long getUnixTime() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * Gets the time in minutes.
     */
    public static int getTribulleTime() {
        return (int) (System.currentTimeMillis() / 60000);
    }

    /**
     * Gets the video id from the link.
     * @param url The YouTube video link.
     * @return The video id.
     */
    public static String getYoutubeID(String url) {
        Matcher matcher = Pattern.compile(".*(?:youtu.be/|v/|u/\\w/|embed/|watch\\?v=)([^#&?]*).*").matcher(url);
        return matcher.matches() ? matcher.group(1) : null;
    }
    /**
     * Formats the text and censor the bad words.
     * @param text The text to format.
     * @return A formatted text without bad words.
     */
    public static String formatText(String text) {
        String symbols = "!@#$%&^";

        for (String word : Application.getBadWordsConfig()) {
            String replacement = word.chars()
                    .mapToObj(_ -> String.valueOf(symbols.charAt(SrcRandom.RandomNumber(0, symbols.length() - 1))))
                    .reduce("", String::concat);

            text = text.replaceAll("(?i)\\b" + word + "\\b", replacement);
        }
        return text;
    }

    /**
     * Formats the room name by uppercasing or lowercasing the room community.
     * @param roomName The room name.
     * @return The formatted room.
     */
    public static String formatRoomName(String roomName) {
        if(roomName.startsWith("*") || roomName.isEmpty()) return roomName;

        return roomName.substring(0, roomName.indexOf('-')).transform(String::toUpperCase) + roomName.substring(roomName.indexOf('-'));
    }

    /**
     * Formats the timestamp into date.
     * @param unixTime Unix timestamp.
     * @param pattern Pattern to format.
     * @return A formated date.
     */
    public static String formatUnixTime(long unixTime, String pattern) {
        LocalDateTime dateTime = Instant.ofEpochSecond(unixTime).atZone(ZoneId.systemDefault()).toLocalDateTime();
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Searches for a file in given directory (recursively).
     * @param directory The base directory.
     * @param fileName The file name.
     * @return File object if exist or else null.
     */
    public static File searchFile(File directory, String fileName) {
        File[] files = directory.listFiles();
        if (files == null) return null;

        for (File file : files) {
            if (file.isDirectory()) {
                File found = searchFile(file, fileName);
                if (found != null) {
                    return found;
                }
            } else if (file.getName().equalsIgnoreCase(fileName)) {
                return file;
            }
        }
        return null;
    }
}