package org.transformice.libraries;

// Imports
import java.security.SecureRandom;
import java.util.List;

public final class SrcRandom {
    private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String digits = "0123456789";
    private static final String alphabetdigits = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
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
     * Generates a number in range.
     * @param min Minimum number.
     * @param max Maximum number.
     * @param exceptions Number expections.
     * @return Random number in range between minimum and maximum without the numbers that contains in exceptions.
     */
    public static int RandomNumber(int min, int max, List<Integer> exceptions) {
        int number = secureRandom.nextInt((max - min) + 1) + min;
        if(exceptions.contains(number)) {
            return RandomNumber(number, max, exceptions);
        }

        return number;
    }

    /**
     * Generates a new number with given length.
     * @param n The number length.
     * @return Nth length random number.
     */
    public static String generateNumber(int n) {
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < n; i++) {
            number.append(digits.charAt(secureRandom.nextInt(digits.length())));
        }
        return number.toString();
    }

    /**
     * Generates a new string with given length that contains letters and digits.
     * @param n The string length.
     * @return Nth length random string.
     */
    public static String generateNumberAndLetters(int n) {
        StringBuilder number = new StringBuilder();
        for (int i = 1; i < n; i++) {
            number.append(alphabetdigits.charAt(secureRandom.nextInt(alphabetdigits.length())));
        }
        return number.toString();
    }
}