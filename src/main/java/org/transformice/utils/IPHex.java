package org.transformice.utils;

public final class IPHex {
    /**
     * Gets the color of given hex form ip address.
     * @param ip Hex form ip address.
     * @return The color of any hex form ip address.
     */
    public static String colorIP(String ip) {
        ip = ip.startsWith("#") ? ip.substring(1) : ip;
        String[] components = ip.split("\\.");

        StringBuilder concatenated = new StringBuilder();
        for (String component : components) {
            concatenated.append(component);
        }
        while (concatenated.length() < 8) {
            concatenated.append("0");
        }
        concatenated.setLength(8);

        int r = Integer.parseInt(concatenated.substring(0, 2), 16);
        int g = Integer.parseInt(concatenated.substring(2, 4), 16);
        int b = Integer.parseInt(concatenated.substring(4, 6), 16);

        return String.format("#%02X%02X%02X", r, g, b);
    }

    /**
     * Converts the ip address to hex form.
     * @param ip The given ip address.
     * @return To hex form (Example 127.0.0.1 is #7F.00.00.01).
     */
    public static String encodeIP(String ip) {
        if(ip.isEmpty()) return "offline";

        String[] parts = ip.split("\\.");
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
     * Converts the hex ip to normal ip address.
     * @param ip Hex form ip address.
     * @return A normal ip address.
     */
    public static String decodeIP(String ip) {
        ip = ip.substring(1);
        String[] parts = ip.split("\\.");
        StringBuilder decodedIP = new StringBuilder();

        for (String part : parts) {
            int value = Integer.parseInt(part, 16);
            decodedIP.append(value).append(".");
        }

        return decodedIP.substring(0, decodedIP.length() - 1);
    }
}