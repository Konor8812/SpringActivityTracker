package com.util;

import com.entity.Activity;
import com.entity.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    void getFormattedTimeSpent() {
        String result1 = Util.getFormattedTimeSpent(1000*60*2);
        Assertions.assertEquals("2 minutes ", result1);
        String result2 = Util.getFormattedTimeSpent(1000*60*60*24);
        Assertions.assertEquals("1 days ", result2);
        String result3 = Util.getFormattedTimeSpent(-12414);
        Assertions.assertEquals("0 seconds ", result3);
    }

    @Test
    void sortActivitiesByDuration() {
        List<Activity> actual = new ArrayList<>();
        actual.add(new Activity(1, "act1", "1 hours", 1.1, 1, 1, new Description("activity1Description")));
        actual.add(new Activity(4, "act4", "4 дней", 4.4, 4, 4, new Description("activity4Description")));
        actual.add(new Activity(2, "act2", "2 days", 2.2, 2, 2, new Description("activity2Description")));
        actual.add(new Activity(3, "act3", "3 часов", 3.3, 3, 3, new Description("activity3Description")));
        actual = Util.sortActivitiesByDuration(actual, "asc");

        List<Activity> expected = new ArrayList<>();
        expected.add(new Activity(1, "act1", "1 hours", 1.1, 1, 1, new Description("activity1Description")));
        expected.add(new Activity(3, "act3", "3 часов", 3.3, 3, 3, new Description("activity3Description")));
        expected.add(new Activity(2, "act2", "2 days", 2.2, 2, 2, new Description("activity2Description")));
        expected.add(new Activity(4, "act4", "4 дней", 4.4, 4, 4, new Description("activity4Description")));

        assertEquals(expected, actual);

    }

    @Test
    void localizedTimeRU() {
        String expected = "2 дней 4 часов 3 минут ";
        String actual = Util.localizedTimeRU("2 days 4 hours 3 minutes ");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void sortActivitiesShouldSortByName() {
        List<Activity> actual = new ArrayList<>();
        actual.add(new Activity(1, "act1", "1 hours", 1.1, 1, 1, new Description("activity1Description")));
        actual.add(new Activity(4, "act4", "4 дней", 4.4, 4, 4, new Description("activity4Description")));
        actual.add(new Activity(3, "act3", "3 часов", 3.3, 3, 3, new Description("activity3Description")));
        actual.add(new Activity(2, "act2", "2 days", 2.2, 2, 2, new Description("activity2Description")));
        actual = Util.sortActivities(actual, "name", "asc");

        List<Activity> expected = new ArrayList<>();
        expected.add(new Activity(1, "act1", "1 hours", 1.1, 1, 1, new Description("activity1Description")));
        expected.add(new Activity(2, "act2", "2 days", 2.2, 2, 2, new Description("activity2Description")));
        expected.add(new Activity(3, "act3", "3 часов", 3.3, 3, 3, new Description("activity3Description")));
        expected.add(new Activity(4, "act4", "4 дней", 4.4, 4, 4, new Description("activity4Description")));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void parseActivitiesFromString() {
        String activitiesAsStringEn = "[Activity(id=1, name=watching tv, duration=2 hours, reward=6.0, takenBy=0, requestedTimes=0, description=free)," +
                " Activity(id=2, name=sleeping, duration=2 days, reward=3.0, takenBy=0, requestedTimes=0, description=relaxation), " +
                "Activity(id=3, name=going to the gym, duration=5 hours, reward=4.5, takenBy=0, requestedTimes=0, description=sport, healthcare), " +
                "Activity(id=4, name=reading, duration=3 hours, reward=3.0, takenBy=0, requestedTimes=0, description=entertainment, education), " +
                "Activity(id=5, name=cooking, duration=4 days, reward=7.5, takenBy=0, requestedTimes=0, description=healthcare, relaxation)]";

        String activitiesAsStringRu = "Activity(id=1, name=посмотреть телевизор, duration=2 часов, reward=6.0, takenBy=0, requestedTimes=0, description=бесплатно), \n" +
                "Activity(id=2, name=спать, duration=2 дней, reward=3.0, takenBy=0, requestedTimes=0, description=отдых), \n" +
                "Activity(id=3, name=сходить в зал, duration=5 часов, reward=4.5, takenBy=0, requestedTimes=0, description=спорт, здоровье), \n" +
                "Activity(id=4, name=почитать, duration=3 часов, reward=3.0, takenBy=0, requestedTimes=0, description=развлечения, учеба), \n" +
                "Activity(id=5, name=готовка, duration=4 дней, reward=7.5, takenBy=0, requestedTimes=0, description=здоровье, расслабление)";

        List<Activity> activitiesEn = Util.parseActivitiesFromString(activitiesAsStringEn, "en");
        List<Activity> activitiesRu = Util.parseActivitiesFromString(activitiesAsStringRu, "ru");
        Assertions.assertEquals(5, activitiesRu.size());
        Assertions.assertEquals(5, activitiesEn.size());
        for(int i = 0; i < activitiesRu.size(); i++){
            Assertions.assertEquals(activitiesEn.get(i).getId(), activitiesRu.get(i).getId());
            Assertions.assertEquals(Util.localizedTimeRU(activitiesEn.get(i).getDuration()), activitiesRu.get(i).getDuration());
            Assertions.assertEquals(activitiesEn.get(i).getReward(), activitiesRu.get(i).getReward());
            Assertions.assertEquals(activitiesEn.get(i).getRequestedTimes(), activitiesRu.get(i).getRequestedTimes());
            Assertions.assertEquals(activitiesEn.get(i).getTakenBy(), activitiesRu.get(i).getTakenBy());
            Assertions.assertEquals(activitiesEn.get(i).getDescription().getDescription() != null, activitiesRu.get(i).getDescription().getDescription() != null);
        }

    }

}