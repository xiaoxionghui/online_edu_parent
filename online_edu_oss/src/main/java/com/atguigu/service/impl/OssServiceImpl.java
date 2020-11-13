package com.atguigu.service.impl;

import com.atguigu.oss.OssTemplate;
import com.atguigu.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * @ClassName OssServiceImpl
 * @Description:
 * @Author xiaoxionghui
 * @Date 2020/11/5 17:40
 * @Version V1.0
 **/
@Service
public class OssServiceImpl implements OssService {

    @Autowired
    private OssTemplate ossTemplate;

    /**
     * 文件上传
     * @param multipartFile
     * @return
     * @throws Exception
     */
    @Override
    public String uploadFile(MultipartFile multipartFile) throws Exception{
        String filename = multipartFile.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf(".",filename.length()));
        filename= UUID.randomUUID().toString().replaceAll("_","")+suffix;
        InputStream inputStream = multipartFile.getInputStream();
        String resUrl = ossTemplate.testUpload(filename, inputStream);
        return resUrl;
    }

    /**
     * 删除单个文件
     * @param fileName
     * @return
     */
    @Override
    public boolean deleteFile(String fileName) {
        ossTemplate.fileDeleteTest(fileName);
        return true;
    }
}
