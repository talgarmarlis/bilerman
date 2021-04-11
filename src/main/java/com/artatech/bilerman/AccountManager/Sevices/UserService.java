package com.artatech.bilerman.AccountManager.Sevices;

import com.artatech.bilerman.AccountManager.Entities.PasswordResetToken;
import com.artatech.bilerman.AccountManager.Entities.User;
import com.artatech.bilerman.AccountManager.Entities.VerificationToken;
import com.artatech.bilerman.Entities.Article;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    Iterable<User> findAll();

    User findById(Long id);

    User findByEmail(String email);

    User findByFacebookId(String id);

    User findByGoogleId(String id);

    List<User> findByIdIn(List<Long> userIds);

    Boolean existsById(Long id);

    Boolean existsByEmail(String email);

    Boolean existsByFacebookId(String id);

    Boolean existsByGoogleId(String id);

    User create(User user);

    User save(User user);

    User update(User user);

    void delete(Long userId);

    User updateAvatar(MultipartFile file, Long userId);

    Resource getAvatar(Long userId);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String token);

    void createPasswordResetToken(User user, String token);

    PasswordResetToken getPasswordResetToken(String token);

    void resetPassword(Long userId, String password);

    User saveArticle(Long id, Article article);

    User clapArticle(Long id, Article article);

    String generatePassword(Integer length);
}
