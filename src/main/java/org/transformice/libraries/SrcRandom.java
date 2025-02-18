package org.transformice.libraries;

// Imports
import java.security.SecureRandom;

public final class SrcRandom {
    private static final SecureRandom secureRandom = new SecureRandom();

    /**
     * Generates a number in range.
     * @param min Minimum number.
     * @param max Maximum number.
     * @return Random number in range between minimum and maximum.
     */
    public static int RandomNumber(int min, int max) {
        return secureRandom.nextInt((max - min) + 1) + min;
    }

    /**
     * Generates a new number with given length.
     * @param n The number length.
     * @return Nth length random number.
     */
    public static String generateNumber(int n) {
        StringBuilder number = new StringBuilder();
        number.append(secureRandom.nextInt(9) + 1);
        for (int i = 1; i < n; i++) {
            number.append(secureRandom.nextInt(10)); // Digits from 0-9
        }

        return number.toString();
    }
}