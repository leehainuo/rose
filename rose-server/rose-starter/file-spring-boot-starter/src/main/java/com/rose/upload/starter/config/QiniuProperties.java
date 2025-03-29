package com.rose.upload.starter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "file.qiniu")
public class QiniuProperties {
    private String accessKey;
    private String secretKey;
    private String prefix;
    private String bucketName;
}
