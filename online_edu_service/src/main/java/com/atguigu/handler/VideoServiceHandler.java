package com.atguigu.handler;

import com.atguigu.edu.service.VideoServiceFeign;
import com.atguigu.response.RetVal;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName VideoServiceHandler
 * @Description:
 * @Author xiaoxionghui
 * @Date 2020/11/13 16:02
 * @Version V1.0
 **/
@Component
public class VideoServiceHandler implements VideoServiceFeign {
    @Override
    public RetVal deleteSingleVideo(String videoId) {
        return RetVal.success().message("返回兜底数据");
    }

    @Override
    public RetVal deleteMultiVideo(List<String> videoIdList) {
        return RetVal.success().message("返回兜底数据");
    }
}