package org.transformice.utils;

// Imports
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.transformice.data.objects.StaffRoleType;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public final class Utils {
    /**
     * Checks if the XML is valid.
     * @param xml The XML.
     * @return True if the XML is valid or else False.
     */
    public static boolean checkValidXML(String xml) {
        try {
            DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
            return true;

        } catch (ParserConfigurationException | SAXException | IOException error) {
            return false;
        }
    }

    /**
     * Colors the given IP address.
     * @param ipAddress The given IP address.
     * @return The IP address's color.
     */
    public static String colorIP(String ipAddress) {
        ipAddress = ipAddress.startsWith("#") ? ipAddress.substring(1) : ipAddress;
        String[] components = ipAddress.split("\\.");
        StringBuilder concatenated = new StringBuilder();
        for (String component : components)
            concatenated.append(component);

        while (concatenated.length() < 8)
            concatenated.append("0");

        concatenated.setLength(8);
        int r = Integer.parseInt(concatenated.substring(0, 2), 16);
        int g = Integer.parseInt(concatenated.substring(2, 4), 16);
        int b = Integer.parseInt(concatenated.substring(4, 6), 16);
        return String.format("#%02X%02X%02X", r, g, b);
    }

    /**
     * Converts the hex ip to normal ip address.
     * @param ip Hex form ip address.
     * @return A normal ip address.
     */
    public static String decodeIP(String ip) {
        if (ip.isEmpty())
            return "(offline)";

        ip = ip.substring(1);
        String[] parts = ip.split("\\.");
        StringBuilder decodedIP = new StringBuilder();

        for (String part : parts) {
            int value = Integer.parseInt(part, 16);
            decodedIP.append(value).append(".");
        }

        return decodedIP.substring(0, decodedIP.length() - 1);
    }

    /**
     * Converts the IP address to HEX format.
     * @param ipAddress The given IP address.
     * @return A HEX format of the ip address.
     */
    public static String encodeIP(String ipAddress) {
        if (ipAddress.isEmpty())
            return "";

        String[] parts = ipAddress.split("\\.");
        StringBuilder encodedIP = new StringBuilder("#");
        for (String part : parts) {
            encodedIP.append(String.format("%02X", Integer.parseInt(part)));
            if (!part.equals(parts[parts.length - 1])) {
                encodedIP.append(".");
            }
        }

        return encodedIP.toString();
    }

    /**
     * Formats the room name.
     * @param roomName The room name to format.
     * @param roomCommunity The room's community.
     * @param isStaff Is the player a staff member.
     * @return The formatted room name.
     */
    public static String formatRoomName(String roomName, String roomCommunity, boolean isStaff) {
        if (roomName.isEmpty())
            return roomName;

        roomCommunity = getCommunityFromLanguage(roomCommunity).toUpperCase();
        roomName = roomName.replace("<", "lt;");
        int sep = roomName.indexOf('-');
        if (sep != -1) {
            String prefix = roomName.substring(0, sep).toUpperCase();
            String name = roomName.substring(sep + 1);
            if (name.startsWith("*"))
                return "INT-" + name;

            if (isStaff || prefix.equals(roomCommunity))
                return roomName;

            if (!getCommunityFromLanguage(prefix).equals("INT"))
                roomName = name;
        }

        return (roomName.startsWith("*") ? "INT" : roomCommunity) + '-' + roomName;
    }

    /**
     * Searches if string contains any html url, tags or clickque event.
     * @param text The given string.
     * @return 1 if string contains url, 2 if contains clickque event, 3 if contains img tag or else 0.
     */
    public static int filterHtml(String text) {
        Matcher matcher = Pattern.compile("href=([\"'])(.*?)([\"'])").matcher(text);
        if (matcher.find()) {
            String url = matcher.group(2);
            if (url.contains("http")) {
                return 1;
            } else if (!url.startsWith("event:")) {
                return 2;
            }
        }

        if (text.contains("<img"))
            return 3;

        return 0;
    }

    /**
     * Gets the game community based on the spoken language.
     * @param languageCode The language's iso 2 code.
     * @return The community.
     */
    public static String getCommunityFromLanguage(String languageCode) {
        return switch (languageCode.toUpperCase()) {
            case "AF" -> "ZA";
            case "AZ" -> "AZ";
            case "ID" -> "ID";
            case "UR" -> "PK";
            case "BI" -> "VU";
            case "MS" -> "MY";
            case "CA" -> "ES";
            case "DE" -> "DE";
            case "EN" -> "GB";
            case "ES" -> "ES";
            case "FR" -> "FR";
            case "HR" -> "HR";
            case "IT" -> "IT";
            case "SW" -> "TZ";
            case "NL" -> "NL";
            case "PT" -> "PT";
            case "RO" -> "RO";
            case "QU" -> "PE";
            case "ST" -> "ZA";
            case "TN" -> "ZA";
            case "SQ" -> "AL";
            case "SS" -> "SZ";
            case "ZH" -> "CN";
            case "TI" -> "ET";
            case "TA" -> "IN";
            case "BN" -> "BD";
            case "HI" -> "IN";
            case "NE" -> "NP";
            case "FA" -> "IR";
            case "AR" -> "SA";
            case "SR" -> "RS";
            case "RU" -> "RU";
            case "EL" -> "GR";
            case "YO" -> "NG";
            case "TR" -> "TR";
            case "SV" -> "SE";
            case "SO" -> "SO";
            case "BS" -> "BA";
            case "NY" -> "MW";
            case "DA" -> "DK";
            case "ET" -> "EE";
            case "NA" -> "NR";
            case "TO" -> "TO";
            case "MG" -> "MG";
            case "SM" -> "WS";
            case "MH" -> "MH";
            case "KL" -> "GL";
            case "RN" -> "BI";
            case "RW" -> "RW";
            case "HT" -> "HT";
            case "LV" -> "LV";
            case "LT" -> "LT";
            case "LB" -> "LU";
            case "HU" -> "HU";
            case "MT" -> "MT";
            case "NO" -> "NO";
            case "UZ" -> "UZ";
            case "PL" -> "PL";
            case "BR" -> "BR";
            case "SK" -> "SK";
            case "SL" -> "SI";
            case "FI" -> "FI";
            case "TL" -> "PH";
            case "VI" -> "VN";
            case "TK" -> "TM";
            case "FJ" -> "FJ";
            case "WO" -> "SN";
            case "IS" -> "IS";
            case "CS" -> "CZ";
            case "BE" -> "BY";
            case "KY" -> "KG";
            case "MK" -> "MK";
            case "MN" -> "MN";
            case "TG" -> "TJ";
            case "UK" -> "UA";
            case "BG" -> "BG";
            case "KK" -> "KZ";
            case "HY" -> "AM";
            case "HE" -> "IL";
            case "DV" -> "MV";
            case "TH" -> "TH";
            case "LO" -> "LA";
            case "DZ" -> "BT";
            case "MY" -> "MM";
            case "KA" -> "GE";
            case "AM" -> "ET";
            case "KM" -> "KH";
            case "CN" -> "CN";
            case "JA" -> "JP";
            case "KO" -> "KR";
            default -> "INT";
        };
    }

    /**
     * Gets the country's name by country's code.
     * @param countryCode The country's code.
     * @return The country's name.
     */
    public static String getCountryNameByCode(String countryCode) {
        if (countryCode == null || countryCode.isBlank())
            return "null";

        String countryName = new Locale.Builder().setRegion(countryCode.toUpperCase()).build().getDisplayCountry(Locale.ENGLISH);
        return countryName.isEmpty() ? "null" : countryName;
    }

    /**
     * Gets the profile color byte.
     *
     * @param staffRoles The player's staff roles.
     * @return Profile color.
     */
    public static byte getProfileColorId(List<StaffRoleType> staffRoles) {
        if (staffRoles.contains(StaffRoleType.ADMIN))
            return 10;

        if (staffRoles.contains(StaffRoleType.PUBLIC_MODO))
            return 5;

        if (staffRoles.contains(StaffRoleType.MAPCREW))
            return 11;

        if (staffRoles.contains(StaffRoleType.FUNCORP))
            return 13;

        return 1;
    }

    /**
     * Gets the youtube's id from a url.
     * @param url The given url.
     * @return The youtube's id.
     */
    public static String getYoutubeID(String url) {
        try {
            URI uri = URI.create(url);
            String host = uri.getHost();
            if (host == null)
                return null;

            host = host.toLowerCase();
            if (host.equals("youtube.com") || host.equals("www.youtube.com") || host.equals("m.youtube.com")) {
                String path = uri.getPath();
                if ("/watch".equals(path)) {
                    for (String parameter : uri.getRawQuery() == null ? new String[0] : uri.getRawQuery().split("&")) {
                        String[] pair = parameter.split("=", 2);
                        if (pair.length == 2 && pair[0].equals("v"))
                            return pair[1];
                    }
                }

                if (path != null && (path.startsWith("/shorts/") || path.startsWith("/embed/") || path.startsWith("/live/")))
                    return path.split("/")[2];
            }
        } catch (IllegalArgumentException ignored) {
            return null;
        }

        return null;
    }

    /**
     * Gets the unix timestamp.
     * @return Seconds.
     */
    public static long getUnixTime() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * Gets the integer RGB of the Hex color.
     * @param color The hex's color.
     * @return -1 if its invalid or else the int RGB.
     */
    public static int hexColorToInt(String color) {
        if (color == null || !color.matches("^#[0-9A-Fa-f]{6}$"))
            return -1;

        try {
            return Integer.parseInt(color.substring(1), 16);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}