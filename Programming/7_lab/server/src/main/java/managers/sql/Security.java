package managers.sql;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security {
    /**
     * Хеширует пароль с использованием алгоритма SHA-224
     * @param password исходный пароль
     * @return хешированная строка в шестнадцатеричном формате
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-224");
            byte[] hashBytes = digest.digest(
                    password.getBytes(StandardCharsets.UTF_8)
            );
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-224 algorithm not available", e);
        }
    }

    /**
     * Конвертирует массив байтов в шестнадцатеричную строку
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
