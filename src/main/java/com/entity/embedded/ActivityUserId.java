package com.entity.embedded;


import javax.persistence.Embeddable;
import java.io.Serializable;

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
}
