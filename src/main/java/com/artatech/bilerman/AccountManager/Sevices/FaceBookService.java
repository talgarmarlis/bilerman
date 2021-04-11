package com.artatech.bilerman.AccountManager.Sevices;

import com.artatech.bilerman.AccountManager.Entities.User;
import com.artatech.bilerman.AccountManager.Models.FacebookPictureData;
import com.artatech.bilerman.AccountManager.Models.FacebookUser;
import com.artatech.bilerman.Enums.ImageCategory;
import com.artatech.bilerman.Services.ImageService;
import com.artatech.bilerman.Services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class FaceBookService {

    private final String FACEBOOK_GRAPH_API_BASE = "https://graph.facebook.com";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    UserService userService;

    @Autowired
    ImageService imageService;

    public User getUser(String fbAccessToken) {
        FacebookUser fbUser = getFbUser(fbAccessToken);
        if(userService.existsByFacebookId(fbUser.getId()))
            return userService.findByFacebookId(fbUser.getId());

        if(fbUser.getEmail() == null) fbUser.setEmail(fbUser.getId() + "@fb.com");

        if(userService.existsByEmail(fbUser.getEmail()))
            return userService.findByEmail(fbUser.getEmail());

        return registerFbUser(fbUser);
    }

    private User registerFbUser(FacebookUser fbUser) {
        User newUser =  convertTo(fbUser);
        FacebookPictureData pData = fbUser.getPicture().getData();
        if(!pData.getIs_silhouette()){
            Long imageId = imageService.upload(ImageCategory.USER, pData.getUrl());
            newUser.setAvatar(imageId);
        }
        newUser = userService.create(newUser);
        return newUser;
    }

    private User convertTo(FacebookUser fbUser) {
        User convertedUser = new User();
        convertedUser.setFacebookId(fbUser.getId());
        convertedUser.setName(fbUser.getFirstName());
        convertedUser.setSurname(fbUser.getLastName());
        convertedUser.setEmail(fbUser.getEmail());
        convertedUser.setPassword(userService.generatePassword(10));
        convertedUser.setEnabled(true);
        return convertedUser;
    }

    public FacebookUser getFbUser(String accessToken) {
        var path = "/me?fields={fields}&redirect={redirect}&access_token={access_token}";
        var fields = "email,first_name,last_name,id,picture.width(720).height(720)";
        final Map<String, String> variables = new HashMap<>();
        variables.put("fields", fields);
        variables.put("redirect", "false");
        variables.put("access_token", accessToken);
        return restTemplate
                .getForObject(FACEBOOK_GRAPH_API_BASE + path, FacebookUser.class, variables);
    }
}
