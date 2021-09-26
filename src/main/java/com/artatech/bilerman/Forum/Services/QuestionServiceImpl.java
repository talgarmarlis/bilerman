package com.artatech.bilerman.Forum.Services;

import com.artatech.bilerman.Entities.Article;
import com.artatech.bilerman.Entities.Draft;
import com.artatech.bilerman.Entities.Tag;
import com.artatech.bilerman.Enums.ImageCategory;
import com.artatech.bilerman.Exeptions.ResourceNotFoundException;
import com.artatech.bilerman.Forum.Models.ForumQuestionModel;
import com.artatech.bilerman.Models.ArticleModel;
import com.artatech.bilerman.Models.PublicationModel;
import com.artatech.bilerman.Repositories.ArticleRepository;
import com.artatech.bilerman.Services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import com.artatech.bilerman.Forum.Entities.Question;
import com.artatech.bilerman.Forum.Repositories.QuestionRepository;
import java.util.*;
import java.util.Collection;
import java.util.HashSet;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    TagService tagService;

    @Override
    public Collection<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public Page<ArticleModel> findByPage(Long userId, String title, String orderBy, String direction, Integer page,
            Integer size) {
        return null;
    }

    @Override
    public Question findById(Long question_id) {
        return questionRepository.findById(question_id)
                .orElseThrow(() -> new ResourceNotFoundException("question", "id", question_id));
    }

    @Override
    public Question save(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public Question publishDraft(ForumQuestionModel model) {
        Question question;
        question = new Question(model.getTitle(), model.getDescription());
        question.setLanguageId(model.getLanguageId());

        question.setTags(new HashSet<>());

        for (int i = 0; i < model.getTags().length; i++) {
            Tag tag;
            if (tagService.existsByName(model.getTags()[i]))
                tag = tagService.findByName(model.getTags()[i]);
            else
                tag = new Tag(model.getTags()[i]);

            question.getTags().add(tag);
            tag.getQuestions().add(question);
        }

        Question saved = save(question);

        return saved;
    }

    @Override
    public void delete(Question question) {
        questionRepository.delete(question);
    }
}
