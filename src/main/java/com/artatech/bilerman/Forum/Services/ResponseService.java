package com.artatech.bilerman.Forum.Services;

import com.artatech.bilerman.Entities.Reply;
import com.artatech.bilerman.Forum.Entities.Response;
import com.artatech.bilerman.Models.ArticleModel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public interface ResponseService {
    Collection<Response> findAll();

    Response findById(Long id);


    Response save(Response response);

    void delete(Response response);

    void delete(Long resonseId);
}
