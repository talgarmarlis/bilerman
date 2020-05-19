package com.artatech.bilerman.Services;

import com.artatech.bilerman.Entities.Draft;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public interface DraftService {

    Collection<Draft> findAll();

    List<Draft> findByIdIn(List<Long> draftIds);

    Page<Draft> findByPage(Long userId, Boolean published, String orderBy, String direction, Integer page, Integer size);

    Draft findById(Long draftId);

    Draft save(Draft draft);

    void delete(Draft draft);

    void delete(Long id);
}
