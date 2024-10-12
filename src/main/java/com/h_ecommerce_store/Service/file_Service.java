package com.h_ecommerce_store.Service;
import org.springframework.beans.factory.annotation.Value;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Service
public class file_Service
{
    @Value("${app.firebase.file}")
    private String file_firebase_path;
    @Value("${app.firebase.bucket}")
    private String bucket;
    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException
    {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile))
        {
            fos.write(multipartFile.getBytes());
            fos.close();
        }
        return tempFile;
    }
    private String uploadFile(File file, String fileName,String type) throws IOException
    {
        BlobId blobId = BlobId.of(bucket, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(type).build();
        InputStream inputStream = file_Service.class.getClassLoader().getResourceAsStream(file_firebase_path);
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/"+bucket+ "/o/%s?alt=media";
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }
    private String getExtension(String fileName)
    {
        return fileName.substring(fileName.lastIndexOf("."));
    }


    public String upload(MultipartFile multipartFile,String type)
    {
        try
        {
            String fileName = multipartFile.getOriginalFilename();
            File file = this.convertToFile(multipartFile, fileName);
            String URL = this.uploadFile(file, fileName,type);
            if(file.delete())
            {
                System.out.println("File deleted successfully");
            }
            return URL;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "Image couldn't upload, Something went wrong";
        }
    }
}
