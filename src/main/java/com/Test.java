package com;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {

    public static void main(String[] args) {

        System.out.println(350 * 7 / 36 / 45);

        System.out.println(new BCryptPasswordEncoder().encode("admin"));
    }

}
