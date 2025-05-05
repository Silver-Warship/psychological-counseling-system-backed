package org.example.psychologicalcounseling.module.safety;

import java.security.SecureRandom;
import java.util.Random;

public class OriginalPasswordBuilder {
    //tool method, generate a 16 digits original password
    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String OTHER_CHAR = "!@#$%&*()_+-=[]?";
    private static final String PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHAR;
    private static final Random random = new SecureRandom();

    /**
     * Generate a random password with a length of 17 characters.
     * The password contains lowercase letters, uppercase letters, numbers, and special characters.
     * @return A randomly generated password.
     */
    public static String generatePassword() {
        int length = 17;

        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(PASSWORD_ALLOW_BASE.length());
            password.append(PASSWORD_ALLOW_BASE.charAt(randomIndex));
        }

        return password.toString();
    }
}
