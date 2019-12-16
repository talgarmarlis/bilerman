package com.artatech.bilerman.Services;

import com.artatech.bilerman.Entities.Image;
import com.artatech.bilerman.Repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService{

    @Autowired
    ImageRepository imageRepository;

    @Override
    public Collection<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public Optional<Image> findById(Integer id) {
        return imageRepository.findById(id);
    }

    @Override
    public List<Image> findByIdIn(List<Integer> imageIds) {
        return imageRepository.findAllById(imageIds);
    }

    @Override
    public Image save(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public void delete(Image image) {
//        imageRepository.delete(image);
    }
}
