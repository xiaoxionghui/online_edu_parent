package com.atguigu;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.*;

import java.util.List;

/**
 * @ClassName UploadDemo
 * @Description:
 * @Author xiaoxionghui
 * @Date 2020/11/11 9:01
 * @Version V1.0
 **/
public class UploadDemo {

    //账号AK信息请填写(必选)
    private static final String accessKeyId = "LTAI4GKMdajb7mgkkDmqNdE6";
    //账号AK信息请填写(必选)
    private static final String accessKeySecret = "PNwSie8Chtq2ZaX4b5a9b3JXFDQS06";

    /*1.获取播放地址函数*/
    public static void  getPlayInfo(String[] argv) throws Exception {
        DefaultAcsClient client = VideoUtils.initVodClient("LTAI4GKMdajb7mgkkDmqNdE6", "PNwSie8Chtq2ZaX4b5a9b3JXFDQS06");
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        try {
            GetPlayInfoRequest request = new GetPlayInfoRequest();
            request.setVideoId("32303b5f2f1f4cdd83f3864f29401164");
            response = client.getAcsResponse(request);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            }
            //Base信息
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

    /*2.获取播放凭证函数 */
    public static void getVideoPlayAuth(String[] argv) throws Exception {
        DefaultAcsClient client = VideoUtils.initVodClient("LTAI4GKMdajb7mgkkDmqNdE6", "PNwSie8Chtq2ZaX4b5a9b3JXFDQS06");
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        try {
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId("254f0f8b191d48d4b9a00723952bd989");
            response = client.getAcsResponse(request);
            //播放凭证
            System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
            //VideoMeta信息
            System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }


    /*3.删除视频*/
    public static void deleteViedo(String[] argv) throws Exception {
        DefaultAcsClient client = VideoUtils.initVodClient("LTAI4GKMdajb7mgkkDmqNdE6", "PNwSie8Chtq2ZaX4b5a9b3JXFDQS06");
        DeleteVideoResponse response = new DeleteVideoResponse();
        try {
            DeleteVideoRequest request = new DeleteVideoRequest();
            //支持传入多个视频ID，多个用逗号分隔
            request.setVideoIds("b7d9b85c8a204d81bb230f45ee4c3b2f");
            response = client.getAcsResponse(request);
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }


    public static void main(String[] args) {
        //1.音视频上传-本地文件上传
        //视频标题(必选)
        String title = "你想看的，都在这里";
        //本地文件上传和文件流上传时，文件名称为上传文件绝对路径，如:/User/sample/文件名称.mp4 (必选)
        //文件名必须包含扩展名
        String fileName = "d:\\work\\磊哥专属.mp4";
        //本地文件上传
//        testUploadVideo(accessKeyId, accessKeySecret, title, fileName);

    }

    /**
     * 本地文件上传接口
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @param title
     * @param fileName
     */
    private static void testUploadVideo(String accessKeyId, String accessKeySecret, String title, String fileName) {
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }
}
