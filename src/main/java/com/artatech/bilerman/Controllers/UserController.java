package com.artatech.bilerman.Controllers;

import com.artatech.bilerman.Entities.User;
import com.artatech.bilerman.Exeptions.ResourceNotFoundException;
import com.artatech.bilerman.Security.CurrentUser;
import com.artatech.bilerman.Security.UserPrincipal;
import com.artatech.bilerman.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Iterable<User> getUsers(){
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Integer id){
        return userService.findById(id).orElse(null);
    }

    @GetMapping("/{email}")
    public User getUserByEmail(@PathVariable("email") String email){
        return userService.findByEmail(email);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal currentUser){
        return userService.findById(currentUser.getId()).orElse(null);
    }

    @GetMapping("/profile/{id}")
    public User getUserProfileById(@PathVariable("id") Integer id){
        return userService.findById(id).orElse(null);
    }

    @GetMapping("/profile/{email}")
    public User getUserProfileByEmail(@PathVariable("email") String email){
        return userService.findByEmail(email);
    }


    @PostMapping("/avatar")
    public User updateAvatar(@RequestParam("file") MultipartFile file, @CurrentUser UserPrincipal currentUser){
        return userService.updateAvatar(file, currentUser.getId());
    }

    @PostMapping("/{id}/avatar")
    public User updateAvatar(@RequestParam("file") MultipartFile file, @PathVariable("id") Integer id){
        return userService.updateAvatar(file, id);
    }

    @GetMapping("/avatar")
    public Resource getAvatar(@CurrentUser UserPrincipal currentUser){
        return userService.getAvatar(currentUser.getId());
    }

    @GetMapping("/{id}/avatar")
    public Resource getAvatar(@PathVariable("id") Integer id){
        return userService.getAvatar(id);
    }
}
