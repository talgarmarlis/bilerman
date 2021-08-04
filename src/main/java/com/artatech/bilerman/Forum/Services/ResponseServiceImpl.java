package com.artatech.bilerman.Forum.Services;

import com.artatech.bilerman.Entities.Comment;
import com.artatech.bilerman.Entities.Reply;
import com.artatech.bilerman.Exeptions.ResourceNotFoundException;
import com.artatech.bilerman.Forum.Repositories.ResponseRepository;
import com.artatech.bilerman.Repositories.CommentRepository;
import com.artatech.bilerman.Forum.Entities.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


@Service
public class ResponseServiceImpl implements ResponseService {
    @Autowired
    ResponseRepository responseRepository;

    @Override
    public Collection<Response> findAll(){return responseRepository.findAll();}

    @Override
    public Response findById(Long response_id) {
        return responseRepository.findById(response_id).orElseThrow(() -> new ResourceNotFoundException("Response", "response_id", response_id));
    }

    @Override
    public Response save(Response response) {
        Response saved;
        if(response.getId() != null){
            saved = findById(response.getId());
            BeanUtils.copyProperties(response, saved, "id", "response_id, createdBy");
            saved = responseRepository.save(saved);
        }
        else saved = responseRepository.save(response);
        return saved;
    }

    @Override
    public void delete(Response response) {
        responseRepository.delete(response);
    }

    @Override
    public void delete(Long response_id) {
        responseRepository.deleteById(response_id);
    }
}
