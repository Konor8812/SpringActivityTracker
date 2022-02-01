package com.service;


import com.dto.UserDTO;
import com.entity.User;
import com.repository.UserRepository;

import org.apache.log4j.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = Logger.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    public boolean saveUser(User user){
        try {
            userRepository.save(user);
            return true;
        }catch(Exception e){
            logger.info("user " + user.getName() + " wasn't saved");
            return false;
        }
    }

    public User findByNameAndPass(String name, String pass){
        User user = userRepository.findByName(name);
        if(user != null && user.getPassword().equals(pass)){
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

    public void updateUserPass(long id, String value) {
        User user = userRepository.findById(id);
        user.setPassword(value);
        userRepository.save(user);
    }

    public void updateUserEmail(long id, String value) {
        User user = userRepository.findById(id);
        user.setEmail(value);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findByName(name);

        if(user == null){
            throw new UsernameNotFoundException("user " + name + " wasn't found");
        }

        return user;
    }

    public void updatePointsAmount(long userId, double points){



    }

    public void updateRequestsAmoubt(long userId, boolean increment){

        if(increment){

        } else{

        }
    }

    public void updateActivitiesEmount(long userId, boolean increment){

    }

    public void updateStatus(long userId, String status){

    }

    public void deleteUser(long userId){

    }
}
