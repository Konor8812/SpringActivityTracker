package com;

import com.entity.Activity;
import com.entity.Description;
import com.util.Util;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) {

//        System.out.println(350 * 7 / 36 / 45);
//
//        System.out.println(new BCryptPasswordEncoder().encode("admin"));


//        List<Activity> activities = new ArrayList<>();
//        activities.add(new Activity(1, "act1", "1 hours", 1.1, 1, 1, new Description("activity1Description")));
//        activities.add(new Activity(2, "act2", "2 hours", 2.2, 2, 2, new Description("activity2Description")));
//        activities.add(new Activity(3, "act3", "3 hours", 3.3, 3, 3, new Description("activity3Description")));
//        activities.add(new Activity(4, "act4", "4 hours", 4.4, 4, 4, new Description("activity4Description")));
//
//        List<Activity> customActivities = new CustomListImplementation<Activity>(activities);
//
//        for(Activity a: customActivities) {
//            System.out.println(a);
//        }
//
//        System.out.println(activities);
//        System.out.println("```");
//        System.out.println();

       // String str = "[Activity(id=1, name=watching tv, duration=2 hours, reward=6.0, takenBy=0, requestedTimes=0, description=free), Activity(id=2, name=sleeping, duration=2 days, reward=3.0, takenBy=0, requestedTimes=0, description=relaxation), Activity(id=3, name=going to the gym, duration=5 hours, reward=4.5, takenBy=0, requestedTimes=0, description=sport, healthcare), Activity(id=4, name=reading, duration=3 hours, reward=3.0, takenBy=0, requestedTimes=0, description=entertainment, education), Activity(id=5, name=cooking, duration=4 days, reward=7.5, takenBy=0, requestedTimes=0, description=healthcare, relaxation)]";

        String strRu = "Activity(id=1, name=посмотреть телевизор, duration=2 часов, reward=6.0, takenBy=0, requestedTimes=0, description=бесплатно), \n" +
                "Activity(id=2, name=спать, duration=2 дней, reward=3.0, takenBy=0, requestedTimes=0, description=отдых), \n" +
                "Activity(id=3, name=сходить в зал, duration=5 часов, reward=4.5, takenBy=0, requestedTimes=0, description=спорт, здоровье), \n" +
                "Activity(id=4, name=почитать, duration=3 часов, reward=3.0, takenBy=0, requestedTimes=0, description=развлечения, учеба), \n" +
                "Activity(id=5, name=готовка, duration=4 дней, reward=7.5, takenBy=0, requestedTimes=0, description=здоровье, расслабление)";




        System.out.println( Util.parseActivitiesFromString(strRu, "ru"));


    }
}
