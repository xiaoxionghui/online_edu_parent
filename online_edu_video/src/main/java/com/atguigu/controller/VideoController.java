package com.atguigu.controller;

import com.atguigu.response.RetVal;
import com.atguigu.videoService.VideoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName VideoController
 * @Description:
 * @Author xiaoxionghui
 * @Date 2020/11/11 18:59
 * @Version V1.0
 **/
@CrossOrigin
@RestController
@RequestMapping("/aliyun")
public class VideoController {

    @Autowired
    private VideoService videoService;

    /**
     * 上传视频
     * @param file
     * @return
     */
    @PostMapping("uploadAliyunVideo")
    public RetVal uploadAliyunVideo(MultipartFile file)  {
        String videoId = videoService.uploadAliyunVideo(file);
        return RetVal.success().data("videoId",videoId);
    }

    /**
     * 删除视频
     * @param videoId
     * @return
     */
    @DeleteMapping("{videoId}")
    public RetVal deleteSingleVideo(@PathVariable("videoId") String videoId){
        videoService.deleteAliyunVideo(videoId);
        return RetVal.success();
    }

    /**
     * 删除多个视频
     * @param videoIdList
     * @return
     */
    @DeleteMapping("deleteMultiVideo")
    public RetVal deleteMultiVideo(@RequestParam("videoIdList") List<String> videoIdList){
        String videoId = StringUtils.join(videoIdList,",");
        videoService.deleteAliyunVideo(videoId);
        return RetVal.success();
    }


}
