package com.example.springboot.service.impl;

import com.example.springboot.service.IFileService;
import com.example.springboot.utils.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

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
        String uploadDir = fileStorageProperties.getFileDir();
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

    // 根据文件类型选择对应的文件夹
    private String getSubFolder(String fileType) {
        switch (fileType) {
            case "Avatar":
                return "avatar";  // 上传到头像文件夹
            case "Picture":
                return "picture";  // 上传到图片文件夹
            case "Cover":
                return "cover";  // 上传到封面文件夹
            default:
                return "others";  // 默认文件夹
        }
    }

    // 通用的文件存储方法，可以根据文件类型决定存储路径
    @Override
    public String storeFile(MultipartFile file, String fileType, String customFileName) {
        // 根据fileType决定文件夹
        String subFolder = getSubFolder(fileType);

        // 获取文件的扩展名
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        // 如果customFileName没有扩展名，使用原文件的扩展名
        if (!customFileName.contains(".")) {
            customFileName += extension;  // 添加扩展名
        }

        Path targetLocation = fileStorageLocation.resolve(subFolder).resolve(customFileName);

        try {
            // 确保文件夹存在
            Files.createDirectories(targetLocation.getParent());

            // 将文件保存到指定路径
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return String.valueOf(targetLocation.getFileName());  // 返回文件的访问路径
        } catch (IOException ex) {
            throw new RuntimeException("存储文件时发生错误", ex);
        }
    }



    // 下载文件方法
    public Resource loadFileAsResource(String fileName, String fileType) {
        // 根据文件类型找到文件所在的子文件夹
        String subFolder = getSubFolder(fileType);
        Path filePath = fileStorageLocation.resolve(subFolder).resolve(fileName).normalize();
        try {
            System.out.print(String.valueOf(filePath));
            // 检查文件是否存在
            if (!Files.exists(filePath)) {
                throw new RuntimeException("文件未找到 " + fileName);
            }

            // 返回文件作为资源对象
            return new FileSystemResource(filePath);
        } catch (Exception ex) {
            throw new RuntimeException("无法读取文件 " + fileName, ex);
        }
    }

    // 生成唯一的文件名
    private String generateUniqueFileName(MultipartFile file) {
        return UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
    }

    @Override
    public Path getFileStorageLocation() {
        return fileStorageLocation;
    }
}
