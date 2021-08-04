package com.artatech.bilerman.Forum.Services;

import com.artatech.bilerman.Entities.Comment;
import com.artatech.bilerman.Forum.Entities.ResponseReply;
import com.artatech.bilerman.Models.CommentModel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public interface ResponseReplyService {
    Collection<ResponseReply> findAll();

    ResponseReply findById(Long id);

    ResponseReply save(ResponseReply responseReply);

    void delete(ResponseReply responseReply);

    void delete(Long responseReplyId);
}
