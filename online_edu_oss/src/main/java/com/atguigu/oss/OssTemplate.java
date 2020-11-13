package com.atguigu.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName OssTemplate
 * @Description: 工具类
 * @Author xiaoxionghui
 * @Date 2020/11/5 17:06
 * @Version V1.0
 **/
@Component
public class OssTemplate {
    @Value("${oss.endpoint}")
    private String endpoint;
    @Value("${oss.accessKeyId}")
    private String accessKeyId;
    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${oss.bucketName}")
    private String bucketName;

    /**
     * 上传单个文件流
     * @throws FileNotFoundException
     */
    public String testUpload(String fileName,InputStream inputStream) throws FileNotFoundException {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        ossClient.putObject(bucketName, fileName, inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
        return "https://"+bucketName+"."+endpoint+"/"+fileName;
    }

    /**
     * 删除单个文件
     */
    public void fileDeleteTest(String fileName){

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, fileName);

        // 关闭OSSClient。
        ossClient.shutdown();

    }

    /**
     * 批量删除文件
     */
    public void testDeleteMultiFile(List<String> fileNameList){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(fileNameList));
        deleteObjectsResult.getDeletedObjects();

        // 关闭OSSClient。
        ossClient.shutdown();

    }
}
