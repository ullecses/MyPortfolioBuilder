package com.example.myportfoliobuilder.services;

import com.example.myportfoliobuilder.models.Photo;
import com.example.myportfoliobuilder.models.User;
import com.example.myportfoliobuilder.repositories.PhotoRepository;
import com.example.myportfoliobuilder.repositories.UserRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class PhotoService {

    private final String photoDirectory = "src/main/resources/userPhotos/";

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Photo savePhoto(MultipartFile file, User user) throws IOException {
        // Создаем уникальное имя для файла
        /*String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
        Path path = Paths.get(photoDirectory, fileName);

        // Создаем директорию, если она не существует
        Files.createDirectories(path.getParent());

        // Сохраняем файл
        file.transferTo(path);

        // Создаем объект Photo и сохраняем в базу данных
        Photo photo = new Photo();
        photo.setFilePath(path.toString());
        photo.setUser(user);
        return photoRepository.save(photo);
        */

        Photo existingPhoto = photoRepository.findByUserId(user.getId());

        if (existingPhoto != null) {
            // Удаляем старое фото с диска
            Path oldPhotoPath = Paths.get(existingPhoto.getFilePath());
            Files.deleteIfExists(oldPhotoPath);

            // Удаляем старую запись в базе данных
            photoRepository.deleteByUserId(user.getId());
            System.out.println(" удалили");
            //photoRepository.delete(existingPhoto);
        }

        // Создаем уникальное имя для нового файла
        String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
        Path path = Paths.get(photoDirectory, fileName);

        // Создаем директорию, если она не существует
        Files.createDirectories(path.getParent());

        // Сохраняем новый файл
        file.transferTo(path);

        // Создаем новый объект Photo
        Photo newPhoto = new Photo();
        newPhoto.setFilePath(path.toString());
        newPhoto.setUser(user);

        return photoRepository.save(newPhoto);
    }

    public Resource getPhotosByUserId(Long userId) throws IOException {
        Photo photo = photoRepository.findByUserId(userId);

        if (photo == null) {
            throw new IllegalArgumentException("No photos found for user ID: " + userId);
        }

        Path filePath = Paths.get(photo.getFilePath());
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new IOException("File not found or not readable: " + filePath);
        }

        return resource;
    }
}
