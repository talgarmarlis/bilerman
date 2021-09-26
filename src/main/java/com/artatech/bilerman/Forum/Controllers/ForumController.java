package com.artatech.bilerman.Forum.Controllers;

import com.artatech.bilerman.AccountManager.Security.CurrentUser;
import com.artatech.bilerman.AccountManager.Security.UserPrincipal;
import com.artatech.bilerman.AccountManager.Sevices.UserService;
import com.artatech.bilerman.Entities.Article;
import com.artatech.bilerman.Forum.Entities.Question;
import com.artatech.bilerman.Forum.Services.QuestionService;
import com.artatech.bilerman.Forum.Services.ResponseReplyService;
import com.artatech.bilerman.Forum.Services.ResponseService;
import com.artatech.bilerman.Models.ArticleDetails;
import com.artatech.bilerman.Models.ArticleModel;
import com.artatech.bilerman.Models.PublicationModel;
import com.artatech.bilerman.Services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/forums")
public class ForumController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ResponseReplyService responseReplyService;

    @Autowired
    private ResponseService responseService;

    @Autowired
    private UserService userService;

    @GetMapping
    public Page<ArticleModel> getQuestions(@RequestParam(value = "userId", required = false) Long userId,
                                          @RequestParam(value = "title", required = false) String title,
                                          @RequestParam(value = "orderBy", defaultValue = "createdAt", required = false) String orderBy,
                                          @RequestParam(value = "direction", defaultValue = "DESC", required = false) String direction,
                                          @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                          @RequestParam(value = "size", defaultValue = "10", required = false) Integer size){
        return questionService.findByPage(userId, title, orderBy, direction, page, size);
    }

    @PostMapping("/publish")
    @PreAuthorize("hasRole('USER')")
    public Question save(@RequestBody Question question){
        return questionService.save(question);
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
