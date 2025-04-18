package com.fileuploader.upload.startUpInitializers;

import jakarta.annotation.PostConstruct;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class  DataBaseInitializer {
    String create_tbl_user = "CREATE TABLE IF NOT EXISTS public.tbl_user + (" +
            "email text not null " +
            "password text NOT NULL " +
            "CONSTRAINT tbl_user_pkey PRIMARY KEY (id)) ";
    String create_tbl_filemetadata = "CREATE TABLE IF NOT EXISTS public.tbl_user + (" +
            "id uuid NOT NULL" +
            "file_name text NOT NULL,"+
            "stored_path text NOT NULL,"+
            "content_type text NOT NULL,"+
            "file_size bigint NOT NULL,"+
            "uploaded_by text NOT NULL,"+
            "CONSTRAINT tbl_filemetadata_pkey PRIMARY KEY (id),"+
            "CONSTRAINT uploaded_by FOREIGN KEY (uploaded_by)"+
            "REFERENCES public.tbl_user (email) MATCH SIMPLE"+
            "ON UPDATE NO ACTION"+
            "ON DELETE NO ACTION";
    private final JdbcTemplate jdbcTemplate;
    public DataBaseInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @PostConstruct
    public void postgresInitializer(){
        if(databaseSchemaExists("tbl_url")){
            return;
        }
        LoggerFactory.getLogger(this.getClass()).info("Database Schema User Doesn't exist creating the schema");
        createDatabaseSchema();
    }
    private boolean databaseSchemaExists(String tablename) {
        try{
            String query = "SELECT to_regclass(?)";
            String result = jdbcTemplate.queryForObject(query,String.class, tablename);
            return result.isEmpty();
        }catch(DataAccessException e){
            LoggerFactory.getLogger(this.getClass()).warn("Couldn't determine if database contains any tables, skipping creation");
            return true;
        }
    }
    private void createDatabaseSchema() {
        jdbcTemplate.execute(create_tbl_user);
        jdbcTemplate.execute(create_tbl_filemetadata);
    }
}