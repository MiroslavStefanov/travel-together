package org.softuni.traveltogether.services;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudService {
    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile multipartFile) throws IOException {
        return this.uploadImage(multipartFile, new HashMap());
    }

    @Override
    public String uploadImage(MultipartFile multipartFile, String imageId) throws IOException {
        return this.uploadImage(multipartFile, new HashMap<>(){{
            put("public_id", imageId);
        }});
    }

    @Override
    public boolean deleteImage(String imageId) throws IOException {
        Map<String, String> result = this.cloudinary.uploader().destroy(imageId, new HashMap<String, Object>(){{put("invalidate", true);}});
        return result.get("result").equals("ok");
    }

    private String uploadImage(MultipartFile multipartFile, Map map) throws IOException {
        File fileToUpload = File.createTempFile("temp-file", multipartFile.getOriginalFilename());
        multipartFile.transferTo(fileToUpload);

        return this.cloudinary
                .uploader()
                .upload(fileToUpload, map)
                .get("url")
                .toString();
    }
}
