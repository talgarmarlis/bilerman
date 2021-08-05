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
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @GetMapping
    public Page<ArticleModel> getArticles(@RequestParam(value = "orderBy", defaultValue = "createdAt", required = false) String orderBy,
                                          @RequestParam(value = "direction", defaultValue = "DESC", required = false) String direction,
                                          @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                          @RequestParam(value = "size", defaultValue = "10", required = false) Integer size){
        return articleService.findAll(orderBy, direction, page, size);
    }

    @GetMapping("/users/{id}")
    public Page<ArticleModel> getArticles(@PathVariable("id") Long userId, @CurrentUser UserPrincipal currentUser,
                                          @RequestParam(value = "orderBy", defaultValue = "createdAt", required = false) String orderBy,
                                          @RequestParam(value = "direction", defaultValue = "DESC", required = false) String direction,
                                          @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                          @RequestParam(value = "size", defaultValue = "10", required = false) Integer size){
        if(currentUser == null ) return articleService.findAllByUser(userId, null, orderBy, direction, page, size);
        return articleService.findAllByUser(userId, currentUser.getId(), orderBy, direction, page, size);
    }

    @GetMapping("/tags")
    public Page<ArticleModel> getArticles(@RequestParam(value = "tagName", required = false) String tagName,
                                          @RequestParam(value = "orderBy", defaultValue = "createdAt", required = false) String orderBy,
                                          @RequestParam(value = "direction", defaultValue = "DESC", required = false) String direction,
                                          @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                          @RequestParam(value = "size", defaultValue = "10", required = false) Integer size){
        return articleService.findByTagName(tagName, orderBy, direction, page, size);
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
    public Page<ArticleModel> getSavedArticles(@CurrentUser UserPrincipal currentUser,
                                               @RequestParam(value = "orderBy", defaultValue = "createdAt", required = false) String orderBy,
                                               @RequestParam(value = "direction", defaultValue = "DESC", required = false) String direction,
                                               @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                               @RequestParam(value = "size", defaultValue = "10", required = false) Integer size){
        Pageable pageable = PageRequest.of(page, size, direction.equals("DESC") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
        List<ArticleModel> list = userService.findById(currentUser.getId()).getSavedArticles().stream()
                                    .map(saved -> { return new ArticleModel(saved.getArticle());}).collect(Collectors.toList());
        return toPage(list, pageable);
    }

    @GetMapping("/saved/ids")
    @PreAuthorize("hasRole('USER')")
    public Long[] getSavedArticleIds(@CurrentUser UserPrincipal currentUser){
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
    public Page<ArticleModel> getClappedArticles(@CurrentUser UserPrincipal currentUser,
                                               @RequestParam(value = "orderBy", defaultValue = "createdAt", required = false) String orderBy,
                                               @RequestParam(value = "direction", defaultValue = "DESC", required = false) String direction,
                                               @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                               @RequestParam(value = "size", defaultValue = "10", required = false) Integer size){
        Pageable pageable = PageRequest.of(page, size, direction.equals("DESC") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
        List<ArticleModel> list = userService.findById(currentUser.getId()).getClaps().stream()
                .map(saved -> { return new ArticleModel(saved.getArticle());}).collect(Collectors.toList());
        return toPage(list, pageable);
    }

    @GetMapping("/clapped/ids")
    @PreAuthorize("hasRole('USER')")
    public Long[] getClappedArticleIds(@CurrentUser UserPrincipal currentUser){
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

    private Page toPage(List list, Pageable pageable) {
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int)pageable.getOffset();
        int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ?
                list.size() :
                pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }
}
