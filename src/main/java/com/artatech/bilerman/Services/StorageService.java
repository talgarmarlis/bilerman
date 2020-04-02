package com.artatech.bilerman.Services;

import com.artatech.bilerman.Entities.Image;
import com.artatech.bilerman.Exeptions.AppException;
import com.artatech.bilerman.Exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class StorageService {

    @Value("${file.upload-dir}")
    private String dir;

    public String store(MultipartFile file, String location) {
        String fileName = UUID.randomUUID().toString() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        try {
            // Copy file to the target location (Replacing existing file with the same name)
            Path filePath = getPath(location).resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new AppException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource load(String fileName, String location) {
        try {
            Path filePath = getPath(location).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new ResourceNotFoundException("File", "fileName", fileName);
            }
        } catch (MalformedURLException ex) {
            throw new ResourceNotFoundException("File", "fileName", fileName);
        }
    }

    public void delete(String fileName, String location){
        Path filePath = getPath(location).resolve(fileName).normalize();
        File file = new File(filePath.toUri());
        if(file.exists()) file.delete();
    }

    private Path getPath(String location){
        Path path = Paths.get(dir  + location)
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(path);
        } catch (Exception ex) {
            throw new AppException("Could not create the directory where the uploaded files will be stored.", ex);
        }

        return path;
    }
}
