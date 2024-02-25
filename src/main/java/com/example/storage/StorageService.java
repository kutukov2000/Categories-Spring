package com.example.storage;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    void init() throws IOException;

    String saveImage(MultipartFile file, FileSaveFormat format) throws IOException;

    void deleteImage(String fileName) throws IOException;
}
