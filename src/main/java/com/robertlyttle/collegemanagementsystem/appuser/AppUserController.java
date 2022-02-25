package com.robertlyttle.collegemanagementsystem.appuser;

import com.robertlyttle.collegemanagementsystem.appuser.enumeration.AppUserRole;
import com.robertlyttle.collegemanagementsystem.security.jwt.JwtTokenProvider;
import com.robertlyttle.collegemanagementsystem.security.login.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<AppUser> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        AppUser user = appUserService.findUserByUsername(request.getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Jwt-Token", jwtTokenProvider.generateJwtToken(user));
        return new ResponseEntity<>(user, headers, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('user:create')")
    @PostMapping("/add")
    public ResponseEntity<AppUser> addNewUser(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("role") AppUserRole appUserRole) {
        AppUser newUser = appUserService.addNewUser(firstName, lastName, appUserRole);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    // TODO - Reset Password


}
