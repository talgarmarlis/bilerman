package com.artatech.bilerman.Controllers;

import com.artatech.bilerman.Enums.ImageCategory;
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

    @PostMapping("/{category}")
    public Long uploadImage(@PathVariable("category") String category, @RequestParam("file") MultipartFile file){
        return imageService.upload(ImageCategory.valueOf(category.toUpperCase()), file);
    }

    @GetMapping("/{category}/{id}")
    public Resource downloadImage(@PathVariable("category") String category, @PathVariable("id") Long id){
        return imageService.download(ImageCategory.valueOf(category.toUpperCase()), id);
    }

}
