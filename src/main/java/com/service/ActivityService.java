package com.service;


import com.dto.ActivityDTO;
import com.entity.Activity;
import com.repository.ActivityRepository;
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

    public boolean addActivity(Activity activity){
        try {
            activityRepository.save(activity);
            return true;
        }catch(Exception e){
            logger.info("activity " + activity.getName() + " wasn't saved");
            return false;
        }
    }

    public List<ActivityDTO> getAllActivitiesWithDescription(){
        return  getDescriptions(getAllActivities());
    }

    public List<Activity> getAllActivities(){
        return activityRepository.findAll();
    }

    public void updateRequestedTimesAmount(long activityId){

    }

    public void updateTakesAmount(long activityId){

    }

    public String getActivityDescription(long activityId){
        return activityRepository.getActivityDescription(activityId);
    }

    public void deleteActivity(long activityId){
        activityRepository.deleteById(activityId);
    }

    public Activity findById(long activityId){
        return activityRepository.findActivityById(activityId);
    }

    public List<ActivityDTO> getDescriptions(List<Activity> activities){
        List<ActivityDTO> dtos = new ArrayList<>();

        for(Activity a: activities){
            ActivityDTO dto = ActivityDTO.parseActivity(a);
            dto.setDescription(activityRepository.getActivityDescription(a.getId()));

            dtos.add(dto);
        }
        return  dtos;
    }
}
