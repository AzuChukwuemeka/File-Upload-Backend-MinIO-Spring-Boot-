package com.fileuploader.upload.dataclasses;

import java.sql.Timestamp;
import java.util.UUID;

public class FileMetaData {
    public UUID file_id;
    public String file_name;
    public String stored_path;
    public String content_type;
    public String uploaded_by;
    public long file_size;

    public FileMetaData(UUID file_id, String file_name, String stored_path, String content_type, String uploaded_by, long file_size) {
        this.file_id = file_id;
        this.file_name = file_name;
        this.stored_path = stored_path;
        this.content_type = content_type;
        this.uploaded_by = uploaded_by;
        this.file_size = file_size;
    }

    public UUID getFile_id() {
        return file_id;
    }

    public void setFile_id(UUID file_id) {
        this.file_id = file_id;
    }

    public String getFile_name() {
        return file_name;
    }
    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }
    public String getStored_path() {
        return stored_path;
    }
    public void setStored_path(String stored_path) {
        this.stored_path = stored_path;
    }
    public String getContent_type() {
        return content_type;
    }
    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }
    public long getFile_size() {
        return file_size;
    }
    public void setFile_size(long file_size) {
        this.file_size = file_size;
    }

    public String getUploaded_by() {
        return uploaded_by;
    }

    public void setUploaded_by(String uploaded_by) {
        this.uploaded_by = uploaded_by;
    }

    @Override
    public String toString() {
        return "FileMetaData{" +
                "file_id=" + file_id +
                ", file_name='" + file_name + '\'' +
                ", stored_path='" + stored_path + '\'' +
                ", content_type='" + content_type + '\'' +
                ", uploaded_by='" + uploaded_by + '\'' +
                ", file_size=" + file_size +
                '}';
    }
}