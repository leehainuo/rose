package com.rose.upload.starter.utils.impl;

import com.alibaba.fastjson2.JSON;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.DownloadUrl;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.rose.upload.starter.config.QiniuProperties;
import com.rose.upload.starter.utils.FileUtil;
import com.rose.upload.starter.vo.FileUploadVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
public class QiniuUtil implements FileUtil {

    private final QiniuProperties qiniuProperties;

    public QiniuUtil(QiniuProperties qiniuProperties) {
        this.qiniuProperties = qiniuProperties;
    }

    @Override
    public FileUploadVO upload(MultipartFile file) {
        FileUploadVO fileUploadVO = new FileUploadVO();
        try {
            //1. 获取凭证
            Auth auth = Auth.create(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());
            //2. 获取上传凭证
            String upToken = auth.uploadToken(qiniuProperties.getBucketName());
            //3. 配置 所在区域
            Configuration cfg = new Configuration(Region.autoRegion());
            //4. 上传管理工具
            UploadManager uploadManager = new UploadManager(cfg);
            //5. 补全参数
            InputStream inputStream = file.getInputStream();
            // 默认不指定key的情况下，以文件内容的hash值作为文件名 时间日期前缀
            // 获取file的地址
            String originalFilename = file.getOriginalFilename();
            //file扩展名 extendFileName
            String extendFileName = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 时间日期前缀
            LocalDate currentDate = LocalDate.now();
            // 时间日期格式化
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd/");
            // 新文件名 等于 时间日期前缀 + 随机uuid + 文件扩展名
            String newFileName = currentDate.format(formatter) + UUID.randomUUID().toString().replace("-", "") + extendFileName;
            log.info("newFileName:{}", newFileName);
            //6. 上传
            Response res = uploadManager.put(inputStream, newFileName, upToken, null, null);
            DefaultPutRet defaultPutRet = JSON.parseObject(res.bodyString(), DefaultPutRet.class);
            //七牛云的文件地址 域名 + 新文件名
            DownloadUrl url = new DownloadUrl(qiniuProperties.getPrefix(), false, newFileName);
            //过期的时间戳 格林威治时间1970年1月1日0时0分0秒0毫秒到当前时间的毫秒值就叫时间戳
            long deadline = System.currentTimeMillis() + 3600000L;
            // 生成下载链接
            auth = Auth.create(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());
            String urlStr = url.buildURL(auth, deadline);
            //将下载链接 放到fileUploadVO中
            fileUploadVO.setUrl(urlStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileUploadVO;

    }
}
