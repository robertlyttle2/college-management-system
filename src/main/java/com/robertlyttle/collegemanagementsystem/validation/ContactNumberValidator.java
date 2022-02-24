package com.robertlyttle.collegemanagementsystem.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactNumberValidator {

    private static final String UK_PHONE_PATTERN = "^(?:0|\\+?44)(?:\\d\\s?){9,10}$";
    private static final Pattern PATTERN = Pattern.compile(UK_PHONE_PATTERN);

    public static boolean isValid(String contactNumber) {
        Matcher matcher = PATTERN.matcher(contactNumber);
        return matcher.matches();
    }
}
