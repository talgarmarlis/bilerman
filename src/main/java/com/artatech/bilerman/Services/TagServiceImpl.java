package com.artatech.bilerman.Services;

import com.artatech.bilerman.Entities.Tag;
import com.artatech.bilerman.Exeptions.ResourceNotFoundException;
import com.artatech.bilerman.Repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class TagServiceImpl implements TagService{

    @Autowired
    TagRepository tagRepository;

    @Autowired
    StorageService storageService;

    @Override
    public Collection<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Tag findById(Long id) {
        return tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag", "id", id));
    }

    @Override
    public Tag findByName(String tagName) {
        return tagRepository.findByName(tagName.toLowerCase());
    }

    @Override
    public Boolean existsByName(String tagName) {
        return tagRepository.existsByName(tagName.toLowerCase());
    }

    @Override
    public List<Tag> findByIdIn(List<Long> tagIds) {
        return tagRepository.findAllById(tagIds);
    }

    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public void delete(Tag tag) {
        tagRepository.delete(tag);
    }

    @Override
    public void delete(Long tagId) {
        delete(findById(tagId));
    }
}
