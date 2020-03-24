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

    Optional<Article> findById(Integer articleId);

    List<Article> findByIdIn(List<Integer> articleIds);

    Article save(Article article);

    void delete(Article article);

    Integer uploadImage(MultipartFile file, Integer articleId);

    Resource downloadImage(Integer articleId, Integer imageId);
}
