package com.atguigu.videoService.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.utils.VideoUtils;
import com.atguigu.videoService.VideoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName VideoServiceImpl
 * @Description:
 * @Author xiaoxionghui
 * @Date 2020/11/11 19:58
 * @Version V1.0
 **/
@Service
public class VideoServiceImpl implements VideoService {

    @Value("${oss.accessKeyId}")
    private String accessKeyId;
    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;

    /**
     * 上传视频
     * @param file
     * @return
     */
    @Override
    public String uploadAliyunVideo(MultipartFile file)  {
        String videoId = null;
        try {
            String fileName = file.getOriginalFilename();
            String title = fileName.substring(0,fileName.lastIndexOf("."));
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            if (response.isSuccess()){
                videoId = response.getVideoId();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return videoId;
    }

    /**
     * 删除视频
     * @param videoId
     */
    @Override
    public void deleteAliyunVideo(String videoId) {
        try {
            DefaultAcsClient client = VideoUtils.initVodClient(accessKeyId, accessKeySecret);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取播放凭证
     * @param videoId
     * @return
     */
    @Override
    public String getVideoPlayAuth(String videoId) {
        GetVideoPlayAuthResponse response = null;
        try {
            DefaultAcsClient defaultAcsClient = VideoUtils.initVodClient(accessKeyId, accessKeySecret);
            response = new GetVideoPlayAuthResponse();
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(videoId);
            response = defaultAcsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return response.getPlayAuth();
    }
}
