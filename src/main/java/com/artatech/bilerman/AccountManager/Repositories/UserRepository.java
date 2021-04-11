package com.artatech.bilerman.AccountManager.Repositories;

import com.artatech.bilerman.AccountManager.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByFacebookId(String id);

    User findByGoogleId(String id);

    List<User> findByIdIn(List<Long> userIds);

    Boolean existsByEmail(String email);

    Boolean existsByFacebookId(String id);

    Boolean existsByGoogleId(String id);
}
