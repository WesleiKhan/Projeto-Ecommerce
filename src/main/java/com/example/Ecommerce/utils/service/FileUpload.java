package com.example.Ecommerce.utils.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileUpload {
    
    String updloadFile(MultipartFile multipartFile) throws IOException;
}
