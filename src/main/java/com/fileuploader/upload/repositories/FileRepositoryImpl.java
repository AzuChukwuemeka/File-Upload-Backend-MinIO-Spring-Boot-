package com.fileuploader.upload.repositories;

import com.fileuploader.upload.dataclasses.FileMetaData;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class FileRepositoryImpl implements FileRepositoryI{
    String DELETE_FILE_BY_ID = "delete from tbl_filemetadata where id = ?";
    String CREATE_FILE = "INSERT INTO tbl_user (id,file_name,stored_path,content_type,file_size,uploaded_by) VALUES (?,?,?,?,?,?)";

    String GET_FILE_ID = "select * from tbl_filemetadata where id = ?";
    String GET_FILES_BY_CREATOR = "select * from tbl_filemetadata where uploaded_by = ?";

    private final JdbcTemplate jdbcTemplate;
    public FileRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public FileMetaData createFileMetaData(UUID file_id, String filename, String stored_path, String content_type, String uploaded_by) {
        jdbcTemplate.update(CREATE_FILE,new Object[]{});
        return new FileMetaData(file_id,filename,stored_path,content_type,uploaded_by,100L);
    }
    @Override
    public FileMetaData getFileMetaData(UUID id) {
        return jdbcTemplate.queryForObject(GET_FILE_ID,(rs,rw) -> {
            return new FileMetaData(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("file_name"),
                    rs.getString("stored_path"),
                    rs.getString("content_type"),
                    rs.getString("uploaded_by"),
                    rs.getInt("file_size")
            );
        }, new Object[]{id});
    }
    @Override
    public List<FileMetaData> getFilesOwnedByUser(String username) {
        return jdbcTemplate.query(GET_FILES_BY_CREATOR,(rs,rw) -> {
            return new FileMetaData(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("file_name"),
                    rs.getString("stored_path"),
                    rs.getString("content_type"),
                    rs.getString("uploaded_by"),
                    rs.getInt("file_size")
            );
        }, new Object[]{username});
    }
    @Override
    public void deleteFileById(UUID uuid) {
        jdbcTemplate.update(DELETE_FILE_BY_ID,new Object[]{uuid});
    }
}
