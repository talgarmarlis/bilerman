package com.artatech.bilerman.Services;

import com.artatech.bilerman.Entities.Article;
import com.artatech.bilerman.Models.ArticleDetails;
import com.artatech.bilerman.Models.ArticleModel;
import com.artatech.bilerman.Models.PublicationModel;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public interface ArticleService {

    Collection<Article> findAll();

    Page<ArticleModel> findByPage(Long userId, String title, String orderBy, String direction, Integer page, Integer size);

    Article findById(Long articleId);

    ArticleDetails getArticleDetailsById(Long articleId);

    List<Article> findByIdIn(List<Long> articleIds);

    Article save(Article article);

    void delete(Article article);

    void delete(Long id);

    Collection<Article> fingAllByUser(Long userId);

    Article publishDraft(PublicationModel model);
}
