package com.example.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("store")
public class StorageProperties {
    private String location = "uploading";
}
