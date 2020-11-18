package com.artatech.bilerman.Controllers;

import com.artatech.bilerman.AccountManager.Security.CurrentUser;
import com.artatech.bilerman.AccountManager.Security.UserPrincipal;
import com.artatech.bilerman.AccountManager.Sevices.UserService;
import com.artatech.bilerman.Entities.Article;
import com.artatech.bilerman.Models.ArticleDetails;
import com.artatech.bilerman.Models.ArticleModel;
import com.artatech.bilerman.Models.PublicationModel;
import com.artatech.bilerman.Services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

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

    @GetMapping("/saved")
    @PreAuthorize("hasRole('USER')")
    public Long[] getSavedArticles(@CurrentUser UserPrincipal currentUser){
        return userService.findById(currentUser.getId()).getSavedArticles().stream()
                .map(savedArticle -> savedArticle.getArticle().getId())
                .collect(Collectors.toList()).toArray(Long[]::new);
    }

    @PostMapping("/{id}/save")
    @PreAuthorize("hasRole('USER')")
    public Long[] saveArticle(@PathVariable("id") Long id, @CurrentUser UserPrincipal currentUser){
        Article articleToSave = articleService.findById(id);
        return userService.saveArticle(currentUser.getId(), articleToSave).getSavedArticles().stream()
                .map(savedArticle -> savedArticle.getArticle().getId())
                .collect(Collectors.toList()).toArray(Long[]::new);
    }

    @GetMapping("/clapped")
    @PreAuthorize("hasRole('USER')")
    public Long[] getClappedArticles(@CurrentUser UserPrincipal currentUser){
        return userService.findById(currentUser.getId()).getClaps().stream()
                .map(clap -> clap.getArticle().getId())
                .collect(Collectors.toList()).toArray(Long[]::new);
    }

    @PostMapping("/{id}/clap")
    @PreAuthorize("hasRole('USER')")
    public Long[] clapArticle(@PathVariable("id") Long id, @CurrentUser UserPrincipal currentUser){
        Article articleToClap = articleService.findById(id);
        return userService.clapArticle(currentUser.getId(), articleToClap).getClaps().stream()
                .map(clap -> clap.getArticle().getId())
                .collect(Collectors.toList()).toArray(Long[]::new);
    }
}
