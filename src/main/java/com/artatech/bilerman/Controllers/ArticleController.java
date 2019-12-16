package com.artatech.bilerman.Controllers;

import com.artatech.bilerman.Entities.Article;
import com.artatech.bilerman.Services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public Article getArticleById(@PathVariable("id") Integer id){
        return articleService.findById(id).orElse(null);
    }


    @PostMapping("/{id}/image")
    public Integer updateImage(@RequestParam("file") MultipartFile file, @PathVariable("id") Integer id){
        return articleService.uploadImage(file, id);
    }

    @GetMapping("/{id}/image/{imageId}")
    public Resource getImage(@PathVariable("id") Integer id, @PathVariable("imageId") Integer imageId){
        return articleService.downloadImage(id, imageId);
    }
}
