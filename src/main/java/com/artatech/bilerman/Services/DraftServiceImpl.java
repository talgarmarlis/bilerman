package com.artatech.bilerman.Services;

import com.artatech.bilerman.Entities.Article;
import com.artatech.bilerman.Entities.Draft;
import com.artatech.bilerman.Exeptions.ResourceNotFoundException;
import com.artatech.bilerman.Repositories.DraftRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class DraftServiceImpl implements DraftService {

    @Autowired
    DraftRepository draftRepository;

    @Autowired
    ImageService imageService;

    @Override
    public Collection<Draft> findAll() {
        return draftRepository.findAll();
    }

    @Override
    public Draft findById(Long draftId) {
        return draftRepository.findById(draftId).orElseThrow(() -> new ResourceNotFoundException("'Draft'", "id", draftId));
    }

    @Override
    public List<Draft> findByIdIn(List<Long> draftIds) {
        return draftRepository.findAllById(draftIds);
    }

    @Override
    public Draft save(Draft draft) {
        if(draft.getPublished() == null) draft.setPublished(false);
        Long deleteImageId = null;
        if(draft.getId() != null) {
            Draft oldDraft = findById(draft.getId());
            if(oldDraft.getImageId() != null && oldDraft.getImageId() != draft.getImageId()){
                if(!oldDraft.getPublished() ||
                        (oldDraft.getArticle().getImageId() != null &&
                                oldDraft.getArticle().getImageId() != oldDraft.getImageId()));
                    deleteImageId = oldDraft.getImageId();
            }
        }
        Draft saved;
        if(draft.getId() != null){
            saved = findById(draft.getId());
            BeanUtils.copyProperties(draft, saved, "id", "article");
            saved = draftRepository.save(saved);
        }
        else saved = draftRepository.save(draft);
        if(deleteImageId != null) imageService.delete(deleteImageId);
        return saved;
    }

    @Override
    public void delete(Draft draft) {
        if(draft != null) imageService.delete(draft.getImageId());
        draftRepository.delete(draft);
    }

    @Override
    public void delete(Long id) {
        draftRepository.delete(findById(id));
    }

    @Override
    public Collection<Draft> fingAllByUser(Long userId) {
        return draftRepository.findAllByCreatedBy(userId);
    }

    @Override
    public Collection<Draft> fingAllByUser(Long userId, Boolean published) {
        return draftRepository.findAllByCreatedByAndPublished(userId, published);
    }
}
