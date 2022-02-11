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
    public List<ActivityDTO> getAvailableActivities(long userId){
        List<Activity> alreadyTakenActivities= parseActivitiesFromUserActivity(activityUserRepository.
                findAllUsersActivities(userId));

        List<Activity> allActivities = activityRepository.findAll();
        List<Activity> availableActivities = new ArrayList<>();
        for(Activity a: allActivities){
            if(!alreadyTakenActivities.contains(a)){
                availableActivities.add(a);
            }
        }

        List<ActivityDTO> dtos = new ArrayList<>();

        for(Activity a: availableActivities){
            dtos.add(ActivityDTO.parseActivity(a));
        }
        return  dtos;
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
        ActivityUserId activityUserId = new ActivityUserId(activityId, userId);



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
