package com.artatech.bilerman.Repositories;

import com.artatech.bilerman.Entities.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Integer> {
}
