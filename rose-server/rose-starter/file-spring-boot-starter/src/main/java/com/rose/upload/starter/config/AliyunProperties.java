package com.rose.upload.starter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "file.aliyun")
public class AliyunProperties {
    private String accessKeyId;
    private String accessKeySecret;
    private String endpoint;
    private String bucketName;
}