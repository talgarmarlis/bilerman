package com.artatech.bilerman.AccountManager.Repositories;

import com.artatech.bilerman.AccountManager.Entities.User;
import com.artatech.bilerman.AccountManager.Entities.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}
