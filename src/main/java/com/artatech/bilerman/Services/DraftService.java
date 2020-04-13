package com.artatech.bilerman.Services;

import com.artatech.bilerman.Entities.Draft;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public interface DraftService {

    Collection<Draft> findAll();

    Draft findById(Long draftId);

    List<Draft> findByIdIn(List<Long> draftIds);

    Draft save(Draft draft);

    void delete(Draft draft);

    void delete(Long id);

    Collection<Draft> fingAllByUser(Long userId);

    Collection<Draft> fingAllByUser(Long userId, Boolean published);
}
