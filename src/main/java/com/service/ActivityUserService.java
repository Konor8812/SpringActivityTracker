package com.service;

import com.entity.ActivityUser;
import com.entity.embedded.ActivityUserId;
import com.repository.ActivityUserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class ActivityUserService {
    private static final Logger logger = Logger.getLogger(ActivityUserService.class);

    @Autowired
    ActivityUserRepository activityUserRepository;

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

}
