package com.fileuploader.upload.utils;

import io.minio.*;
import io.minio.errors.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class MinioBlobStore implements BlobStoreHandler{
    //The exceptions in these classes are added to the method signature due to the fact that they will be caught when used within spring web by controller advices or default exception handler
    private final MinioClient minioClient;

    public MinioBlobStore(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @Override
    public boolean createBucket(String name) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioClient.makeBucket(
                MakeBucketArgs
                        .builder()
                        .bucket(name)
                        .build()
        );
        return true;
    }
    @Override
    public boolean checkIfBucketExists(String name) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return minioClient.bucketExists(
                BucketExistsArgs
                        .builder()
                        .bucket(name)
                        .build()
        );
    }
    @Override
    public void uploadFile(String bucketName, String objectName, String pathToFile) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioClient.putObject(
                PutObjectArgs
                        .builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(new BufferedInputStream(new FileInputStream(pathToFile)),-1,10486760)
                        .build()
        );
    }
    @Override
    public void uploadFile(String bucketName, String objectName, InputStream inputStream) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioClient.putObject(
                PutObjectArgs
                        .builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(new BufferedInputStream(inputStream),-1,10486760)
                        .build()
        );

    }
    @Override
    public byte[] downloadFile(String bucketName, String objectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return minioClient.getObject(
                GetObjectArgs
                        .builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build()
        ).readAllBytes();
    }

    @Override
    public void downloadFile(String bucketName, String objectName, OutputStream response) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        GetObjectResponse object = minioClient.getObject(
                GetObjectArgs
                        .builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build()
        );
        int counter = 0;
        while((counter = object.read()) != -1){
            response.write(counter);
        }
    }

    @Override
    public void deleteFile(String bucketName, String objectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioClient.removeObject(
                RemoveObjectArgs
                        .builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build()
        );
    }
}