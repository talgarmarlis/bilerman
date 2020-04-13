package com.artatech.bilerman.Controllers;

import com.artatech.bilerman.Services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping()
    public Long uploadImage(@RequestParam("file") MultipartFile file){
        return imageService.upload(file);
    }

    @GetMapping("/{id}")
    public Resource downloadImage(@PathVariable("id") Long id){
        return imageService.download(id);
    }

}
