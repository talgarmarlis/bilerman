package com.artatech.bilerman.Services;

import com.artatech.bilerman.Entities.Article;
import com.artatech.bilerman.Entities.Image;
import com.artatech.bilerman.Exeptions.ResourceNotFoundException;
import com.artatech.bilerman.Repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    StorageService storageService;

    @Autowired
    ImageService imageService;

    @Override
    public Collection<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public Optional<Article> findById(Integer articleId) {
        return articleRepository.findById(articleId);
    }

    @Override
    public List<Article> findByIdIn(List<Integer> articleIds) {
        return articleRepository.findAllById(articleIds);
    }

    @Override
    public void save(Article article) {

    }

    @Override
    public void delete(Article article) {

    }

    @Override
    public Integer uploadImage(MultipartFile file, Integer articleId) {
        Article article = findById(articleId).orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
        Image image = new Image();
        image.setArticleId(articleId);
        image.setName("default.png");
        image = imageService.save(image);

        String fileName =  storageService.store(file, getImageLocation(image.getCreatedAt()));
        image.setName(fileName);
        image = imageService.save(image);
        return image.getId();


    }

    @Override
    public Resource downloadImage(Integer articleId, Integer imageId) {
        Image image = imageService.findById(imageId).orElseThrow(() -> new ResourceNotFoundException("Image", "id", imageId));
        return storageService.load(image.getName(), getImageLocation(image.getCreatedAt()));
    }

    private String getImageLocation(Instant instant){
        return "";
    }
}
