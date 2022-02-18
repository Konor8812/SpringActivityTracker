package com.repository;


import com.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long>{

    public Activity findActivityById(long activityId);

    @Query(value = "SELECT description FROM mydb.activity_description WHERE activity_description_id = (?)",
            nativeQuery=true)
    public String getActivityDescription(long activityId);
}
