package com.artatech.bilerman.Services;

import com.artatech.bilerman.Exeptions.AppException;
import com.artatech.bilerman.Exeptions.ResourceNotFoundException;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.UUID;

@Service
public class StorageService {

    @Value("${app.file.upload-dir}")
    private String dir;

    private final int WIDTH = 800;

    private final int MIN_SIZE = 100000;

    public String store(MultipartFile mFile, String location) {
        String imageformat = mFile.getOriginalFilename().substring(mFile.getOriginalFilename().lastIndexOf(".") + 1);
        try {
            File imageSourceFile = asFile(mFile);
            return storeFile(imageSourceFile, imageformat, location);
        }
        catch (IOException ex) {
            ex.printStackTrace();
            throw new AppException("Could not store the file. Please try again!", ex);
        }
    }

    public String store(String url, String location) {
        try {
            File imageSourceFile = asFile(url);
            return storeFile(imageSourceFile, "jpg", location);
        }
        catch (IOException ex) {
            ex.printStackTrace();
            throw new AppException("Could not store the file. Please try again!", ex);
        }

    }

    private String storeFile(File imageFile, String format, String location) {
        String imageFormat = format.toLowerCase(Locale.ROOT);
        String fileName = UUID.randomUUID() + "." + format.toLowerCase(Locale.ROOT);
        Path filePath = getPath(location).resolve(fileName);
        try {
            System.out.println(imageFile.length());
            if(imageFile.length() <= MIN_SIZE)
                Files.copy(new FileInputStream(imageFile), filePath, StandardCopyOption.REPLACE_EXISTING);
            else {
                BufferedImage sourceImage = ImageIO.read(imageFile);
                BufferedImage outputImage = resizeImage(sourceImage);
                ImageIO.write(outputImage, imageFormat, new File(filePath.toString()));
            }
            imageFile.delete();
            return fileName;
        } catch (IOException ex) {
            ex.printStackTrace();
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

    private BufferedImage resizeImage(BufferedImage originalImage) {
        return Scalr.resize(originalImage, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, WIDTH, Scalr.OP_ANTIALIAS);
    }
}
