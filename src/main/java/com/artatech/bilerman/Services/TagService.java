package com.artatech.bilerman.Services;

import com.artatech.bilerman.Entities.Tag;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

@Service
public interface TagService {

    Collection<Tag> findAll();

    Collection<Tag> getFamiliarTags(String tagName, Integer size);

    Tag findById(Long id);

    Tag findByName(String tagName);

    Boolean existsByName(String tagName);

    List<Tag> findByIdIn(List<Long> tagIds);

    Tag save(Tag tag);

    void delete(Tag tag);

    void delete(Long tagId);
}
