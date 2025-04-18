package com.fileuploader.upload.startUpInitializers;

import com.fileuploader.upload.utils.BlobStoreHandler;
import com.fileuploader.upload.utils.MinioBlobStore;
import io.github.cdimascio.dotenv.Dotenv;
import io.minio.MinioClient;
import jakarta.annotation.PostConstruct;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MinioClientInitializer {
    private final BlobStoreHandler minioBlobStore;
    private final Dotenv dotenv;
    public MinioClientInitializer(BlobStoreHandler minioBlobStore, Dotenv dotenv) {
        this.minioBlobStore = minioBlobStore;
        this.dotenv = dotenv;
    }
    @PostConstruct
    public void setUpMinioClient(){
        try{
            if(minioBlobStore.checkIfBucketExists(dotenv.get("MY_BUCKET_NAME"))){
                return;
            };
            minioBlobStore.createBucket(dotenv.get("MY_BUCKET_NAME"));
        }catch(Exception e){
            LoggerFactory.getLogger(this.getClass()).warn("Issues encountered communicating with cloud store for the first time");
            throw new RuntimeException(e.getMessage());
        }
    }
}