package com.artatech.bilerman.Services;

import com.artatech.bilerman.Entities.Article;
import com.artatech.bilerman.Exeptions.ResourceNotFoundException;
import com.artatech.bilerman.Repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ImageService imageService;

    @Override
    public Collection<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public Optional<Article> findById(Long articleId) {
        return articleRepository.findById(articleId);
    }

    @Override
    public List<Article> findByIdIn(List<Long> articleIds) {
        return articleRepository.findAllById(articleIds);
    }

    @Override
    public Article save(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public void delete(Article article) {

    }

    @Override
    public void delete(Long id) {
        articleRepository.deleteById(id);
    }

    @Override
    public Long uploadImage(MultipartFile file, Long articleId) {
        Article article = findById(articleId).orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
        return imageService.upload(file, articleId);
    }

    @Override
    public Resource downloadImage(Long articleId, Long imageId) {
        return imageService.download(imageId);
    }

    @Override
    public void delete(Long articleId, Long imageId) {
        imageService.delete(imageId);
    }

    @Override
    public Collection<Article> fingAllByUser(Long userId, Boolean published) {
        return articleRepository.findAllByCreatedByAndPublished(userId, published);
    }

    private String getImageLocation(Instant instant){
        return "";
    }
}
