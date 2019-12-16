package com.artatech.bilerman.Services;

import com.artatech.bilerman.Entities.User;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Iterable<User> findAll();

    Optional<User> findById(Integer id);

    User findByEmail(String email);

    List<User> findByIdIn(List<Integer> userIds);

    Boolean existsByEmail(String email);

    User create(User user);

    User save(User user);

    void delete(Integer userId);

    User updateAvatar(MultipartFile file, Integer userId);

    Resource getAvatar(Integer userId);
}
