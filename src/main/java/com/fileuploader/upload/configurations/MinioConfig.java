package com.fileuploader.upload.configurations;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
    @Bean
    public MinioClient minioClient(){
        MinioClient minioClient = MinioClient
                .builder()
                .endpoint("http://localhost:9000")
                .credentials("minioadmin", "minioadmin123")
                .build();
        return minioClient;
    }
}
