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


    public static boolean validateActivity(String name, String duration, double reward, String description){

        Matcher nameMatcher = Pattern.compile("[A-Za-z ]{2,}").matcher(name);
        Matcher durationMatcher = Pattern.compile("(\\d)+((\\.\\d)?) (hours|days)").matcher(duration);
        Matcher descriptionMatcher = Pattern.compile("([a-zA-Z]+)((, [a-zA-Z]+)*)").matcher(description);

        return nameMatcher.matches() && durationMatcher.matches() && descriptionMatcher.matches() && reward > 0;

    }

    public static boolean validateActivityForLocalization(String lang, String enName, String translatedName, String translatedDescription){

        String languages = "ru en";
        boolean langValid = languages.contains(lang.trim().toLowerCase());

        Matcher nameMatcher = Pattern.compile("[A-Za-z ]{2,}").matcher(enName);

        Matcher translatedNameMatcher = Pattern.compile("\\S{2,}").matcher(translatedName);

        Matcher translatedDescriptionMatcher = Pattern.compile("(\\S+)((, \\S+)*)").matcher(translatedDescription);

        System.out.println("lang  =  " + lang + " matches " + langValid);
        System.out.println("en name = " + enName + " matches " + nameMatcher.matches());
        System.out.println("translated name = "  + translatedName + " matches " + translatedNameMatcher.matches());
        System.out.println("description = " + translatedDescription + " matches " + translatedDescriptionMatcher.matches());

        return langValid && nameMatcher.matches() && translatedNameMatcher.matches() && translatedDescriptionMatcher.matches();


    }
}
