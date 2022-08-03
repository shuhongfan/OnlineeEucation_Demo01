package com.shf.vod.controller;

import com.shf.commonutils.R;
import com.shf.vod.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api("阿里云视频点播微服务")
@CrossOrigin
@RestController
@RequestMapping("/admin/vod/video")
public class VideoAdminController {
    @Autowired
    private VideoService videoService;

    @ApiOperation("上传视频")
    @PostMapping("upload")
    public R uploadVideo(@ApiParam(name = "file", value = "文件", required = true)
                         @RequestParam("file") MultipartFile file) {
        String uploadVideo = videoService.uploadVideo(file);
        return R.ok().message("视频上传成功").data("videoId", uploadVideo);
    }

    @ApiOperation("删除视频")
    @DeleteMapping("{videoId}")
    public R removeVideo(
            @ApiParam(name = "videoId", value = "云端视频ID", required = true)
            @PathVariable String videoId) {
        videoService.removeVideo(videoId);
        return R.ok().message("视频删除成功");
    }
}
