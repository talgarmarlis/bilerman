package com.artatech.bilerman.Controllers;

import com.artatech.bilerman.Entities.Article;
import com.artatech.bilerman.Models.ArticleDetails;
import com.artatech.bilerman.Models.ArticleModel;
import com.artatech.bilerman.Models.PublicationModel;
import com.artatech.bilerman.Services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping
    public Page<ArticleModel> getArticles(@RequestParam(value = "userId", required = false) Long userId,
                                          @RequestParam(value = "title", required = false) String title,
                                          @RequestParam(value = "orderBy", defaultValue = "createdAt", required = false) String orderBy,
                                          @RequestParam(value = "direction", defaultValue = "DESC", required = false) String direction,
                                          @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                          @RequestParam(value = "size", defaultValue = "10", required = false) Integer size){
        return articleService.findByPage(userId, title, orderBy, direction, page, size);
    }

    @GetMapping("/{id}")
    public ArticleDetails getArticleById(@PathVariable("id") Long id){
        return articleService.getArticleDetailsById(id);
    }

    @PostMapping("/publish")
    public Article publishDraft(@RequestBody PublicationModel model){
        return articleService.publishDraft(model);
    }
}
