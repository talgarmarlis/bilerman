package com.artatech.bilerman.Services;

import com.artatech.bilerman.Entities.Role;
import com.artatech.bilerman.Entities.User;
import com.artatech.bilerman.Enums.RoleName;
import com.artatech.bilerman.Exeptions.AppException;
import com.artatech.bilerman.Exeptions.ResourceNotFoundException;
import com.artatech.bilerman.Repositories.RoleRepository;
import com.artatech.bilerman.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final String AVATAR_PATH = "/avatar";

    UserRepository userRepository;

    RoleRepository roleRepository;

    PasswordEncoder passwordEncoder;

    StorageService storageService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                           StorageService storageService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.storageService = storageService;
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findByIdIn(List<Integer> userIds) {
        return userRepository.findByIdIn(userIds);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User create(User user) {
        // Creating user's account
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));
        user.setRoles(Collections.singleton(userRole));

        return userRepository.save(user);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(Integer userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User updateAvatar(MultipartFile file, Integer userId) {
        User user = findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if(user.getAvatar() != null) storageService.delete(user.getAvatar(), AVATAR_PATH);
        String fileName = storageService.store(file, AVATAR_PATH);
        user.setAvatar(fileName);
        return save(user);
    }

    @Override
    public Resource getAvatar(Integer userId) {
        User user = findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        if(user.getAvatar() == null) return storageService.load("default.png", AVATAR_PATH);

        return storageService.load(user.getAvatar(), AVATAR_PATH);
    }
}
