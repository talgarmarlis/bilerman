package com.artatech.bilerman.Services;

import com.artatech.bilerman.Entities.Image;
import com.artatech.bilerman.Enums.ImageCategory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public interface ImageService {

    Collection<Image> findAll();

    Image findById(Long id);

    List<Image> findByIdIn(List<Long> imageIds);

    Image save(Image image);

    void delete(ImageCategory category, Image image);

    void delete(ImageCategory category, Long imageId);

    Long upload(ImageCategory category, MultipartFile file);

    Long upload(ImageCategory category, String url);

    Resource download(ImageCategory category, Long imageId);
}
