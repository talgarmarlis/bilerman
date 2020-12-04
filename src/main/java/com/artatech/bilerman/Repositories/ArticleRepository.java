package com.artatech.bilerman.Repositories;

import com.artatech.bilerman.Entities.Article;
import com.artatech.bilerman.Entities.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Collection<Article> findAllByCreatedBy(Long userId);

    Page<Article> findAllByCreatedBy(Long userId, Pageable pageable);

    Page<Article> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Article> findAllByCreatedByAndTitleContainingIgnoreCase(Long userId, String title, Pageable pageable);

    @Query("SELECT a FROM Article a WHERE (:userId is null or a.createdBy = :userId) " +
            "and (:title is null or lower(a.title) like concat('%', lower(:title), '%')) " +
            "and (:tagName is null or (select count(*) from a.tags t where t.name like concat('%', lower(:tagName), '%')) > 0)")
    Page<Article> findAllByIgnoringNulls(Long userId, String title, String tagName, Pageable pageable);

    Page<Article> findAllByTagsContaining(Tag tag, Pageable pageable);

    void deleteById(Long id);
}
