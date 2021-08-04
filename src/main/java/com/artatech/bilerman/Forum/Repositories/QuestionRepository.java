package com.artatech.bilerman.Forum.Repositories;

import com.artatech.bilerman.Entities.Article;
import com.artatech.bilerman.Entities.Tag;
import com.artatech.bilerman.Forum.Entities.Question;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    public Collection<Question> findAllByCreatedBy(Long userId);

    Page<TypePatternQuestions.Question> findAllByCreatedBy(Long userId, Pageable pageable);
}
