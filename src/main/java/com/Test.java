package com;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {

    public  void main(String[] args) {
        
        String s = "Dermatoglyphics";
        String s2 = "aba";
        String s3 = "moOse";

        System.out.println(seeIfWordIsAnIsogram(s));
        System.out.println(seeIfWordIsAnIsogram(s2));
        System.out.println(seeIfWordIsAnIsogram(s3));

        System.out.println(new BCryptPasswordEncoder().encode("admin"));
    }

    public static boolean seeIfWordIsAnIsogram(String word){

        word = word.toLowerCase();
        char[] chars = word.toCharArray();

        for(int i = 0; i < chars.length; i++){

            for(int k = i + 1; k < chars.length; k++){
                if(chars[k] == chars[i]){
                    return false;
                }

            }
        }
        return true;
    }
}
