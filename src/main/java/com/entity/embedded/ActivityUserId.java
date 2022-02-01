package com.entity.embedded;


import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@Embeddable
public class ActivityUserId implements Serializable {

    private static final long serialVersionUID = -1981867606257400359L;
    private long userId;
    private long activityId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public ActivityUserId(long activityId, long userId) {
        this.userId = userId;
        this.activityId = activityId;
    }


}
