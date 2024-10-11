package com.skillplay.scheduler;


import com.skillplay.dto.UserRequestDto;
import com.skillplay.utils.GlobalStorage;
import com.skillplay.utils.KeyAndOtpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AppScheduler {

    private final KeyAndOtpUtils keyAndOtpUtils;

    @Scheduled(cron = "0 0 0 * * ?")
    private void cleanUserOTPMap() {
        Map<String, Map<String, UserRequestDto>> validOtps = new HashMap();
        GlobalStorage.userMap.forEach((k,v) ->{
            if(keyAndOtpUtils.isKeyValid(k)){
                validOtps.put(k,v);
            }
        });
        GlobalStorage.userMap = validOtps;
    }

}
