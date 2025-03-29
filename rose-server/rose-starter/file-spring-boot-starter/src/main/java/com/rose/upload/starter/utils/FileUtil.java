package com.rose.upload.starter.utils;

import com.rose.upload.starter.vo.FileUploadVO;
import org.springframework.web.multipart.MultipartFile;

public interface FileUtil {
    FileUploadVO upload(MultipartFile file);
}
