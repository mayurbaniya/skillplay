package com.skillplay.utils;

import org.springframework.stereotype.Component;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.Random;

@Component
public class KeyAndOtpUtils {


    private final String ALGORITHM = "AES";
    private final byte[] keyBytes = "LIGHTERENTAXNEC1".getBytes(StandardCharsets.UTF_8); // Ensure this key is 16 bytes

    public String generateUniqueKey() throws Exception {
        long currentTimeMillis = System.currentTimeMillis();
        String timestamp = String.valueOf(currentTimeMillis);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKey secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encryptedBytes = cipher.doFinal(timestamp.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }


    public String decryptUniqueKey(String encryptedKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKey secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] decryptedBytes = Base64.getDecoder().decode(encryptedKey);
        byte[] originalBytes = cipher.doFinal(decryptedBytes);

        return new String(originalBytes, StandardCharsets.UTF_8);
    }

    public boolean isKeyValid(String encryptedKey) {
        long expirationTimeInMillis = 5 * 60 * 1000;
        try {
            String decryptedTimestamp = decryptUniqueKey(encryptedKey);
            long timestamp = Long.parseLong(decryptedTimestamp);
            long currentTimeMillis = System.currentTimeMillis();
            return (currentTimeMillis - timestamp) <= expirationTimeInMillis;
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }


    public String generateOtp(int length){
        String numbers = "0123456789";
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for(int i = 0; i< length; i++){
            int index = random.nextInt(numbers.length());
            otp.append(numbers.charAt(index));}
        System.out.println("Generated OTP: "+ otp.toString());
        return otp.toString();
    }

}
