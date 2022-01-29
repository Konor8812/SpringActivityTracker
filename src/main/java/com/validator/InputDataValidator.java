package com.validator;

import java.util.regex.*;

public class InputDataValidator {

    public static boolean validateLoginInput(String name,
                                             String pass,
                                             String email){

        if(!(email == null) && !email.isEmpty()){
            return validateName(name) && validatePassword(pass) && validateEmail(email);
        } else{
            return validateName(name) && validatePassword(pass);
        }

    }

    public static boolean validateName(String name){
        Matcher nameMatcher = Pattern.compile("[a-zA-Z0-9]{4,16}").matcher(name);
        return nameMatcher.matches();
    }

    public static boolean validatePassword(String password){
        Matcher passMatcher = Pattern.compile("[a-zA-Z0-9~!@#$%^&*()-_+=]{4,16}").matcher(password);
        return passMatcher.matches();
    }

    public static boolean validateEmail(String email){
        Matcher emailMatcher = Pattern.compile("([a-zA-Z_0-9]+)@([a-zA-Z]+)\\.([a-z]+)").matcher(email);
        return emailMatcher.matches();
    }
}
