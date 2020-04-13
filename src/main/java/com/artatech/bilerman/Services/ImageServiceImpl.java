package com.artatech.bilerman.Services;

import com.artatech.bilerman.Entities.Image;
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
    public void delete(Image image) {
        storageService.delete(image.getName(), getImageLocation(image.getCreatedAt()));
        imageRepository.delete(image);
    }

    @Override
    public void delete(Long imageId) {
        delete(findById(imageId));
    }

    @Override
    public Long upload(MultipartFile file) {
        Image image = new Image();
        image.setName("default");
        image = save(image);
        String fileName =  storageService.store(file, getImageLocation(image.getCreatedAt()));
        image.setName(fileName);
        image = save(image);
        return image.getId();
    }

    @Override
    public Resource download(Long imageId) {
        Image image = findById(imageId);
        return storageService.load(image.getName(), getImageLocation(image.getCreatedAt()));
    }

    private String getImageLocation(Instant createdAt){
        String result = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(Date.from(createdAt));
        result += "/" + cal.get(Calendar.YEAR);
        result += "/" + cal.get(Calendar.MONTH);
        result += "/" + cal.get(Calendar.DAY_OF_MONTH);
        return result;
    }
}
