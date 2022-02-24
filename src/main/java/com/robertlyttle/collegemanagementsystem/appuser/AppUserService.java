package com.robertlyttle.collegemanagementsystem.appuser;

import com.robertlyttle.collegemanagementsystem.appuser.enumeration.AppUserRole;
import com.robertlyttle.collegemanagementsystem.exception.model.NameNotValidException;
import com.robertlyttle.collegemanagementsystem.security.login.LoginAttemptService;
import com.robertlyttle.collegemanagementsystem.validation.NameValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.ExecutionException;


@AllArgsConstructor
@Service
@Transactional
@Slf4j
@Qualifier("myUserDetailsService")
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final LoginAttemptService loginAttemptService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final String USER_NOT_FOUND_MESSAGE = "No user found with username: ";
        final String USER_FOUND_MESSAGE = "Found user with username: ";
        AppUser user = appUserRepository.findAppUserByUsername(username);
        if (user == null) {
            log.error(USER_NOT_FOUND_MESSAGE + username);
            throw new UsernameNotFoundException(USER_NOT_FOUND_MESSAGE + username);
        }
        validateLogin(user);
        log.info(USER_FOUND_MESSAGE + username);
        return user;
    }

    public AppUser addNewUser(String firstName, String lastName, AppUserRole appUserRole) {
        if (validateName(firstName, lastName)) {
            String password = generatePassword();
            String username = generateCollegeEmail(firstName, lastName);
            AppUser newAppUser = new AppUser();
            newAppUser.setFirstName(StringUtils.capitalize(firstName));
            newAppUser.setLastName(StringUtils.capitalize(lastName));
            newAppUser.setUsername(username);
            newAppUser.setPassword(passwordEncoder.encode(password));
            newAppUser.setRole(String.valueOf(appUserRole));
            newAppUser.setAuthorities(appUserRole.getAuthorities());
            newAppUser.setEnabled(true);
            newAppUser.setNonLocked(true);
            log.info("New user '{} {}' created", firstName, lastName);
            log.info("New user password: {}", password); // Will delete - only used for testing purposes
            return appUserRepository.save(newAppUser);
        }
        return null;
    }

    public AppUser findUserByUsername(String username) {
        return appUserRepository.findAppUserByUsername(username);
    }

    public String generateCollegeEmail(String firstName, String lastName) {
        final String EMAIL_SUFFIX = "@belfastcollege.ac.uk";

        String generatedUsername = StringUtils.deleteWhitespace(firstName).toLowerCase()
                + "."
                + StringUtils.deleteWhitespace(lastName).toLowerCase()
                + EMAIL_SUFFIX;

        int increment = 1;
        while (appUserRepository.findAppUserByUsername(generatedUsername) != null) {
            generatedUsername = StringUtils.deleteWhitespace(firstName).toLowerCase()
                    + "."
                    + StringUtils.deleteWhitespace(lastName).toLowerCase()
                    + increment
                    + EMAIL_SUFFIX;
            increment++;
        }
        return generatedUsername;
    }

    public String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    public String generateCollegeNumber() {
        final String STUDENT_NUMBER_PREFIX = "B00";
        return STUDENT_NUMBER_PREFIX + RandomStringUtils.randomNumeric(6);
    }

    public boolean validateName(String firstName, String lastName) {
        final String FIRST_NAME_INVALID = "First name is not a valid name. Please enter a valid name under 25 characters";
        final String LAST_NAME_INVALID = "Last name is not a valid name. Please enter a valid name under 25 characters";

        if (!NameValidator.isFirstNameValid(firstName)) {
            log.info(FIRST_NAME_INVALID);
            throw new NameNotValidException(FIRST_NAME_INVALID);
        }

        if (!NameValidator.isLastNameValid(lastName)) {
            log.info(LAST_NAME_INVALID);
            throw new NameNotValidException(LAST_NAME_INVALID);
        }

        return true;
    }

    private void validateLogin(AppUser user) {
        if (user.isNonLocked()) {
            try {
                user.setNonLocked(!loginAttemptService.hasExceededMaxAttempts(user.getUsername()));
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            loginAttemptService.removeUserFromCache(user.getUsername());
        }
    }
}
