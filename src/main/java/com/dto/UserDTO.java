package com.dto;

import com.entity.User;

public class UserDTO {

    private final long id;
    private final String name;
    private final String status;
    private final String email;
    private final int activitiesAmount;
    private final int requestsAmount;
    private final double totalPoints;

    private final String role;


    private UserDTO(User user) {
        this.id=user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.activitiesAmount = user.getActivitiesAmount();
        this.requestsAmount = user.getRequestsAmount();
        this.totalPoints = user.getTotalPoints();
        this.status = user.getStatus();

        this.role = user.getRoles().toString().contains("user") ? "user" : "admin";
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

    public double getTotalPoints() {
        return totalPoints;
    }

    public long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

}
