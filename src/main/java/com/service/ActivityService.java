package com.service;


import com.dto.ActivityDTO;
import com.entity.Activity;
import com.repository.ActivityRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityService {
    private static final Logger logger = Logger.getLogger(ActivityService.class);

    @Autowired
    ActivityRepository activityRepository;

    public List<ActivityDTO> getAllActivitiesList(){
        List<Activity> activities = activityRepository.findAll();
        List<ActivityDTO> dtos = new ArrayList<>();

        for(Activity a: activities){
            dtos.add(ActivityDTO.parseActivity(a));
        }
        return  dtos;
    }
}
