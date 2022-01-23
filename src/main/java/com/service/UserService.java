package com.service;


import com.entity.User;
import com.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    private static final Logger logger = Logger.getLogger(UserService.class);

    @Autowired
    UserRepository repo;

    public boolean saveUser(User user){
        try {
            repo.save(user);
            return true;
        }catch(Exception e){
            logger.info("user " + user.getId() + " wasn't saved");
            return false;
        }

    }

    public User findByName(String name) {
        return repo.findByName(name);
    }

    public User findByNameAndPass(String name, String pass){
        User user = repo.findByName(name);
        if(user.getPassword().equals(pass)){
            return user;
        }else{
            return null;
        }

    }
}
