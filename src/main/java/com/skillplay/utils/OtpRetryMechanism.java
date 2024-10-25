package com.skillplay.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OtpRetryMechanism {

//    public void incrementRetryCount(String email, AppConstants purpose){
//        if(GlobalStorage.otpRetryMap.containsKey(email)){
//            Map<AppConstants, Integer> purposeMap = GlobalStorage.otpRetryMap.get(email);
//            if(purposeMap.containsKey(purpose)){
//                purposeMap.put(purpose, purposeMap.get(purpose) +1);
//            }
//        }else{
//
//            Map<AppConstants, Integer> purposeMap = GlobalStorage.otpRetryMap.get(email);
//            if(purposeMap.containsKey(purpose)){
//                purposeMap.put(purpose , 1 );
//            }
//        }
//    }

    public static void incrementRetryCount(String email, AppConstants purpose){
        GlobalStorage.otpRetryMap.computeIfAbsent(email, k -> new HashMap<>())
                .merge(purpose, 1, Integer::sum);
    }


    public static void clearRetryCount(String email, AppConstants purpose){
        Map<AppConstants, Integer> purposeMap = GlobalStorage.otpRetryMap.get(email);
        if (purposeMap != null) {
            purposeMap.put(purpose, 0);
        }
    }

    public static boolean isRetryAvailable(String email, AppConstants purpose){
        Map<AppConstants, Integer> purposeMap = GlobalStorage.otpRetryMap.get(email);
        if (purposeMap != null) {
            Integer retryCount = purposeMap.get(purpose);
            return retryCount == null || retryCount < 3;
        }
        return true;
    }

}
