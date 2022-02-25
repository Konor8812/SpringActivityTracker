package com.service;


import com.dto.UserDTO;
import com.entity.Role;
import com.entity.User;
import com.repository.UserRepository;

import com.util.Util;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = Logger.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByName(user.getName());

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(new Role(2L, "user")));
        user.setPassword(Util.bCryptEncode(user.getPassword()));

        userRepository.save(user);
        return true;

    }

    public User findUserById(long userId) {
        return userRepository.findById(userId);
    }

    public List<UserDTO> getAllUsersList() {
        List<User> users = userRepository.findAll();
        List<UserDTO> dtos = new ArrayList<>();
        for (User u : users) {
            dtos.add(UserDTO.parseUser(u));
        }
        return dtos;
    }

    public void updateUserPass(long id, String value) {
        User user = userRepository.findById(id);
        String encodedPass = Util.bCryptEncode(value);
        user.setPassword(encodedPass);
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

        if (user == null) {
            throw new UsernameNotFoundException("user " + name + " wasn't found");
        }

        return user;
    }

    public void updatePointsAmount(long userId, double points) {
        User user = findUserById(userId);
        user.setTotalPoints(user.getTotalPoints() + points);

        userRepository.save(user);
    }

    public void updateRequestsAmount(long userId, boolean increment) {
        User user = findUserById(userId);
        user.setRequestsAmount(increment ? user.getRequestsAmount() + 1 : user.getRequestsAmount() - 1);
        userRepository.save(user);

    }

    public void updateActivitiesAmount(long userId) {
        User user = findUserById(userId);
        user.setActivitiesAmount(user.getActivitiesAmount() + 1);
        userRepository.save(user);
    }

    public void updateStatus(long userId, String status) {
        User user = userRepository.findById(userId);
        user.setStatus(status);
        userRepository.save(user);
    }

    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }
}
