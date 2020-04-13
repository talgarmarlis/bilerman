package com.artatech.bilerman.Repositories;

import com.artatech.bilerman.Entities.Draft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface DraftRepository extends JpaRepository<Draft, Long> {

    Collection<Draft> findAllByCreatedBy(Long userId);

    Collection<Draft> findAllByCreatedByAndPublished(Long userId, Boolean published);

    void deleteById(Long id);
}
