package com.srgstart.blog.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author srgstart
 * @Create 2021/4/1 15:08
 * @Description TODO
 */
public interface FileService {
    /**
     * 上传文件到阿里云oss
     * @param file :上传的文件
     * @return ：文件路径
     */
    String upload(MultipartFile file);
}
