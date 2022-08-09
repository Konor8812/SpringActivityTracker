package com.service;


import com.entity.Activity;
import com.entity.LocalizationActivityRu;
import com.repository.ActivityRepository;
import com.repository.LocalizationRepositoryRu;
import com.util.Util;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {
    private static final Logger logger = Logger.getLogger(ActivityService.class);

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    LocalizationRepositoryRu localizationRepositoryRu;

    public boolean addActivity(Activity activity) {
        try {
            activityRepository.save(activity);
            return true;
        } catch (Exception e) {
            logger.info("activity " + activity.getName() + " wasn't saved, reason: " + e.getMessage());
            return false;
        }
    }

    public List<Activity> getNextActivities(int offset, String lang) {
        Pageable nextPageWithFiveElements = PageRequest.of(offset, 5);
        Page<Activity> p = activityRepository.findAll(nextPageWithFiveElements);
        System.out.println(p.getContent());
        List<Activity> activities = p.getContent();
        switch (lang){
            case "ru":
                for(Activity a : activities){
                    LocalizationActivityRu lar = localizationRepositoryRu.getLocActivityByName(a.getName());
                    if(lar != null){
                        a.getDescription().setDescription(lar.getActivityDescriptionRu());
                        a.setName(lar.getActivityNameRu());
                    }
                    a.setDuration(Util.localizedTimeRU(a.getDuration()));
                }
                return activities;
            default: return activities;
        }
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
        Activity ac = activityRepository.findActivityById(activityId);
        if (ac != null) {
            if(localizationRepositoryRu.existsById(ac.getName())) {
                localizationRepositoryRu.deleteById(ac.getName());
            }
            activityRepository.deleteById(activityId);
        }
    }

    public Activity findById(long activityId) {
        return activityRepository.findActivityById(activityId);
    }

    public List<Activity> getAllActivitiesWithTag(String tagName) {

        List<Activity> activities = activityRepository.getAllActivitiesWithTag(tagName);
        return activities;
    }

    public boolean addLocalizationForActivity(String language, String enName, String translatedName, String translatedDescription) {

        switch (language) {
            case "ru":
                LocalizationActivityRu lar = localizationRepositoryRu.getLocActivityByName(enName);

                if(lar == null){
                    localizationRepositoryRu.save(new LocalizationActivityRu(enName, translatedName, translatedDescription));
                }else{
                    lar.setActivityNameRu(translatedName);
                    lar.setActivityDescriptionRu(translatedDescription);
                    localizationRepositoryRu.save(lar);
                }
                return true;
            case "en":
                return true;
            default:
                logger.error("Unsupported language " + language);
                return false;
        }

    }

    public List<Activity> getAllActivities(String name, String asc, String lang) {
        return activityRepository.findAll();
    }
}
