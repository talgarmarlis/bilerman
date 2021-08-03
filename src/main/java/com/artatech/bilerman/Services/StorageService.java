package com.artatech.bilerman.Services;

import com.artatech.bilerman.Exeptions.AppException;
import com.artatech.bilerman.Exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class StorageService {

    @Value("${app.file.upload-dir}")
    private String dir;

    public String store(MultipartFile mFile, String location) {
        String fileName = UUID.randomUUID().toString() + mFile.getOriginalFilename().substring(mFile.getOriginalFilename().lastIndexOf("."));
        try {
            // Copy file to the target location (Replacing existing file with the same name)
            File imageSourceFile = asFile(mFile);
            Path filePath = getPath(location).resolve(fileName);
            File compressedImageFile = filePath.toFile();
            new ImageCompressor(imageSourceFile).compressTo(compressedImageFile);
            imageSourceFile.delete();
            return fileName;
        } catch (IOException ex) {
            throw new AppException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public String store(String url, String location) {
        String fileName = UUID.randomUUID().toString() + ".jpg";
        try {
            File imageSourceFile = asFile(url);
            Path filePath = getPath(location).resolve(fileName);
            File compressedImageFile = filePath.toFile();
            new ImageCompressor(imageSourceFile).compressTo(compressedImageFile);
            imageSourceFile.delete();
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

    private File asFile(MultipartFile multipartFile) throws IOException {
        String tempFileName = UUID.randomUUID().toString();
        File imageFile = Files.createTempFile(tempFileName, ".jpg").toFile();
        multipartFile.transferTo(imageFile);
        return imageFile;
    }

    private File asFile(String urlAsString) throws IOException {
        URL url;
        try {
            url = new URL(urlAsString);
            url.toURI();
        } catch (MalformedURLException| URISyntaxException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        String tempFileName = UUID.randomUUID().toString();
        File imageFile = Files.createTempFile(tempFileName, ".jpg").toFile();
        FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
        ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
        fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        return imageFile;
    }
}
