package com.artatech.bilerman.Controllers;

import com.artatech.bilerman.Entities.Article;
import com.artatech.bilerman.Services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping
    public Iterable<Article> getArticles(){
        return articleService.findAll();
    }

    @GetMapping("/{id}")
    public Article getArticleById(@PathVariable("id") Long id){
        return articleService.findById(id).orElse(null);
    }

    @GetMapping("/user/{id}/drafts")
    public Collection<Article> getDraftsByUserId(@PathVariable("id") Long id){
        return articleService.fingAllByUser(id, false);
    }

    @GetMapping("/user/{id}/published")
    public Collection<Article> getPublishedByUserId(@PathVariable("id") Long id){
        return articleService.fingAllByUser(id, true);
    }

    @PostMapping
    public Article saveArticle(@RequestBody Article article){
        return articleService.save(article);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        articleService.delete(id);
    }

    @PostMapping("/{id}/image")
    public Long uploadImage(@RequestParam("upload") MultipartFile file, @PathVariable("id") Long id){
        return articleService.uploadImage(file, id);
    }

    @GetMapping("/{id}/image/{imageId}")
    public Resource getImage(@PathVariable("id") Long id, @PathVariable("imageId") Long imageId){
        return articleService.downloadImage(id, imageId);
    }

    @DeleteMapping("/{id}/image/{imageId}")
    public void deleteImage(@PathVariable("id") Long id, @PathVariable("imageId") Long imageId){
        articleService.delete(id, imageId);
    }
}
