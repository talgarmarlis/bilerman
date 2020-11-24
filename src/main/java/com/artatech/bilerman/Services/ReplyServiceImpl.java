package com.artatech.bilerman.Services;

import com.artatech.bilerman.Entities.Comment;
import com.artatech.bilerman.Entities.Reply;
import com.artatech.bilerman.Exeptions.ResourceNotFoundException;
import com.artatech.bilerman.Repositories.ReplyRepository;
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
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    ReplyRepository replyRepository;

    @Override
    public Collection<Reply> findAll() {
        return replyRepository.findAll();
    }

    @Override
    public Page<Reply> findByPage(Long commentId, String orderBy, String direction, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, direction.equals("DESC") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
        return replyRepository.findAllByCommentId(commentId, pageable);
    }

    @Override
    public Reply findById(Long id) {
        return replyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reply", "replyId", id));
    }

    @Override
    public List<Reply> findByIdIn(List<Long> replyIds) {
        return replyRepository.findAllById(replyIds);
    }

    @Override
    public Reply save(Reply reply) {
        Reply saved;
        if(reply.getId() != null){
            saved = findById(reply.getId());
            BeanUtils.copyProperties(reply, saved, "id", "commentId, createdBy");
            saved = replyRepository.save(saved);
        }
        else saved = replyRepository.save(reply);
        return saved;
    }

    @Override
    public void delete(Reply reply) {
        replyRepository.delete(reply);
    }

    @Override
    public void delete(Long replyId) {
        replyRepository.deleteById(replyId);
    }
}
