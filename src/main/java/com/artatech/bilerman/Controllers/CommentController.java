package com.artatech.bilerman.Controllers;

import com.artatech.bilerman.Entities.Comment;
import com.artatech.bilerman.Models.CommentModel;
import com.artatech.bilerman.Services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public Page<CommentModel> getComments(@RequestParam(value = "articleId", required = true) Long articleId,
                                          @RequestParam(value = "orderBy", defaultValue = "createdAt", required = false) String orderBy,
                                          @RequestParam(value = "direction", defaultValue = "ASC", required = false) String direction,
                                          @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                          @RequestParam(value = "size", defaultValue = "10", required = false) Integer size){
        return commentService.findByPage(articleId, orderBy, direction, page, size);
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable("id") Long id){
        return commentService.findById(id);
    }

    @PostMapping
    public Comment saveComment(@RequestBody Comment comment) {
        return commentService.save(comment);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable("id") Long id){
        commentService.delete(id);
    }
}
