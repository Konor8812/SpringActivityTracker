package com.repository;


import com.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long>{

     Activity findActivityById(long activityId);

    @Query(value = "select * from activity order by name ASC ", nativeQuery = true)
     ArrayList<Activity> findAllByNameASC();

    @Query(value = "select * from activity order by name DESC ", nativeQuery = true)
    ArrayList<Activity> findAllByNameDESC();

    @Query(value = "select * from activity order by taken_by ASC", nativeQuery = true)
    ArrayList<Activity> findAllByTakesASC();

    @Query(value = "select * from activity order by taken_by DESC ", nativeQuery = true)
    ArrayList<Activity> findAllByTakesDESC();

    @Query(value = "select * from activity order by reward ASC", nativeQuery = true)
    ArrayList<Activity> findAllByRewardASC();

    @Query(value = "select * from activity order by reward DESC ", nativeQuery = true)
    ArrayList<Activity> findAllByRewardDESC();

    @Query(value = "select * from activity order by requested_times ASC", nativeQuery = true)
    ArrayList<Activity> findAllByRequestsASC();

    @Query(value = "select * from activity order by requested_times DESC ", nativeQuery = true)
    ArrayList<Activity> findAllByRequestsDESC();


    @Query(value="select * from activity join activity_description on description_id=activity_description.id where activity_description.description like concat('%', ?, '%')", nativeQuery = true)
    ArrayList<Activity> getAllActivitiesWithTag(String tag);

     @Transactional
     @Modifying
     @Query(value = "delete from activity_description where id=(?)", nativeQuery = true)
     void deleteDescription(long descriptionId);

    @Transactional
     @Modifying
     @Query(value = "SET FOREIGN_KEY_CHECKS = 0", nativeQuery = true)
     void DISABLE_FOREIGN_KEY_CHECKS();

    @Transactional
    @Modifying
    @Query(value = "SET FOREIGN_KEY_CHECKS = 1", nativeQuery = true)
     void ENABLE_FOREIGN_KEY_CHECKS();
}
