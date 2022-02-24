package com.robertlyttle.collegemanagementsystem.security.login;

import com.robertlyttle.collegemanagementsystem.appuser.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationSuccessListener {

    private final LoginAttemptService loginAttemptService;

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {
        AppUser user = (AppUser) event.getAuthentication().getPrincipal();
        loginAttemptService.removeUserFromCache(user.getUsername());
    }
}
