package com.repository;

import com.entity.ActivityUser;
import com.entity.embedded.ActivityUserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityUserRepository extends JpaRepository<ActivityUser, ActivityUserId> {

    List<ActivityUser> findByActivityUserId(ActivityUserId activityUserId);
}
