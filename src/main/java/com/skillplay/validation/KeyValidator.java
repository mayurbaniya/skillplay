package com.skillplay.validation;

import com.skillplay.utils.KeyAndOtpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KeyValidator {

    private final KeyAndOtpUtils decryptUniqueKey;

    public boolean isKeyValid(String encryptedKey) throws Exception {
        long expirationTimeInMillis = 5 * 60 * 1000;
        String decryptedTimestamp = decryptUniqueKey.decryptUniqueKey(encryptedKey);
        long timestamp = Long.parseLong(decryptedTimestamp);
        long currentTimeMillis = System.currentTimeMillis();

        return (currentTimeMillis - timestamp) <= expirationTimeInMillis;
    }
}
