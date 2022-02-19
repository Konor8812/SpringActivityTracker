package com.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public abstract class Util {

    public static String bCryptEncode(String s){
        return new BCryptPasswordEncoder().encode(s);
    }

    public static String getFormattedTimeSpent(long millis) {
        StringBuilder sb = new StringBuilder();
        long second = 1000;
        long minute = 60 * second;
        long hour = 60 * minute;
        long day = 24 * hour;

        long days = millis / day;
        long leftOver = millis % day;
        long hours = leftOver / hour;
        leftOver = leftOver % hour;
        long minutes = leftOver / minute;
        leftOver = leftOver % minute;
        long seconds = leftOver / second;
        if (days != 0) {
            sb.append(days).append(" days ");
        }
        if (hours != 0) {
            sb.append(hours).append(" hours ");
        }
        if (minutes != 0) {
            sb.append(minutes).append(" minutes ");
        }
        if (seconds != 0) {
            sb.append(seconds).append(" seconds ");
        }
        return sb.toString();
    }

    public static String formatDescription(String s){
        return "";
    }
}
