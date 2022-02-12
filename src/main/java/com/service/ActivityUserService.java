package com.service;

import com.dto.ActivityDTO;
import com.entity.Activity;
import com.entity.ActivityUser;
import com.entity.embedded.ActivityUserId;
import com.repository.ActivityRepository;
import com.repository.ActivityUserRepository;
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

        List<Activity> allActivities = activityService.getAllActivities();
        List<Activity> availableActivities = new ArrayList<>();
        L: for(Activity a: allActivities){
            for(Activity ac: alreadyTakenActivities){
                if(a.equals(ac)){
                   continue L;
                }
            }
            availableActivities.add(a);
        }

        return  availableActivities;
    }

    public List<ActivityDTO> getUsersActivities(long userId){
        List<ActivityUser> usersActivities = activityUserRepository.findAllUsersActivities(userId);

        List<ActivityDTO> dtos = new ArrayList<>();
        for(ActivityUser a: usersActivities){
            Activity ac = parseActivityFromUserActivity(a);
            ActivityDTO dto = ActivityDTO.parseActivity(ac);
            dto.setTimeSpent(a.getTimeSpent());
            dto.setStatus(a.getStatus());
            dtos.add(dto);
        }
        return dtos;
    }

    public void reqActivity(long activityId, long userId) {
        ActivityUserId activityUserId = new ActivityUserId(activityId, userId);
        ActivityUser activityUser = new ActivityUser();
        activityUser.setActivityUserId(activityUserId);
        activityUserRepository.save(activityUser);
    }

    public void approveActivityForUser(long activityId, long userId){
        ActivityUserId activityUserId = new ActivityUserId(activityId, userId);


    }

    public void denyApproval(long activityId, long userId){
        ActivityUserId activityUserId = new ActivityUserId(activityId, userId);


    }

    public void activityCompleted(long activityId, long userId){
        double reward = activityService.findById(activityId).getReward();
        userService.updateActivitiesAmount(userId, true);
        userService.updatePointsAmount(userId, reward);

        ActivityUserId activityUserId = new ActivityUserId(activityId, userId);
        activityUserRepository.deleteById(activityUserId);
    }

    public void activityGaveUp(long activityId, long userId){
        ActivityUserId activityUserId = new ActivityUserId(activityId, userId);

    }

    public List<Activity> parseActivitiesFromUserActivity(List<ActivityUser> list){
        List<Activity> activities = new ArrayList<>();
        for(ActivityUser au: list){
            activities.add(activityRepository.findActivityById(au.getActivityUserId().getActivityId()));
        }
        return activities;
    }

    public Activity parseActivityFromUserActivity(ActivityUser activityUser) {
        return activityRepository.findActivityById(activityUser.getActivityUserId().getActivityId());
    }
}
