package com.shf.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.shf.commonutils.R;
import com.shf.vod.utils.AliyunVodSDKUtils;
import com.shf.vod.utils.ConstantPropertiesUtil;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@Api("阿里云视频点播微服务")
@RestController
@RequestMapping("/vod/video")
public class VideoController {
    @GetMapping("get-play-auth/{videoId}")
    public R getVideoPlayAuth(@PathVariable("videoId") String videoId) throws Exception {
//        获取阿里云存储相关常量
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;

//        初始化
        DefaultAcsClient defaultAcsClient = AliyunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);

//        请求
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);

//        响应
        GetVideoPlayAuthResponse response = defaultAcsClient.getAcsResponse(request);

//        得到播放凭证
        String playAuth = response.getPlayAuth();

//        返回结果
        return R.ok().message("获取凭证成功").data("playAuth", playAuth);

    }
}
