package com.entity;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique=true)
    private String name;

    private String password;

    private String email;

    @Column(name="activities_amount")
    private int activitiesAmount = 0;

    @Column(name="requests_amount")
    private int requestsAmount = 0;

    private String status = "available";

    @Column(name="total_points")
    private int totalPoints = 0;

    private String role = "user";


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getActivitiesAmount() {
        return activitiesAmount;
    }

    public void setActivitiesAmount(int activities_amount) {
        this.activitiesAmount = activities_amount;
    }

    public int getRequestsAmount() {
        return requestsAmount;
    }

    public void setRequestsAmount(int requests_amount) {
        this.requestsAmount = requests_amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int total_points) {
        this.totalPoints = total_points;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public boolean isEmailEmpty(){
        return email == null;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
