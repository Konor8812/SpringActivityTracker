package com.util;

import com.entity.Activity;
import com.entity.Description;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Util {

    private static Pattern[] patternsRu;
    private static Pattern[] patternsEn;

    public static String bCryptEncode(String s) {
        return new BCryptPasswordEncoder().encode(s);
    }

    public static String getFormattedTimeSpent(long millis) {
        if(millis <= 0 ){
            return "0 seconds ";
        }

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

            if (activity.getDuration().contains("days") || activity.getDuration().contains("дней")) {
                daysDuration.add(activity);
            } else if(activity.getDuration().contains("hours") || activity.getDuration().contains("часов")){
                hoursDuration.add(activity);
            }
        }

        if (order.equals("asc")) {
            hoursDuration = hoursDuration.stream().sorted(Comparator.comparing(Activity::getDuration)).collect(Collectors.toList());
            daysDuration = daysDuration.stream().sorted(Comparator.comparing(Activity::getDuration)).collect(Collectors.toList());

            for (Activity activity : hoursDuration) {
                sorted.add(activity);
            }
            for (Activity activity : daysDuration) {
                sorted.add(activity);
            }
        } else {
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
        return duration;
    }


    public static List<Activity> sortActivities(List<Activity> activities, String sortBy, String orderBy) {
        Stream<Activity> activitiesStream = activities.stream();
        switch (sortBy.concat(orderBy)) {
            case "nameasc":
                activitiesStream = activitiesStream.sorted(Comparator.comparing(Activity::getName));
                break;
            case "namedesc":
                activitiesStream = activitiesStream.sorted(Comparator.comparing(Activity::getName).reversed());
                break;
            case "rewardasc":
                activitiesStream = activitiesStream.sorted(Comparator.comparing(Activity::getReward));
                break;
            case "rewarddesc":
                activitiesStream = activitiesStream.sorted(Comparator.comparing(Activity::getReward).reversed());
                break;
            case "requestsasc":
                activitiesStream = activitiesStream.sorted(Comparator.comparing(Activity::getRequestedTimes));
                break;
            case "requestsdesc":
                activitiesStream = activitiesStream.sorted(Comparator.comparing(Activity::getRequestedTimes).reversed());
                break;
            case "takesasc":
                activitiesStream = activitiesStream.sorted(Comparator.comparing(Activity::getTakenBy));
                break;
            case "takesdesc":
                activitiesStream = activitiesStream.sorted(Comparator.comparing(Activity::getTakenBy).reversed());
                break;
            case "durationasc":
            case "durationdesc":
                return sortActivitiesByDuration(activities, orderBy);
            default:
        }

        return activitiesStream.collect(Collectors.toList());

    }


    public static List<Activity> parseActivitiesFromString(String activitiesAsString, String lang) {
        if (activitiesAsString == null || activitiesAsString.isEmpty()) {
            return null;
        }
        List<Activity> activities = new ArrayList<>();
        activitiesAsString = activitiesAsString.replaceAll("\\[|]", "");
        String[] activitiesAsStrings = activitiesAsString.split("Activity");

        getPatternsAccordingToLang(lang);

        for (String activityAsString : activitiesAsStrings) {
            if (activityAsString.isEmpty()) {
                continue;
            }
            if (lang.equals("ru")) {
                activities.add(buildActivity(patternsRu, activityAsString));
            } else {
                activities.add(buildActivity(patternsEn, activityAsString));
            }
        }

        return activities;
    }

    private static Activity buildActivity(Pattern[] patterns, String activityAsString) {
        Activity a = new Activity();
        Matcher m = patterns[0].matcher(activityAsString);
        m.find();
        a.setId(Long.parseLong(m.group(1)));

        m = patterns[1].matcher(activityAsString);
        m.find();
        a.setName(m.group(1));

        m = patterns[2].matcher(activityAsString);
        m.find();
        a.setDuration(m.group(1));

        m = patterns[3].matcher(activityAsString);
        m.find();
        a.setReward(Double.parseDouble(m.group(1)));

        m = patterns[4].matcher(activityAsString);
        m.find();
        a.setTakenBy(Integer.parseInt(m.group(1)));

        m = patterns[5].matcher(activityAsString);
        m.find();
        a.setRequestedTimes(Integer.parseInt(m.group(1)));

        m = patterns[6].matcher(activityAsString);
        m.find();
        a.setDescription(new Description(m.group(1)));

        return a;
    }

    private static Pattern[] getPatternsAccordingToLang(String lang) {

        if (lang.equals("ru")) {
            if(patternsRu != null){
                return patternsRu;
            }
            patternsRu = new Pattern[7];
            patternsRu[0] = Pattern.compile("(?:id=)(\\d+)");
            patternsRu[1] = Pattern.compile("(?:name=)([а-яА-Яa-zA-Z ]+)");
            patternsRu[2] = Pattern.compile("(?:duration=)(\\d+ (часов|дней))");
            patternsRu[3] = Pattern.compile("(?:reward=)(\\d+.\\d?)");
            patternsRu[4] = Pattern.compile("(?:takenBy=)(\\d+)");
            patternsRu[5] = Pattern.compile("(?:requestedTimes=)(\\d+)");
            patternsRu[6] = Pattern.compile("(?:description=)([а-яА-Яa-zA-Z ,]+)");
            return patternsRu;
        } else {
            if(patternsEn != null){
                return patternsEn;
            }
            patternsEn = new Pattern[7];
            patternsEn[0] = Pattern.compile("(?:id=)(\\d+)");
            patternsEn[1] = Pattern.compile("(?:name=)([a-zA-Z ]+)");
            patternsEn[2] = Pattern.compile("(?:duration=)(\\d+ (hours|days))");
            patternsEn[3] = Pattern.compile("(?:reward=)(\\d+.\\d?)");
            patternsEn[4] = Pattern.compile("(?:takenBy=)(\\d+)");
            patternsEn[5] = Pattern.compile("(?:requestedTimes=)(\\d+)");
            patternsEn[6] = Pattern.compile("(?:description=)([a-zA-Z ,]+)");
            return patternsEn;
        }

    }

}
