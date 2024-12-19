package com.example.springboot.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface IFileService {

    // 通用的文件存储方法，根据文件类型决定存储路径
    String storeFile(MultipartFile file, String fileType,String fileName);

    // 获取文件存储目录
    Path getFileStorageLocation();

    Resource loadFileAsResource(String fileName, String fileType);
}
