package org.softuni.traveltogether.service;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.softuni.traveltogether.services.CloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CloudinaryServiceTests {
    private static final String TEST_IMAGE_ID = "test/profile-default";

    @Autowired
    private CloudService cloudService;

    @Test
    public void testUploadImage_imageFile_expectNotNullImageUrl() throws IOException {
        InputStream fis = this.getClass().getResourceAsStream("/static/assets/profile-default.png");
        MultipartFile multipartFile = new MockMultipartFile("test", fis);

        String url = this.cloudService.uploadImage(multipartFile);

        TestCase.assertNotNull("Uploading images to cloud does not work", url);
    }

    @Test
    public void testUploadImage_imageFileWithId_expectNotNullImageUrl() throws IOException {
        InputStream fis = this.getClass().getResourceAsStream("/static/assets/profile-default.png");
        MultipartFile multipartFile = new MockMultipartFile("test", fis);

        String url = this.cloudService.uploadImage(multipartFile,  TEST_IMAGE_ID);

        TestCase.assertNotNull("Uploading images to cloud does not work", url);
    }

    @Test
    public void testDeleteImage_imageId_expectNotNullImageUrl() throws IOException {
        InputStream fis = this.getClass().getResourceAsStream("/static/assets/profile-default.png");
        MultipartFile multipartFile = new MockMultipartFile("test", fis);

        this.cloudService.uploadImage(multipartFile, TEST_IMAGE_ID);

        boolean success = this.cloudService.deleteImage(TEST_IMAGE_ID);

        TestCase.assertTrue("Deleting images to cloud does not work", success);
    }
}
