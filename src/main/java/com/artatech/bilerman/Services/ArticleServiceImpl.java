package com.artatech.bilerman.Services;

import com.artatech.bilerman.Entities.Article;
import com.artatech.bilerman.Entities.Draft;
import com.artatech.bilerman.Entities.Tag;
import com.artatech.bilerman.Enums.ImageCategory;
import com.artatech.bilerman.Exeptions.ResourceNotFoundException;
import com.artatech.bilerman.Models.ArticleDetails;
import com.artatech.bilerman.Models.ArticleModel;
import com.artatech.bilerman.Models.PublicationModel;
import com.artatech.bilerman.Repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

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
    public Page<ArticleModel> findByPage(Long userId, String title, String orderBy, String direction, Integer page, Integer size) {
        Page<Article> pagedArticles = findArticlesByPage(userId, title, orderBy, direction, page, size);
        Page<ArticleModel> pagedResult = pagedArticles.map(article -> {return new ArticleModel(article);});
        return pagedResult;
    }

    private Page<Article> findArticlesByPage(Long userId, String title, String orderBy, String direction, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, direction.equals("DESC") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
        if(userId == null && title == null) return articleRepository.findAll(pageable);
        else if(userId == null) return articleRepository.findAllByTitleContainingIgnoreCase(title, pageable);
        else if(title == null) return articleRepository.findAllByCreatedBy(userId, pageable);
        return articleRepository.findAllByCreatedByAndTitleContainingIgnoreCase(userId, title, pageable);
    }

    @Override
    public Page<ArticleModel> findByTagName(String tagName, String orderBy, String direction, Integer page, Integer size) {
        Tag tag = tagService.findByName(tagName);
        if(tag == null) return Page.empty();
        Pageable pageable = PageRequest.of(page, size, direction.equals("DESC") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
        return articleRepository.findAllByTagsContaining(tag, pageable).map(article -> {return new ArticleModel(article);});
    }

    @Override
    public Article findById(Long articleId) {
        return articleRepository.findById(articleId).orElseThrow(() -> new ResourceNotFoundException("article", "id", articleId));
    }

    @Override
    public ArticleDetails getArticleDetailsById(Long articleId) {
        Article article =  findById(articleId);
        article.setViews(article.getViews() + 1);
        return new ArticleDetails(save(article));
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
        Long deleteImageId = null;
        Draft draft = draftService.findById(model.getDraftId());
        Article article;
        if(draft.getArticle() != null) {
            article = draft.getArticle();
            article.setTitle(model.getTitle());
            article.setSubtitle(model.getSubtitle());
            article.setLanguageId(model.getLanguageId());
            article.setBody(draft.getBody());
            if(article.getImageId() != draft.getImageId()) deleteImageId = article.getImageId();
            article.setImageId(draft.getImageId());
        }
        else  {
            article = new Article(model.getTitle(), model.getSubtitle(), draft.getBody());
            article.setLanguageId(model.getLanguageId());
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
        if(deleteImageId != null) imageService.delete(ImageCategory.ARTICLE, deleteImageId);
        return saved;
    }
}
