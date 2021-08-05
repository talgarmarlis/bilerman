package com.artatech.bilerman.Repositories;

import com.artatech.bilerman.Entities.Article;
import com.artatech.bilerman.Entities.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findAllByIsListedTrueAndIsPublicTrue(Pageable pageable);

    Page<Article> findAllByCreatedBy(Long userId, Pageable pageable);

    Page<Article> findAllByCreatedByAndIsPublicTrue(Long userId, Pageable pageable);

    Page<Article> findAllByTagsContainingAndIsListedTrueAndIsPublicTrue(Tag tag, Pageable pageable);

    void deleteById(Long id);
}
