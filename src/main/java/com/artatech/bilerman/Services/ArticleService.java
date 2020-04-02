package com.artatech.bilerman.Services;

import com.artatech.bilerman.Entities.Article;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public interface ArticleService {

    Collection<Article> findAll();

    Optional<Article> findById(Long articleId);

    List<Article> findByIdIn(List<Long> articleIds);

    Article save(Article article);

    void delete(Article article);

    void delete(Long id);

    Long uploadImage(MultipartFile file, Long articleId);

    Resource downloadImage(Long articleId, Long imageId);

    void delete(Long articleId, Long imageId);

    Collection<Article> fingAllByUser(Long userId, Boolean published);
}
