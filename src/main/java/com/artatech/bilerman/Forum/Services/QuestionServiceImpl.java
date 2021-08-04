package com.artatech.bilerman.Forum.Services;
import com.artatech.bilerman.Entities.Article;
import com.artatech.bilerman.Entities.Draft;
import com.artatech.bilerman.Entities.Tag;
import com.artatech.bilerman.Enums.ImageCategory;
import com.artatech.bilerman.Exeptions.ResourceNotFoundException;
import com.artatech.bilerman.Models.ArticleModel;
import com.artatech.bilerman.Models.PublicationModel;
import com.artatech.bilerman.Repositories.ArticleRepository;
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

    @Override
    public Collection<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public Page<ArticleModel> findByPage(Long userId, String title, String orderBy, String direction, Integer page, Integer size) {
        return null;
    }

    @Override
    public Question findById(Long question_id) {
        return questionRepository.findById(question_id).orElseThrow(() -> new ResourceNotFoundException("question", "id", question_id));
    }

    @Override
    public Question save(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public void delete(Question question) {
        questionRepository.delete(question);
    }
}
