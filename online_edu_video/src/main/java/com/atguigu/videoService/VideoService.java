package com.atguigu.videoService;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface VideoService {
    /**
     * 上传文件
     * @param file
     * @return
     */
    String uploadAliyunVideo(MultipartFile file) ;

    /**
     * 删除文件
     * @param videoId
     */
    void deleteAliyunVideo(String videoId);

    /**
     * 获取视频播放凭证
     * @param videoId
     * @return
     */
    String getVideoPlayAuth(String videoId);
}
