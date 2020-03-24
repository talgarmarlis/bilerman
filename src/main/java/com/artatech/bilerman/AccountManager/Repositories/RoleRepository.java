package com.artatech.bilerman.AccountManager.Repositories;

import com.artatech.bilerman.AccountManager.Entities.Role;
import com.artatech.bilerman.AccountManager.Enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleName name);
}
