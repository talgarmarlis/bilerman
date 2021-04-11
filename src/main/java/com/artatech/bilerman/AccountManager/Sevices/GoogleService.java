package com.artatech.bilerman.AccountManager.Sevices;

import com.artatech.bilerman.AccountManager.Entities.User;
import com.artatech.bilerman.AccountManager.Models.GoogleUser;
import com.artatech.bilerman.Enums.ImageCategory;
import com.artatech.bilerman.Services.ImageService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.json.MockJsonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleService {

    @Value("${security.oauth2.client.registration.google.client-id:client-id}")
    private String CLIENT_ID;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    UserService userService;

    @Autowired
    ImageService imageService;

    public User getUser(String gToken) {
        GoogleUser gUser = getGoogleUser(gToken);
        if(userService.existsByGoogleId(gUser.getId()))
            return userService.findByGoogleId(gUser.getId());

        if(gUser.getEmail() == null) gUser.setEmail(gUser.getId() + "@fb.com");

        if(userService.existsByEmail(gUser.getEmail()))
            return userService.findByEmail(gUser.getEmail());

        return registerFbUser(gUser);
    }

    private User registerFbUser(GoogleUser gUser) {
        User newUser =  convertTo(gUser);
        if(gUser.getPictureUrl() != null){
            Long imageId = imageService.upload(ImageCategory.USER, gUser.getPictureUrl());
            newUser.setAvatar(imageId);
        }
        newUser = userService.create(newUser);
        return newUser;
    }

    private User convertTo(GoogleUser gUser) {
        User convertedUser = new User();
        convertedUser.setGoogleId(gUser.getId());
        convertedUser.setName(gUser.getFirstName());
        convertedUser.setSurname(gUser.getLastName());
        convertedUser.setEmail(gUser.getEmail());
        convertedUser.setPassword(userService.generatePassword(10));
        convertedUser.setEnabled(true);
        return convertedUser;
    }

    public GoogleUser getGoogleUser(String gToken) {
        NetHttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new GsonFactory();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(CLIENT_ID))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        try {
            GoogleIdToken idToken = GoogleIdToken.parse(verifier.getJsonFactory(), gToken);
            boolean tokenIsValid = (idToken != null) && verifier.verify(idToken);
//            GoogleIdToken idToken = verifier.verify(gToken);
            if (idToken != null) {
                Payload payload = idToken.getPayload();
                // Print user identifier
                String googleId = payload.getSubject();
                System.out.println("User ID: " + googleId);

                GoogleUser gUser = new GoogleUser();
                gUser.setFirstName(payload.get("given_name").toString());
                gUser.setLastName(payload.get("family_name").toString());
                gUser.setPictureUrl(payload.get("picture").toString());
                gUser.setEmail(payload.get("email").toString());
                gUser.setId(googleId);
                return gUser;
            } else {
                System.out.println("Invalid ID token.");
            }
        }
        catch (IOException | GeneralSecurityException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
