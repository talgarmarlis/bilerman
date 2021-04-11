package com.artatech.bilerman.Services;

import com.artatech.bilerman.Entities.Image;
import com.artatech.bilerman.Enums.ImageCategory;
import com.artatech.bilerman.Exeptions.ResourceNotFoundException;
import com.artatech.bilerman.Repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.*;

@Service
public class ImageServiceImpl implements ImageService{

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    StorageService storageService;

    @Override
    public Collection<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public Image findById(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Image", "id", id));
    }

    @Override
    public List<Image> findByIdIn(List<Long> imageIds) {
        return imageRepository.findAllById(imageIds);
    }

    @Override
    public Image save(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public void delete(ImageCategory category, Image image) {
        storageService.delete(image.getName(), getImageLocation(category, image.getCreatedAt()));
        imageRepository.delete(image);
    }

    @Override
    public void delete(ImageCategory category, Long imageId) {
        delete(category, findById(imageId));
    }

    @Override
    public Long upload(ImageCategory category, MultipartFile file) {
        Image image = new Image();
        image.setName("default");
        image = save(image);
        String fileName =  storageService.store(file, getImageLocation(category, image.getCreatedAt()));
        image.setName(fileName);
        image = save(image);
        return image.getId();
    }

    @Override
    public Long upload(ImageCategory category, String url) {
        Image image = new Image();
        image.setName("default");
        image = save(image);
        String fileName =  storageService.store(url, getImageLocation(category, image.getCreatedAt()));
        image.setName(fileName);
        image = save(image);
        return image.getId();
    }

    @Override
    public Resource download(ImageCategory category, Long imageId) {
        Image image = findById(imageId);
        return storageService.load(image.getName(), getImageLocation(category, image.getCreatedAt()));
    }

    private String getImageLocation(ImageCategory category, Instant createdAt){
        String result = "/" + category.toString().toLowerCase();
        Calendar cal = Calendar.getInstance();
        cal.setTime(Date.from(createdAt));
        result += "/" + cal.get(Calendar.YEAR);
        result += "/" + cal.get(Calendar.MONTH);
        result += "/" + cal.get(Calendar.DAY_OF_MONTH);
        return result;
    }
}
