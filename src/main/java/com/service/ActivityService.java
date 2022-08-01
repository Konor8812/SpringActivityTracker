package com.service;


import com.entity.Activity;
import com.entity.LocalizationActivityRu;
import com.repository.ActivityRepository;
import com.repository.LocalizationRepositoryRu;
import com.util.Util;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Activity> getAllActivities(String sortedBy, String order, String lang) {
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

        if (lang.equals("ru")) {
            for (Activity a : result) {

                LocalizationActivityRu lar = localizationRepositoryRu.getLocActivityByName(a.getName());
                if (lar != null) {
                    a.setName(lar.getActivityNameRu());
                    a.getDescription().setDescription(lar.getActivityDescriptionRu());
                }
                a.setDuration(Util.localizedTimeRU(a.getDuration()));

            }
            //else if(){}

            if (sortedBy.equals("name")) {

                if (order.equals("desc")) {
                    result = result.stream().sorted(Comparator.comparing(Activity::getName).reversed()).collect(Collectors.toList());
                } else {
                    result = result.stream().sorted(Comparator.comparing(Activity::getName)).collect(Collectors.toList());
                }
            }
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
}
