package com.service;


import com.dto.ActivityDTO;
import com.entity.Activity;
import com.repository.ActivityRepository;
import com.util.Util;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityService {
    private static final Logger logger = Logger.getLogger(ActivityService.class);

    @Autowired
    ActivityRepository activityRepository;


    public boolean addActivity(Activity activity) {
        try {
            activityRepository.save(activity);
            return true;
        } catch (Exception e) {
            logger.info("activity " + activity.getName() + " wasn't saved, reason: " + e.getMessage());
            return false;
        }
    }

    public List<Activity> getAllActivities(String sortedBy, String order) {
        List<Activity> result;
        switch (sortedBy.concat(order)) {
            case "nameasc":
                result = activityRepository.findAllByNameASC();
                break;
            case "namedesc":
                result = activityRepository.findAllByNameDESC();
                break;
            case "rewarddesc":
                result = activityRepository.findAllByRewardDESC();
                break;
            case "rewardasc":
                result = activityRepository.findAllByRewardASC();
                break;
            case "requestsasc":
                result = activityRepository.findAllByRequestsASC();
                break;
            case "requestsdesc":
                result = activityRepository.findAllByRequestsDESC();
                break;
            case "takesasc":
                result = activityRepository.findAllByTakesASC();
                break;
            case "takesdesc":
                result = activityRepository.findAllByTakesDESC();
                break;
            case "durationasc":
                result = Util.sortActivitiesByDuration(activityRepository.findAll(), "asc");
                break;
            case "durationdesc":
                result = Util.sortActivitiesByDuration(activityRepository.findAll(), "desc");
                break;
            default:
                logger.error("Error while sorting activities, params => " + sortedBy + " " + order);
                result = activityRepository.findAllByNameASC();
        }
        return result;
    }

    public void updateRequestedTimesAmount(long activityId) {
        Activity ac = activityRepository.findActivityById(activityId);
        ac.setRequestedTimes(ac.getRequestedTimes() + 1);
        activityRepository.save(ac);
    }

    public void updateTakesAmount(long activityId) {
        Activity ac = activityRepository.findActivityById(activityId);
        ac.setRequestedTimes(ac.getTakenBy() + 1);
        activityRepository.save(ac);
    }


    public void deleteActivity(long activityId) {
        activityRepository.DISABLE_FOREIGN_KEY_CHECKS();
        Activity ac = activityRepository.findActivityById(activityId);
        activityRepository.deleteDescription(ac.getId());
        activityRepository.deleteById(activityId);
        activityRepository.ENABLE_FOREIGN_KEY_CHECKS();
    }

    public Activity findById(long activityId) {
        return activityRepository.findActivityById(activityId);
    }

    public List<Activity> getAllActivitiesWithTag(String tagName){

        List<Activity> activities = activityRepository.getAllActivitiesWithTag(tagName);
        return activities;
    }

}
