package com.atguigu;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName OssTest
 * @Description:
 * @Author xiaoxionghui
 * @Date 2020/11/5 16:32
 * @Version V1.0
 **/
public class OssTest {
    /**
     * 上传单个文件流
     * @throws FileNotFoundException
     */
    @Test
    public void testUpload() throws FileNotFoundException {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = "LTAI4GKMdajb7mgkkDmqNdE6";
        String accessKeySecret = "PNwSie8Chtq2ZaX4b5a9b3JXFDQS06";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        InputStream inputStream = new FileInputStream("C:\\Users\\86183\\Pictures\\Saved Pictures\\images.jfif");
        ossClient.putObject("fuhaoniupi", "images.jpg", inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 删除单个文件
     */
    @Test
    public void fileDeleteTest(){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4GKMdajb7mgkkDmqNdE6";
        String accessKeySecret = "PNwSie8Chtq2ZaX4b5a9b3JXFDQS06";
        String bucketName = "fuhaoniupi";
        String objectName = "images (1).jpg";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();

    }

    /**
     * 批量删除文件
     */
    @Test
    public void testDeleteMultiFile(){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4GKMdajb7mgkkDmqNdE6";
        String accessKeySecret = "PNwSie8Chtq2ZaX4b5a9b3JXFDQS06";
        String bucketName = "fuhaoniupi";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。key等同于ObjectName，表示删除OSS文件时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        List<String> keys = new ArrayList<String>();
        keys.add("beautifulgirl1");
        keys.add("beautifulgirl2");
        keys.add("beautifulgirl3");

        DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(keys));
        List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();

        // 关闭OSSClient。
        ossClient.shutdown();

    }

}
