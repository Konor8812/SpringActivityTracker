package com.repository;

import com.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


        User findByName(String name);

        User findById(long id);

        @Transactional
        @Modifying
        @Query(value = "update user t set t.requests_amount = (t.requests_amount - 1) where t.id = (?) ",
                nativeQuery = true)
        void updateRequestsAmount(long userId);
}
