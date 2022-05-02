package com.repository;


import com.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long>{

     Activity findActivityById(long activityId);

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
