package com.artatech.bilerman.AccountManager.Controllers;

import com.artatech.bilerman.AccountManager.Entities.PasswordResetToken;
import com.artatech.bilerman.AccountManager.Entities.User;
import com.artatech.bilerman.AccountManager.Entities.VerificationToken;
import com.artatech.bilerman.AccountManager.Models.Payloads.ApiResponse;
import com.artatech.bilerman.AccountManager.Models.Payloads.JwtAuthenticationResponse;
import com.artatech.bilerman.AccountManager.Models.LoginRequest;
import com.artatech.bilerman.AccountManager.Security.CustomOAuth2User;
import com.artatech.bilerman.AccountManager.Security.JwtTokenProvider;
import com.artatech.bilerman.AccountManager.Sevices.*;
import com.artatech.bilerman.Exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.util.Calendar;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    FaceBookService faceBookService;

    @Autowired
    GoogleService googleService;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    private MessageSource messages;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail().trim().toLowerCase(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/facebook/{token}")
    public ResponseEntity<?> authenticateFacebookUser(@PathVariable("token") String fbAccessToken) {
        User user = faceBookService.getUser(fbAccessToken);
        UserDetails userDetails = customUserDetailsService.loadUserById(user.getId());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/google/{token}")
    public ResponseEntity<?> authenticateGoogleUser(@PathVariable("token") String gToken) {
        User user = googleService.getUser(gToken);
        UserDetails userDetails = customUserDetailsService.loadUserById(user.getId());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User signUpUser, WebRequest request) {
        User checkUser = userService.findByEmail(signUpUser.getEmail());
        if(checkUser != null) {
            if(!checkUser.isEnabled()) userService.delete(checkUser.getId());
            else{
                return new ResponseEntity(new ApiResponse(false, "Email is already taken!"),
                        HttpStatus.BAD_REQUEST);
            }
        }

        User savedUser = userService.create(signUpUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{email}")
                .buildAndExpand(savedUser.getEmail()).toUri();

        authenticationService.sendEmailVerificationEmail(savedUser, request.getLocale());

        return ResponseEntity.created(location).body(savedUser);
    }

    @PostMapping("/sendRegistrationToken/{userId}")
    public void resendRegistrationToken(@PathVariable("userId") Long userId, WebRequest request) {
        User user = userService.findById(userId);
        authenticationService.sendEmailVerificationEmail(user, request.getLocale());
    }


    @PostMapping(value = "/confirm")
    public ResponseEntity<?> confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {

        Locale locale = request.getLocale();

        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null)
            return new ResponseEntity(new ApiResponse(false, "Invalid activation link"), HttpStatus.BAD_REQUEST);

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0)
            return new ResponseEntity(new ApiResponse(false, "Activation link has been expired"), HttpStatus.BAD_REQUEST);

        user.setEnabled(true);
        return new ResponseEntity(userService.save(user), HttpStatus.OK);
    }

    @PostMapping("/requestPasswordResetLink")
    public void sendPasswordResetLink(@RequestParam("email") String email, WebRequest request) {
        User user = userService.findByEmail(email);
        if(user == null) throw new ResourceNotFoundException("User", "email", email);
        authenticationService.sendPasswordResetEmail(user, request.getLocale());
    }

    @PostMapping(value = "/passwordResetToken")
    public ResponseEntity<?> getPasswordResetUser(WebRequest request, Model model, @RequestParam("token") String token) {

        PasswordResetToken passwordResetToken = userService.getPasswordResetToken(token);
        if (passwordResetToken == null)
            return new ResponseEntity(new ApiResponse(false, "Invalid reset link"), HttpStatus.BAD_REQUEST);

        User user = passwordResetToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((passwordResetToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0)
            return new ResponseEntity(new ApiResponse(false, "Reset link has been expired"), HttpStatus.BAD_REQUEST);

        return new ResponseEntity(userService.save(user), HttpStatus.OK);
    }

    @PostMapping(value = "/resetPassword/user/{userId}")
    public void resetUserPassword(@PathVariable("userId") Long userId, @Valid @RequestBody User user) {
        userService.resetPassword(userId, user.getPassword());
    }
}
