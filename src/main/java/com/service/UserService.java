package com.service;


import com.dto.UserDTO;
import com.entity.User;
import com.repository.UserRepository;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserService {

    private static final Logger logger = Logger.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    public boolean saveUser(User user){
        try {
            userRepository.save(user);
            return true;
        }catch(Exception e){
            logger.info("user " + user.getId() + " wasn't saved");
            return false;
        }

    }

    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    public User findByNameAndPass(String name, String pass){
        User user = userRepository.findByName(name);
        if(user.getPassword().equals(pass)){
            return user;
        }else{
            return null;
        }
    }

    public List<UserDTO> getAllUsersList(){
        List<User> users = userRepository.findAll();
        List<UserDTO> dtos = new ArrayList<>();
        for(User u: users){
            dtos.add(UserDTO.parseUser(u));
        }
        return dtos;
    }
}
