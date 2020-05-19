package com.artatech.bilerman.Repositories;

import com.artatech.bilerman.Entities.Draft;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface DraftRepository extends JpaRepository<Draft, Long> {

    Collection<Draft> findAllByCreatedBy(Long userId);

    Page<Draft> findAllByCreatedBy(Long userId, Pageable pageable);

    Page<Draft> findAllByPublished(Boolean published, Pageable pageable);

    Page<Draft> findAllByCreatedByAndPublished(Long userId, Boolean published, Pageable pageable);

    void deleteById(Long id);
}
