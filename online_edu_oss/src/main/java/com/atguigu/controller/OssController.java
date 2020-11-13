package com.atguigu.controller;

import com.atguigu.response.RetVal;
import com.atguigu.service.OssService;
import com.sun.org.apache.bcel.internal.generic.RET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName OssController
 * @Description:
 * @Author xiaoxionghui
 * @Date 2020/11/5 17:26
 * @Version V1.0
 **/
@CrossOrigin
@RestController
@RequestMapping("/oss")
public class OssController {
    @Autowired
    private OssService ossService;

    /**
     * 文件上传接口
     * @param multipartFile
     * @return
     */
    @PostMapping("uploadFile")
    public RetVal uploadFile(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        String resUrl = ossService.uploadFile(multipartFile);
        return RetVal.success().data("resUrl",resUrl);
    }

    @DeleteMapping("deleteFile")
    public RetVal deleteFile(String fileName){
        boolean flag = ossService.deleteFile(fileName);
        return RetVal.success();
    }

}
