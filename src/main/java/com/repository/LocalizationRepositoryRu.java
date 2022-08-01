package com.repository;


import com.entity.LocalizationActivityRu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalizationRepositoryRu extends JpaRepository<LocalizationActivityRu, String> {

    @Query(value = "select * from localization_en_ru where activity_name_en = (?) limit 1", nativeQuery = true)
    LocalizationActivityRu getLocActivityByName(String name);

}
