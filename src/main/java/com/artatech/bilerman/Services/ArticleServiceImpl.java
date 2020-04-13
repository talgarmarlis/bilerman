package com.artatech.bilerman.Services;

import com.artatech.bilerman.Entities.Article;
import com.artatech.bilerman.Entities.Draft;
import com.artatech.bilerman.Entities.Tag;
import com.artatech.bilerman.Exeptions.ResourceNotFoundException;
import com.artatech.bilerman.Models.PublicationModel;
import com.artatech.bilerman.Repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ImageService imageService;

    @Autowired
    DraftService draftService;

    @Autowired
    TagService tagService;

    @Override
    public Collection<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public Optional<Article> findById(Long articleId) {
        return articleRepository.findById(articleId);
    }

    @Override
    public List<Article> findByIdIn(List<Long> articleIds) {
        return articleRepository.findAllById(articleIds);
    }

    @Override
    public Article save(Article article) {
        if(article.getViews() == null) article.setViews(0);
        return articleRepository.save(article);
    }

    @Override
    public void delete(Article article) {
        articleRepository.delete(article);
    }

    @Override
    public void delete(Long id) {
        articleRepository.deleteById(id);
    }

    @Override
    public Collection<Article> fingAllByUser(Long userId) {
        return articleRepository.findAllByCreatedBy(userId);
    }

    @Override
    public Article publishDraft(PublicationModel model) {
        Draft draft = draftService.findById(model.getDraftId());
        Article article;
        if(draft.getArticle() != null) {
            article = draft.getArticle();
            article.setTitle(model.getTitle());
            article.setSubtitle(model.getSubtitle());
            article.setBody(draft.getBody());
            article.setImageId(draft.getImageId());
        }
        else  {
            article = new Article(model.getTitle(), model.getSubtitle(), draft.getBody());
            article.setImageId(draft.getImageId());
            article.setDraft(draft);
            draft.setArticle(article);
        }

        article.setTags(new HashSet<>());

        for(int i = 0; i < model.getTags().length; i++){
            Tag tag;
            if(tagService.existsByName(model.getTags()[i])) tag = tagService.findByName(model.getTags()[i]);
            else tag = new Tag(model.getTags()[i]);

            article.getTags().add(tag);
            tag.getArticles().add(article);
        }

        Article saved = save(article);
        draft.setPublished(true);
        draftService.save(draft);
        return saved;
    }
}
