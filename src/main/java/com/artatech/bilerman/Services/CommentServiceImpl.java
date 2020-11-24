package com.artatech.bilerman.Services;

import com.artatech.bilerman.Entities.Article;
import com.artatech.bilerman.Entities.Comment;
import com.artatech.bilerman.Exeptions.ResourceNotFoundException;
import com.artatech.bilerman.Models.ArticleModel;
import com.artatech.bilerman.Models.CommentModel;
import com.artatech.bilerman.Repositories.CommentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public Collection<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Page<CommentModel> findByPage(Long articleId, String orderBy, String direction, Integer page, Integer size) {
        Page<Comment> pagedComments = findCommentsByPage(articleId, orderBy, direction, page, size);
        Page<CommentModel> pagedResult = pagedComments.map(comment -> {return new CommentModel(comment);});
        return pagedResult;
    }

    private Page<Comment> findCommentsByPage(Long articleId, String orderBy, String direction, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, direction.equals("DESC") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
        return commentRepository.findAllByArticleId(articleId, pageable);
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", id));
    }

    @Override
    public List<Comment> findByIdIn(List<Long> commentIds) {
        return commentRepository.findAllById(commentIds);
    }

    @Override
    public Comment save(Comment comment) {
        Comment saved;
        if(comment.getId() != null){
            saved = findById(comment.getId());
            BeanUtils.copyProperties(comment, saved, "id", "articleId, createdBy");
            saved = commentRepository.save(saved);
        }
        else saved = commentRepository.save(comment);
        return saved;
    }

    @Override
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public void delete(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
