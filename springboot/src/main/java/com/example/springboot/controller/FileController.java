package com.example.springboot.controller;

import com.example.springboot.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/File")
public class FileController {

    @Autowired
    IFileService fileService;

    // 上传文件的接口
    @PostMapping("/Avatar")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // 使用 FileService 存储文件
            String filePath = fileService.storeFile(file);
            return ResponseEntity.ok("文件上传成功，存储路径：" + filePath);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("上传文件失败: " + e.getMessage());
        }
    }
}
