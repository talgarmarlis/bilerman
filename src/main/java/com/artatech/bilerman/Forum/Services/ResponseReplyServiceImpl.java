package com.artatech.bilerman.Forum.Services;

import com.artatech.bilerman.Entities.Comment;
import com.artatech.bilerman.Forum.Entities.ResponseReply;
import com.artatech.bilerman.Exeptions.ResourceNotFoundException;
import com.artatech.bilerman.Forum.Entities.Question;
import com.artatech.bilerman.Forum.Repositories.QuestionRepository;
import com.artatech.bilerman.Forum.Repositories.ResponseReplyRepository;
import com.artatech.bilerman.Repositories.CommentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ResponseReplyServiceImpl implements ResponseReplyService {
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    ResponseReplyRepository responseReplyRepository;

    @Override
    public Collection<ResponseReply> findAll() {
        return responseReplyRepository.findAll();
    }

    @Override
    public ResponseReply findById(Long id) {
        return responseReplyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ResponseReply", "response_id", id));
    }

    @Override
    public ResponseReply save(ResponseReply responseReply) {
        ResponseReply saved;
        if(responseReply.getId() != null){
            saved = findById(responseReply.getId());
            BeanUtils.copyProperties(responseReply, saved, "id", "responseReplyId, createdBy");
            saved = responseReplyRepository.save(saved);
        }
        else saved = responseReplyRepository.save(responseReply);

        return saved;
    }

    @Override
    public void delete(ResponseReply responseReply) {
        responseReplyRepository.delete(responseReply);
    }

    @Override
    public void delete(Long responeReplyId) {
        responseReplyRepository.deleteById(responeReplyId);
    }
}
