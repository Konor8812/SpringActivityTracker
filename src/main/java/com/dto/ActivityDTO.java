package com.dto;

import com.entity.Activity;
import com.entity.Description;

public class ActivityDTO {

    private final long id;
    private final String name;
    private final String duration;
    private final String reward;
    private final int takenBy;

    private Description description;
    private String timeSpent;
    private String status;

    private ActivityDTO(Activity activity) {
        this.id= activity.getId();
        this.name = activity.getName();
        this.duration = activity.getDuration();
        this.reward = activity.getDuration();
        this.takenBy = activity.getTakenBy();
        this.description = activity.getDescription();
    }

    public String getStatus() {
        return status;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static ActivityDTO parseActivity(Activity activity){
        return new ActivityDTO(activity);
    }

    public String getName() {
        return name;
    }

    public String getDuration() {
        return duration;
    }

    public String getReward() {
        return reward;
    }

    public int getTakenBy() {
        return takenBy;
    }

    public String getTimeSpent() {
        return timeSpent;
    }

    public long getId() {
        return id;
    }

    public void setTimeSpent(String timeSpent) {
        this.timeSpent = timeSpent;
    }
}
