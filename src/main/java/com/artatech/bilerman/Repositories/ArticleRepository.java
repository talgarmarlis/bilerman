package com.artatech.bilerman.Repositories;

import com.artatech.bilerman.Entities.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Collection<Article> findAllByCreatedBy(Long userId);

    Page<Article> findAllByCreatedBy(Long userId, Pageable pageable);

    Page<Article> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Article> findAllByCreatedByAndTitleContainingIgnoreCase(Long userId, String title, Pageable pageable);

    void deleteById(Long id);
}
