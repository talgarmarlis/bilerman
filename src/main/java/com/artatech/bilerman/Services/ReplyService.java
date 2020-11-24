package com.artatech.bilerman.Services;

import com.artatech.bilerman.Entities.Comment;
import com.artatech.bilerman.Entities.Reply;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public interface ReplyService {

    Collection<Reply> findAll();

    Page<Reply> findByPage(Long commentId, String orderBy, String direction, Integer page, Integer size);

    Reply findById(Long id);

    List<Reply> findByIdIn(List<Long> replyIds);

    Reply save(Reply reply);

    void delete(Reply reply);

    void delete(Long replyId);
}
