package com.artatech.bilerman.Repositories;

import com.artatech.bilerman.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    List<User> findByIdIn(List<Integer> userIds);

    Boolean existsByEmail(String email);
}
