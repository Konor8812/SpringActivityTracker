package com.service;


import com.entity.User;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @Autowired
    UserRepository repo;

    public boolean saveUser(User user){
        repo.save(user);
        return true;
    }

    public User findByName(String name) {
        return repo.findByName(name);
    }
}
