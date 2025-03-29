package com.rose.upload.starter.config;

import com.rose.upload.starter.utils.FileUtil;
import com.rose.upload.starter.utils.impl.AliyunUtil;
import com.rose.upload.starter.utils.impl.QiniuUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({FileProperties.class, AliyunProperties.class, QiniuProperties.class})
public class FileAutoConfiguration {

    @Bean
    @ConditionalOnProperty(name = "file.provider", havingValue = "qiniu")
    public FileUtil qiniuUtil(QiniuProperties qiniuProperties) {
        return new QiniuUtil(qiniuProperties);
    }

    @Bean
    @ConditionalOnProperty(name = "file.provider", havingValue = "aliyun")
    public FileUtil aliyunUtil(AliyunProperties aliyunProperties) {
        return new AliyunUtil(aliyunProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public FileUtil defaultUtil() {
        throw new IllegalStateException("No file provider configured");
    }
}
