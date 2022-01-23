package com.dto;

import com.entity.Activity;

public class ActivityDTO {

    private final String name;
    private final String duration;
    private final String reward;
    private final int takenBy;

    private ActivityDTO(Activity activity) {
        this.name = activity.getName();
        this.duration = activity.getDuration();
        this.reward = activity.getDuration();
        this.takenBy = activity.getTaken_by();
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
}
