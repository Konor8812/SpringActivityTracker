package com.entity;


import javax.persistence.*;
import javax.validation.constraints.Pattern;


@Table(name="user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Pattern(regexp="([a-zA-Z0-9_]+)@([a-zA-Z0-9_]+)\\.([a-zA-Z]+)", message = "incorrect email format")
    private String email;

    @Pattern(regexp = "[a-zA-Z_]")
    private String name;

    private String password;

    private int activities_amount;

    private int requests_amount;

    private String status;

    private int total_points;

    private String role;

    protected User(){}

    protected User(int id, String email, String name, String password){}


}
