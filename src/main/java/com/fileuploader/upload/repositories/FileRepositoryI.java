package com.fileuploader.upload.repositories;

import com.fileuploader.upload.dataclasses.FileMetaData;

import java.util.List;
import java.util.UUID;

public interface FileRepositoryI {
    public FileMetaData createFileMetaData(UUID file_id, String filename, String stored_path, String content_type, String uploaded_by);

    public FileMetaData getFileMetaData(UUID id);
    public List<FileMetaData> getFilesOwnedByUser(String username);
    public void deleteFileById(UUID uuid);
}
