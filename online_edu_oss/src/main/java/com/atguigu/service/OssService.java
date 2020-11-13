package com.atguigu.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    String uploadFile(MultipartFile multipartFile) throws Exception;

    boolean deleteFile(String fileName);
}
