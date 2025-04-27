package com.example.Ecommerce.client.service.cloudinary;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import com.example.Ecommerce.client.service.cloudinary.contract.FileUpload;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;

@Service
public class FileUploadAdapter implements FileUpload {

    private final Cloudinary cloudinary;

    public FileUploadAdapter(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String updloadFile(MultipartFile multipartFile) throws IOException {
        
        return cloudinary.uploader()
                .upload(multipartFile.getBytes(),
                        Map.of("public_id", UUID.randomUUID().toString()))
                .get("url")
                .toString();        
                
    }
    
}
