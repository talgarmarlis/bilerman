package com.artatech.bilerman.AccountManager.Repositories;

import com.artatech.bilerman.AccountManager.Entities.PasswordResetToken;
import com.artatech.bilerman.AccountManager.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);
}
