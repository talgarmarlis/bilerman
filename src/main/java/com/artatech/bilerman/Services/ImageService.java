package com.artatech.bilerman.Services;

import com.artatech.bilerman.Entities.Image;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public interface ImageService {

    Collection<Image> findAll();

    Optional<Image> findById(Integer id);

    List<Image> findByIdIn(List<Integer> imageIds);

    Image save(Image image);

    void delete(Image image);
}
