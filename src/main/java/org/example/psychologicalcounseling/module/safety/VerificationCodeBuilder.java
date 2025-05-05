package org.example.psychologicalcounseling.module.safety;

public class VerificationCodeBuilder {
    /**
     * Generates a six-digit verification code.
     * @return A string representing the six-digit verification code.
     */
    public static String generateVerificationCode() {
        StringBuilder verificationCode = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int random = (int) (Math.random() * 10);
            verificationCode.append(random);
        }
        return verificationCode.toString();
    }
}
