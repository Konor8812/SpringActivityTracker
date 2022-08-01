package com.util;

import com.entity.Activity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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


    public static List<Activity> sortActivitiesByDuration(List<Activity> activities, String order) {
        List<Activity> hoursDuration = new ArrayList<>();
        List<Activity> daysDuration = new ArrayList<>();

        List<Activity> sorted = new ArrayList<>();

        for (Activity activity : activities) {

            if (activity.getDuration().contains("days")) {
                daysDuration.add(activity);
            } else {
                hoursDuration.add(activity);
            }
        }

        if(order.equals("asc")) {
            hoursDuration = hoursDuration.stream().sorted(Comparator.comparing(Activity::getDuration)).collect(Collectors.toList());
            daysDuration = daysDuration.stream().sorted(Comparator.comparing(Activity::getDuration)).collect(Collectors.toList());

            for (Activity activity : hoursDuration) {
                sorted.add(activity);
            }
            for (Activity activity : daysDuration) {
                sorted.add(activity);
            }
        }else{
            hoursDuration = hoursDuration.stream().sorted(Comparator.comparing(Activity::getDuration).reversed()).collect(Collectors.toList());
            daysDuration = daysDuration.stream().sorted(Comparator.comparing(Activity::getDuration).reversed()).collect(Collectors.toList());

            for (Activity activity : daysDuration) {
                sorted.add(activity);
            }
            for (Activity activity : hoursDuration) {
                sorted.add(activity);
            }
        }
        return sorted;

    }

    public static String localizedTimeRU(String duration) {
        duration = duration.replaceAll("days", "дней");
        duration = duration.replaceAll("hours", "часов");
        duration = duration.replaceAll("minutes", "минут");
        duration = duration.replaceAll("seconds", "секунд");
        return  duration;
    }

}
