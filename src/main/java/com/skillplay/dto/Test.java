package com.skillplay.dto;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class Test {

    private static final String ALGORITHM = "AES";
    private static final byte[] keyBytes = "MySuperSecretKey".getBytes(StandardCharsets.UTF_8); // Ensure this key is 16 bytes

    public static String generateUniqueKey() throws Exception {
        long currentTimeMillis = System.currentTimeMillis();
        String timestamp = String.valueOf(currentTimeMillis);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKey secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encryptedBytes = cipher.doFinal(timestamp.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }


    public static String decryptUniqueKey(String encryptedKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKey secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] decryptedBytes = Base64.getDecoder().decode(encryptedKey);
        byte[] originalBytes = cipher.doFinal(decryptedBytes);

        return new String(originalBytes, StandardCharsets.UTF_8);
    }

    public static boolean isKeyValid(String encryptedKey) throws Exception {
        long expirationTimeInMillis = 5 * 60 * 1000;
        String decryptedTimestamp = decryptUniqueKey(encryptedKey);
        long timestamp = Long.parseLong(decryptedTimestamp);
        long currentTimeMillis = System.currentTimeMillis();

        return (currentTimeMillis - timestamp) <= expirationTimeInMillis;
    }

    public static void main(String[] args) throws Exception {
        String uniqueKey = generateUniqueKey();

        String decryptUniqueKey = decryptUniqueKey(uniqueKey);

        System.out.println("Generated Unique Key: " + uniqueKey);
        System.out.println("decrypted Key: " + decryptUniqueKey);

        long expirationTimeInMillis = 5 * 60 * 1000; // 5 minutes

        // Step 1: Create an Instant from the timestamp
        Instant instant = Instant.ofEpochMilli(Long.parseLong(decryptUniqueKey));

        // Step 2: Convert Instant to ZonedDateTime in the system default time zone
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());

        // Step 3: Format ZonedDateTime to a readable date and time string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = zonedDateTime.format(formatter);

        // Output the formatted date and time
        System.out.println("Formatted Date Time: " + formattedDateTime);


        boolean isValid = isKeyValid(uniqueKey);
        System.out.println("Is Key Valid: " + isValid);
    }
}

