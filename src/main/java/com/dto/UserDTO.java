package com.dto;

import com.entity.User;

public class UserDTO {

    private final String name;
    private final String email;
    private final int activities_amount;
    private final int requests_amount;
    private final int total_points;
    private final String role;

    private UserDTO(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.activities_amount = user.getActivities_amount();
        this.requests_amount = user.getRequests_amount();
        this.total_points = user.getTotal_points();
        this.role = user.getRole();
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

    public int getActivities_amount() {
        return activities_amount;
    }

    public int getRequests_amount() {
        return requests_amount;
    }

    public int getTotal_points() {
        return total_points;
    }

    public String getRole() {
        return role;
    }
}
