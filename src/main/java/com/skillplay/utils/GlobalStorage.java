package com.skillplay.utils;


import com.skillplay.dto.UserRequestDto;

import java.util.HashMap;
import java.util.Map;

public class GlobalStorage {
    public static Map<String, Map<String, UserRequestDto>> userMap = new HashMap<>();
    public static Map<String,Map<String,String>> otpMap = new HashMap<>();
    public static Map<String,Map<AppConstants,Integer>> otpRetryMap = new HashMap<>();
}