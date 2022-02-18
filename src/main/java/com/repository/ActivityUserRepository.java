package com.repository;

import com.entity.ActivityUser;
import com.entity.embedded.ActivityUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActivityUserRepository extends JpaRepository<ActivityUser, ActivityUserId> {

    ActivityUser findByActivityUserId(ActivityUserId activityUserId);

    @Query(value = "SELECT * FROM mydb.user_has_activity WHERE user_id = (?)", nativeQuery = true)
    List<ActivityUser> findAllUsersActivities(long userId);

    @Query(value = "select activity_id from mydb.user_has_activity where user_id = (?) and status = 'requested'", nativeQuery = true)
    List<Long> getRequestedActivitiesIds(long userId);
}
