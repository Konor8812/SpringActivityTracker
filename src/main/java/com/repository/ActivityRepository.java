package com.repository;


import com.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long>{

     Activity findActivityById(long activityId);

     @Query(value = "select * from activity order by name", nativeQuery = true)
     ArrayList<Activity> findAll();

//    @Query(value = "SELECT MAX(a) FROM EntityA a " +
//            "INNER JOIN EntityB b on a.Bid = b.id " +
//            "WHERE b.paramY = :paramY " +
//            "AND b.paramZ = :paramZ " +
//            "AND a.paramX = :paramX " +
//            "ORDER BY a.id DESC")
//    EntityA findLastEntityAByParamsXYZ(
//            @Param("paramX") String paramX,
//            @Param("paramY") String paramY,
//            @Param("paramZ") String paramZ);

//    @Query(value = "select * from activity order by name DESC ", nativeQuery = true)
//    ArrayList<Activity> findAllByNameDESC();
//
//    @Query(value = "select * from activity order by taken_by ASC", nativeQuery = true)
//    ArrayList<Activity> findAllByTakesASC();
//
//    @Query(value = "select * from activity order by taken_by DESC ", nativeQuery = true)
//    ArrayList<Activity> findAllByTakesDESC();
//
//    @Query(value = "select * from activity order by reward ASC", nativeQuery = true)
//    ArrayList<Activity> findAllByRewardASC();
//
//    @Query(value = "select * from activity order by reward DESC ", nativeQuery = true)
//    ArrayList<Activity> findAllByRewardDESC();
//
//    @Query(value = "select * from activity order by requested_times ASC", nativeQuery = true)
//    ArrayList<Activity> findAllByRequestsASC();
//
//    @Query(value = "select * from activity order by requested_times DESC ", nativeQuery = true)
//    ArrayList<Activity> findAllByRequestsDESC();


    @Query(value="select * from activity join activity_description on description_id=activity_description.id where activity_description.description like concat('%', ?, '%')", nativeQuery = true)
    ArrayList<Activity> getAllActivitiesWithTag(String tag);

     @Transactional
     @Modifying
     @Query(value = "delete from activity_description where id=(?)", nativeQuery = true)
     void deleteDescription(long descriptionId);

}
