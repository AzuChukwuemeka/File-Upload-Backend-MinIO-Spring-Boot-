package com.fileuploader.upload.services;

import com.fileuploader.upload.dataclasses.FileMetaData;
import com.fileuploader.upload.repositories.FileRepositoryI;
import com.fileuploader.upload.utils.BlobStoreHandler;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {
    private final FileRepositoryI fileRepository;
    private final Dotenv dotenv;
    private final BlobStoreHandler minioblob;
    public FileService(FileRepositoryI fileRepository, Dotenv dotenv, BlobStoreHandler minioblob) {
        this.fileRepository = fileRepository;
        this.dotenv = dotenv;
        this.minioblob = minioblob;
    }
    public FileMetaData uploadFile(InputStream inputStream, String filename, String extension, String uploaded_by){
        UUID file_id = UUID.randomUUID();
        try(inputStream){
            String file_name = filename.concat(file_id.toString()).concat(extension);
            minioblob.uploadFile(dotenv.get("MY_BUCKET_NAME"), file_name, inputStream);
            return fileRepository.createFileMetaData(file_id, filename, file_name, extension, uploaded_by);
        }catch (Exception e) {
            fileRepository.deleteFileById(file_id);
            throw new RuntimeException(e);
        }
    }
    public List<FileMetaData> getFilesForUser(String email){
        return fileRepository.getFilesOwnedByUser(email);
    }
    public FileMetaData getFileMetaData(UUID id){
        return fileRepository.getFileMetaData(id);
    }

    public void downloadFile(HttpServletResponse response, UUID id){
        FileMetaData fileMetaData = fileRepository.getFileMetaData(id);
        try (OutputStream response_data = response.getOutputStream()){
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition","attachment; filename=\"" + fileMetaData.getFile_name() + "\"");
            minioblob.downloadFile(dotenv.get("MY_BUCKET_NAME"),fileMetaData.getFile_name(),response_data);
        } catch (Exception e) {
            throw new RuntimeException("Error Trying To Download File");
        }
   }
}