package com;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class asdas {

    public  void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("admin"));
        System.out.println(new BCryptPasswordEncoder().encode("test"));
        System.out.println(new BCryptPasswordEncoder().encode("test1"));
        System.out.println(new BCryptPasswordEncoder().encode("test2"));
    }
}
