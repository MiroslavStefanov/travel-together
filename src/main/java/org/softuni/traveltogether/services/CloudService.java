package org.softuni.traveltogether.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudService {
    String uploadImage(MultipartFile multipartFile) throws IOException;

    String uploadImage(MultipartFile multipartFile, String imageId) throws IOException;

    boolean deleteImage(String imageId) throws IOException;
}
