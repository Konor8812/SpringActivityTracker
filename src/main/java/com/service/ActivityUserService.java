package com.service;

import com.dto.ActivityDTO;
import com.entity.Activity;
import com.entity.ActivityUser;
import com.entity.embedded.ActivityUserId;
import com.repository.ActivityRepository;
import com.repository.ActivityUserRepository;
import com.util.Util;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityUserService {
    private static final Logger logger = Logger.getLogger(ActivityUserService.class);

    @Autowired
    ActivityUserRepository activityUserRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    ActivityService activityService;

    @Autowired
    UserService userService;

    public List<Activity> getAvailableActivities(long userId){
        List<Activity> alreadyTakenActivities = parseActivitiesFromUserActivity(
                activityUserRepository.findAllUsersActivities(userId));

        List<Activity> allActivities = activityService.getAllActivities("name", "asc");
        List<Activity> availableActivities = new ArrayList<>();
        L: for(Activity a: allActivities) {
            for (Activity ac : alreadyTakenActivities) {
                if (a.equals(ac)) {
                    continue L;
                }
            }
            availableActivities.add(a);
        }

        return availableActivities;
    }

    public List<ActivityDTO> getUsersActivities(long userId){
        List<ActivityUser> usersActivities = activityUserRepository.findAllUsersActivities(userId);

        List<ActivityDTO> dtos = new ArrayList<>();
        for(ActivityUser a: usersActivities){
            Activity ac = parseActivityFromUserActivity(a);
            ActivityDTO dto = ActivityDTO.parseActivity(ac);
            long timeSpent = System.currentTimeMillis() - a.getTimeSpent();
            dto.setTimeSpent(Util.getFormattedTimeSpent(timeSpent));
            dto.setStatus(a.getStatus());
            dtos.add(dto);
        }
        return dtos;
    }

    // requestsAmount + 1; requestedTimes + 1; status = requested // working
    public void reqActivity(long activityId, long userId) {
        ActivityUserId activityUserId = new ActivityUserId(activityId, userId);
        ActivityUser activityUser = new ActivityUser();
        activityUser.setActivityUserId(activityUserId);
        activityUserRepository.save(activityUser);

        activityService.updateRequestedTimesAmount(activityId);
        userService.updateRequestsAmount(userId, true);
    }

    // requestsAmount -1; taken_by amount + 1; status = inProcess
    public void approveActivityForUser(long activityId, long userId){
        ActivityUserId activityUserId = new ActivityUserId(activityId, userId);
        ActivityUser au = activityUserRepository.findByActivityUserId(activityUserId);
        au.setStatus("inProcess");
        au.setTime_spent(System.currentTimeMillis());
        activityUserRepository.save(au);

        activityService.updateTakesAmount(activityId);
        userService.updateRequestsAmount(userId, false);
    }

    // requestsAmount -1; delete
    public void denyApproval(long activityId, long userId){
        ActivityUserId activityUserId = new ActivityUserId(activityId, userId);
        activityUserRepository.deleteById(activityUserId);
        userService.updateRequestsAmount(userId, false);
    }

    // points + ; activitiesAmount + 1;
    public void activityCompleted(long activityId, long userId){
        double reward = activityService.findById(activityId).getReward();
        userService.updateActivitiesAmount(userId);
        userService.updatePointsAmount(userId, reward);

        ActivityUserId activityUserId = new ActivityUserId(activityId, userId);
        activityUserRepository.deleteById(activityUserId);
    }

    // points - /0.5; delete
    public void activityGaveUp(long activityId, long userId){
        ActivityUserId activityUserId = new ActivityUserId(activityId, userId);
        activityUserRepository.deleteById(activityUserId);
        double points = activityService.findById(activityId).getReward();
        userService.updatePointsAmount(userId, points / 2);
    }

    public void activityDeleted(long activityId){
        List<Long> userIds = activityUserRepository.getUsersWithRequestId(activityId);
        userService.activityDeleted(userIds);
    }

    public List<Activity> parseActivitiesFromUserActivity(List<ActivityUser> list){
        List<Activity> activities = new ArrayList<>();
        for(ActivityUser au: list){
            activities.add(parseActivityFromUserActivity(au));
        }
        return activities;
    }

    private Activity parseActivityFromUserActivity(ActivityUser activityUser) {
        return activityRepository.findActivityById(activityUser.getActivityUserId().getActivityId());
    }
}
