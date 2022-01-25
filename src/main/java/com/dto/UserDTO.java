package com.dto;

import com.entity.User;

public class UserDTO {

    private final String name;
    private final String status;
    private final String email;
    private final int activitiesAmount;
    private final int requestsAmount;
    private final int totalPoints;

    private UserDTO(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.activitiesAmount = user.getActivitiesAmount();
        this.requestsAmount = user.getRequestsAmount();
        this.totalPoints = user.getTotalPoints();
        this.status = user.getStatus();
    }

    public String getStatus() {
        return status;
    }

    public static UserDTO parseUser(User user){
        return new UserDTO(user);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getActivitiesAmount() {
        return activitiesAmount;
    }

    public int getRequestsAmount() {
        return requestsAmount;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

}
