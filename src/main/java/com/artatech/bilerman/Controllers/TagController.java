package com.artatech.bilerman.Controllers;

import com.artatech.bilerman.AccountManager.Security.CurrentUser;
import com.artatech.bilerman.AccountManager.Security.UserPrincipal;
import com.artatech.bilerman.AccountManager.Sevices.UserService;
import com.artatech.bilerman.Entities.Article;
import com.artatech.bilerman.Entities.Tag;
import com.artatech.bilerman.Models.ArticleDetails;
import com.artatech.bilerman.Models.ArticleModel;
import com.artatech.bilerman.Models.PublicationModel;
import com.artatech.bilerman.Services.ArticleService;
import com.artatech.bilerman.Services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @GetMapping
    public Collection<Tag> getTags(){
        return tagService.findAll();
    }

    @GetMapping("/{tagName}")
    public Tag getTagByName(@PathVariable("tagName") String tagName){
        return tagService.findByName(tagName);
    }

    @GetMapping("/familiar")
    public Collection<Tag> getFamiliarTags(@RequestParam(value = "tagName", required = true) String tagName,
                                           @RequestParam(value = "size", defaultValue = "5", required = false) Integer size){
        return tagService.getFamiliarTags(tagName, size);
    }
}
