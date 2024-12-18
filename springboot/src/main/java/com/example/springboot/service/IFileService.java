package com.example.springboot.service;

import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface IFileService {
    // 文件上传
    String storeFile(MultipartFile file);

    // 获取文件存储路径（可选，如果需要）
    Path getFileStorageLocation();
}

