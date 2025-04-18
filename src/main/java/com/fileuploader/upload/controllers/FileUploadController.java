package com.fileuploader.upload.controllers;


import com.fileuploader.upload.dataclasses.FileMetaData;
import com.fileuploader.upload.services.FileService;
import com.fileuploader.upload.utils.BlobStoreHandler;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/file")
public class FileUploadController {
    private final BlobStoreHandler minioblob;
    private final FileService fileService;
    private final Dotenv dotenv;
    public FileUploadController(BlobStoreHandler minioblob, FileService service, Dotenv dotenv) {
        this.minioblob = minioblob;
        this.fileService = service;
        this.dotenv = dotenv;
    }

    @PostMapping("uploadFile")
    public FileMetaData uploadFile(
            @RequestHeader("filename") String filename,
            @RequestHeader("file-extension") String extension,
            HttpServletRequest request
    ){
        try {
            String users_name = SecurityContextHolder.getContext().getAuthentication().getName();
            return fileService.uploadFile(request.getInputStream(),filename,extension,users_name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/files/{email}")
    public List<FileMetaData> getFilesForUser(@PathVariable String email){
        String users_name = SecurityContextHolder.getContext().getAuthentication().getName();
        return fileService.getFilesForUser(users_name);
    }
    @GetMapping("/files/{id}")
    public void downloadFile(@PathVariable UUID id, HttpServletResponse response){
        fileService.downloadFile(response,id);
    }
    @GetMapping("/files/{id}")
    public FileMetaData getFileMetaData(@PathVariable UUID id){
        return fileService.getFileMetaData(id);
    }
}
