package com.entity;

import com.entity.embedded.ActivityUserId;
import com.util.Util;
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

    @Column(name="time_spent")
    private long timeSpent = System.currentTimeMillis();

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

    public long getTimeSpent() {
        return timeSpent;
    }

    public void setTime_spent(long timeSpent) {
        this.timeSpent = timeSpent;
    }
}
