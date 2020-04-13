package com.artatech.bilerman.Controllers;

import com.artatech.bilerman.Entities.Article;
import com.artatech.bilerman.Models.PublicationModel;
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

    @PostMapping("/publish")
    public Article publishDraft(@RequestBody PublicationModel model){
        return articleService.publishDraft(model);
    }
}
