package com.entity;

import com.entity.embedded.ActivityUserId;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_has_activity")
public class ActivityUser {

    @EmbeddedId
    private ActivityUserId activityUserId;

    private String status = "requested";

    private String time_spent;

    public ActivityUserId getActivityUserId() {
        return activityUserId;
    }

    public void setActivityUserId(ActivityUserId activityUserId) {
        this.activityUserId = activityUserId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime_spent() {
        return time_spent;
    }

    public void setTime_spent(String time_spent) {
        this.time_spent = time_spent;
    }
}
