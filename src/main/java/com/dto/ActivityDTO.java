package com.dto;

import com.entity.Activity;

public class ActivityDTO {

    private final long id;
    private final String name;
    private final String duration;
    private final String reward;
    private final int takenBy;

    private String description;
    private String timeSpent;
    private String status;

    private ActivityDTO(Activity activity) {
        this.id= activity.getId();
        this.name = activity.getName();
        this.duration = activity.getDuration();
        this.reward = activity.getDuration();
        this.takenBy = activity.getTakenBy();
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
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

    public void setTimeSpent(String timeSpent) {
        this.timeSpent = timeSpent;
    }
}
