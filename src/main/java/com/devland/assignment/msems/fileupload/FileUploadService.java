package com.devland.assignment.msems.fileupload;

import com.devland.assignment.msems.applicationuser.ApplicationUserService;
import com.devland.assignment.msems.applicationuser.model.ApplicationUser;
import com.devland.assignment.msems.fileupload.exception.FileStorageException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileUploadService {
    private final ApplicationUserService applicationUserService;
    private final Path uploadPath = Paths.get("uploads");

    public FileUploadService(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            throw new FileStorageException("Could not create upload directory.");
        }
    }

    public String savedPhoto(MultipartFile file) {
        return storeFile(file);
    }

    public String changedPhoto(ApplicationUser user, MultipartFile file) {
        if (user.getProfilePicturePath() != null) {
            ApplicationUser existingUser = this.applicationUserService.findBy(user.getId());
            deletePhoto(existingUser.getProfilePicturePath());
        }
        return storeFile(file);
    }

    private String storeFile(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        try {
            assert originalFileName != null;
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String fileName = UUID.randomUUID() + fileExtension;

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException e) {
            throw new FileStorageException("Could not store file " + originalFileName + ", Please try again later.");
        }
    }

    public void deletePhoto(String fileName) {
        try {
            Path filePath = uploadPath.resolve(fileName);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new FileStorageException("Could not delete file " + fileName + ", Please try again later.");
        }
    }
}