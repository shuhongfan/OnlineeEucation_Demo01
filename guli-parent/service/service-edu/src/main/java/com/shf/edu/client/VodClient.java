package com.shf.edu.client;

import com.shf.commonutils.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {
    @ApiOperation("删除视频")
    @DeleteMapping("/vod/admin/video/{videoId}")
    public R removeVideo(
            @ApiParam(name = "videoId", value = "云端视频ID", required = true)
            @PathVariable String videoId);

    @ApiOperation("批量删除视频")
    @DeleteMapping("/vod/admin/video/delete-batch")
    public R removeVideoList(
            @ApiParam(name = "videoIdList", value = "云端视频ID", required = true)
            @RequestParam("videoList") List<String> videoList
    );
}
