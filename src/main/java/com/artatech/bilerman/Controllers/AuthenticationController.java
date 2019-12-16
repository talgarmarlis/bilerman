package com.artatech.bilerman.Controllers;

import com.artatech.bilerman.Entities.Role;
import com.artatech.bilerman.Entities.User;
import com.artatech.bilerman.Enums.RoleName;
import com.artatech.bilerman.Exeptions.AppException;
import com.artatech.bilerman.Models.Payloads.ApiResponse;
import com.artatech.bilerman.Models.Payloads.JwtAuthenticationResponse;
import com.artatech.bilerman.Models.Payloads.LoginRequest;
import com.artatech.bilerman.Repositories.RoleRepository;
import com.artatech.bilerman.Repositories.UserRepository;
import com.artatech.bilerman.Security.JwtTokenProvider;
import com.artatech.bilerman.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User signUpUser) {
        if(userService.existsByEmail(signUpUser.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        User savedUser = userService.create(signUpUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{email}")
                .buildAndExpand(savedUser.getEmail()).toUri();

        return ResponseEntity.created(location).body(savedUser);
    }
}
