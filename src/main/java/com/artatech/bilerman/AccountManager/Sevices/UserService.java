package com.artatech.bilerman.AccountManager.Sevices;

import com.artatech.bilerman.AccountManager.Entities.PasswordResetToken;
import com.artatech.bilerman.AccountManager.Entities.User;
import com.artatech.bilerman.AccountManager.Entities.VerificationToken;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Iterable<User> findAll();

    User findById(Long id);

    User findByEmail(String email);

    List<User> findByIdIn(List<Long> userIds);

    Boolean existsByEmail(String email);

    User create(User user);

    User save(User user);

    void delete(Long userId);

    User updateAvatar(MultipartFile file, Long userId);

    Resource getAvatar(Long userId);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String token);

    void createPasswordResetToken(User user, String token);

    PasswordResetToken getPasswordResetToken(String token);

    void resetPassword(Long userId, String password);
}
