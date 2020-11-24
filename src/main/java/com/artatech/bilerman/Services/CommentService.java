package com.artatech.bilerman.Services;

import com.artatech.bilerman.Entities.Comment;
import com.artatech.bilerman.Models.ArticleModel;
import com.artatech.bilerman.Models.CommentModel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public interface CommentService {

    Collection<Comment> findAll();

    Page<CommentModel> findByPage(Long articleId, String orderBy, String direction, Integer page, Integer size);

    Comment findById(Long id);

    List<Comment> findByIdIn(List<Long> commentIds);

    Comment save(Comment comment);

    void delete(Comment comment);

    void delete(Long commentId);
}
