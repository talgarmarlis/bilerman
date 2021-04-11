package com.artatech.bilerman.AccountManager.Sevices;

import com.artatech.bilerman.AccountManager.Entities.PasswordResetToken;
import com.artatech.bilerman.AccountManager.Entities.Role;
import com.artatech.bilerman.AccountManager.Entities.User;
import com.artatech.bilerman.AccountManager.Entities.VerificationToken;
import com.artatech.bilerman.AccountManager.Enums.RoleName;
import com.artatech.bilerman.AccountManager.Repositories.PasswordResetTokenRepository;
import com.artatech.bilerman.AccountManager.Repositories.VerificationTokenRepository;
import com.artatech.bilerman.Entities.Article;
import com.artatech.bilerman.Entities.Clap;
import com.artatech.bilerman.Entities.SavedArticle;
import com.artatech.bilerman.Enums.ImageCategory;
import com.artatech.bilerman.Exeptions.AppException;
import com.artatech.bilerman.Exeptions.ResourceNotFoundException;
import com.artatech.bilerman.AccountManager.Repositories.RoleRepository;
import com.artatech.bilerman.AccountManager.Repositories.UserRepository;
import com.artatech.bilerman.Services.ImageService;
import com.artatech.bilerman.Services.StorageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    RoleRepository roleRepository;

    PasswordEncoder passwordEncoder;

    ImageService imageService;

    StorageService storageService;

    VerificationTokenRepository tokenRepository;

    PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                           ImageService imageService, StorageService storageService, VerificationTokenRepository tokenRepository,
                           PasswordResetTokenRepository passwordResetTokenRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.imageService = imageService;
        this.storageService = storageService;
        this.tokenRepository = tokenRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user", "id", id));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email.trim().toLowerCase());
    }

    @Override
    public User findByFacebookId(String id) {
        return userRepository.findByFacebookId(id);
    }

    @Override
    public User findByGoogleId(String id) {
        return userRepository.findByGoogleId(id);
    }

    @Override
    public List<User> findByIdIn(List<Long> userIds) {
        return userRepository.findByIdIn(userIds);
    }

    @Override
    public Boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email.trim().toLowerCase());
    }

    @Override
    public Boolean existsByFacebookId(String id) {
        return userRepository.existsByFacebookId(id);
    }

    @Override
    public Boolean existsByGoogleId(String id) {
        return userRepository.existsByGoogleId(id);
    }

    @Override
    public User create(User user) {
        // Creating user's account
        user.setEmail(user.getEmail().trim().toLowerCase());
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
    public User update(User user) {
        User saved = findById(user.getId());
        BeanUtils.copyProperties(user, saved, "id", "email", "password", "avatar", "roles", "claps", "savedArticles");
        return save(saved);
    }

    @Override
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User updateAvatar(MultipartFile file, Long userId) {
        User user = findById(userId);
        if(user.getAvatar() != null){
            Long imageId = user.getAvatar();
            user.setAvatar(null);
            save(user);
            imageService.delete(ImageCategory.USER, imageId);
        }
        Long imageId = imageService.upload(ImageCategory.USER, file);
        user.setAvatar(imageId);
        return save(user);
    }

    @Override
    public Resource getAvatar(Long userId) {
        User user = findById(userId);
        return imageService.download(ImageCategory.USER, user.getAvatar());
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken verificationToken = tokenRepository.findByUser(user);
        if(verificationToken != null) tokenRepository.delete(verificationToken);

        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public void createPasswordResetToken(User user, String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByUser(user);
        if(passwordResetToken != null) passwordResetTokenRepository.delete(passwordResetToken);

        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(myToken);
    }

    @Override
    public PasswordResetToken getPasswordResetToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    public void resetPassword(Long userId, String password) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setPassword(passwordEncoder.encode(password));
        save(user);
    }

    @Override
    public User saveArticle(Long id, Article article) {
        User user = findById(id);
        boolean exists = false;
        SavedArticle toRemove = new SavedArticle();
        for(SavedArticle savedArticle: user.getSavedArticles()) {
            if (savedArticle.getArticle().equals(article)) {
                exists = true;
                toRemove = savedArticle;
            }
        }

        if(exists) user.getSavedArticles().remove(toRemove);
        else {
            SavedArticle savedArticle = new SavedArticle(article, user);
            user.getSavedArticles().add(savedArticle);
        }
        return save(user);
    }

    @Override
    public User clapArticle(Long id, Article article) {
        User user = findById(id);
        boolean exists = false;
        for(Clap clap: user.getClaps()) {
            if (clap.getArticle().equals(article)) {
                exists = true;
                clap.setCount(clap.getCount() + 1);
            }
        }

        if(!exists) {
            Clap clap = new Clap(article, user);
            user.getClaps().add(clap);
        }
        return save(user);
    }

    @Override
    public String generatePassword(Integer length) {
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random();
        char[] password = new char[length];

        password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
        password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
        password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        password[3] = numbers.charAt(random.nextInt(numbers.length()));

        for(int i = 4; i< length ; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        return new String(password);
    }
}
