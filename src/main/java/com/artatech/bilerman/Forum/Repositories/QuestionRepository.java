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

    @Query("SELECT a FROM Question a WHERE (:userId is null or a.createdBy = :userId) " +
            "and (:title is null or lower(a.title) like concat('%', lower(:title), '%')) " +
            "and (:tagName is null or (select count(*) from a.tags t where t.name like concat('%', lower(:tagName), '%')) > 0)")
    Page<Question> findAllByIgnoringNulls(Long userId, String title, String tagName, Pageable pageable);
}
