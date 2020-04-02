package com.artatech.bilerman.Repositories;

import com.artatech.bilerman.Entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Collection<Article> findAllByCreatedByAndPublished(Long userId, Boolean published);

    void deleteById(Long id);
}
