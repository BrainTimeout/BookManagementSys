package com.example.springboot.service.impl;

import com.example.springboot.service.IFileService;
import com.example.springboot.utils.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService implements IFileService {

    private Path fileStorageLocation;

    @Autowired
    private FileStorageProperties fileStorageProperties;

    public FileService() {
        // 构造函数不再做文件存储初始化
    }

    @PostConstruct
    public void init() {
        // 获取上传目录路径，如果没有配置则使用默认路径
        String uploadDir = fileStorageProperties.getUploadDir();
        if (uploadDir == null || uploadDir.isEmpty()) {
            uploadDir = "uploads";  // 默认上传目录
        }

        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);  // 确保文件夹存在
        } catch (IOException ex) {
            throw new RuntimeException("无法创建文件存储目录", ex);
        }
    }

    @Override
    public String storeFile(MultipartFile file) {
        // 获取文件名
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        // 计算文件的目标路径
        Path targetLocation = fileStorageLocation.resolve(fileName);
        try {
            // 将文件保存到指定路径
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return "/uploads/" + fileName;  // 返回文件的访问路径
        } catch (IOException ex) {
            throw new RuntimeException("存储文件时发生错误", ex);
        }
    }

    @Override
    public Path getFileStorageLocation() {
        return fileStorageLocation;
    }
}
