package com.robertlyttle.collegemanagementsystem.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameValidator {
    private static final String FIRST_NAME_PATTERN = "[a-zA-Z][a-zA-Z ]{1,25}$*";
    private static final String LAST_NAME_PATTERN = "[a-zA-z]{1,25}+([ '-][a-zA-Z]{1,25}+)*";

    public static boolean isFirstNameValid(String name) {
        Matcher matcher = Pattern.compile(FIRST_NAME_PATTERN).matcher(name);
        return matcher.matches();
    }

    public static boolean isLastNameValid(String name) {
        Matcher matcher = Pattern.compile(LAST_NAME_PATTERN).matcher(name);
        return matcher.matches();
    }
}
