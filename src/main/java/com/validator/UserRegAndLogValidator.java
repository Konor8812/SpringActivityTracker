package com.validator;

import java.util.regex.*;

public class UserRegAndLogValidator {


    public static boolean validateLoginInput(String name,
                                             String pass,
                                             String email){
        Matcher nameMatcher = Pattern.compile("[a-zA-Z0-9]{4,16}").matcher(name);
        Matcher passMatcher = Pattern.compile("[a-zA-Z0-9~!@#$%^&*()-_+=]{4,16}").matcher(pass);

        if(email != null){
            Matcher emailMatcher = Pattern.compile("([a-zA-Z_0-9]+)@([a-zA-Z]+)\\.([a-z]+)").matcher(email);
            return emailMatcher.matches() && nameMatcher.matches() && passMatcher.matches();
        } else{
            return nameMatcher.matches() && passMatcher.matches();
        }

    }
}
