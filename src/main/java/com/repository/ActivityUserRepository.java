package com.repository;

import com.entity.ActivityUser;
import com.entity.embedded.ActivityUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityUserRepository extends JpaRepository<ActivityUser, ActivityUserId> {

    ActivityUser findByActivityUserId(ActivityUserId activityUserId);

    @Query(value = "select * from mydb.user_has_activity WHERE user_id = (?)", nativeQuery = true)
    List<ActivityUser> findAllUsersActivities(long userId);

    @Query(value = "select activity_id from mydb.user_has_activity where user_id = (?) and status = 'requested'", nativeQuery = true)
    List<Long> getRequestedActivitiesIds(long userId);

    @Query(value = "select user_id from mydb.user_has_activity where activity_id = (?) and status = 'requested'", nativeQuery = true)
    List<Long> getUsersWithRequestId(long activityId);

}
