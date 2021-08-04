package com.artatech.bilerman.Forum.Services;
import com.artatech.bilerman.Forum.Entities.Question;
import com.artatech.bilerman.Models.PublicationModel;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.List;
import java.util.List;

@Service
public interface QuestionService {

    Collection<Question> findAll();

    Question findById(Long question_id);

    Question save(Question question);

    void delete(Question question);
}
