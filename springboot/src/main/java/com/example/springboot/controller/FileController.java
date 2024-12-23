package com.example.springboot.controller;

import com.example.springboot.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/File")
public class FileController {

    @Autowired
    IFileService fileService;

    // 上传文件的接口
    @PostMapping("/Upload/{fileType}/{fileName}")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("fileType") String fileType, @PathVariable("fileName") String fileName) {
        try {
            // 使用 FileService 存储文件
            String filePath = fileService.storeFile(file, fileType,fileName);
            // 使用 ResponseEntity 返回文件路径
            return ResponseEntity.ok("上传成功");  // 返回文件路径作为响应体
        } catch (Exception e) {
            // 文件上传失败时，返回 500 错误和错误信息
            return ResponseEntity.status(500).body("上传文件失败: " + e.getMessage());
        }
    }



    // 文件下载接口
    @GetMapping("/Download/{fileType}/{fileName:.+}")
    public ResponseEntity<Resource> downloadAvatarFile(@PathVariable("fileName") String fileName,@PathVariable("fileType") String fileType) {
        // 加载文件
        Resource resource = fileService.loadFileAsResource(fileName, fileType);

        // 获取原始文件名
        String fileNameWithExtension = StringUtils.cleanPath(fileName);

        try {
            // 返回文件作为下载
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileNameWithExtension + "\"")
                    .body(resource);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
