package com.example.myportfoliobuilder.services;

import com.example.myportfoliobuilder.models.Photo;
import com.example.myportfoliobuilder.models.User;
import com.example.myportfoliobuilder.repositories.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PhotoService {

    private final String photoDirectory = "src/main/resources/userPhotos/";

    @Autowired
    private PhotoRepository photoRepository;

    public Photo savePhoto(MultipartFile file, User user) throws IOException {
        // Создаем уникальное имя для файла
        String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
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
    }
}
