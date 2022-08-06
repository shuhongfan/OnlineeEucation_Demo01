package com.shf.edu.controller;


import com.shf.commonutils.R;
import com.shf.edu.entity.chapter.VideoVo;
import com.shf.edu.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-01
 */
@Api("课时管理")
@RestController
@RequestMapping("/edu/admin/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @ApiOperation(value = "新增课时")
    @PostMapping("save-video-info")
    public R save(
            @ApiParam(name = "VideoVo", value = "课程对象", required = true)
            @RequestBody VideoVo videoVo) {
        videoService.saveVideoInfo(videoVo);
        return R.ok();
    }

    @ApiOperation(value = "根据ID查询课时")
    @GetMapping("video-info/{id}")
    public R getVideoInfoById(
            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id) {
        VideoVo videoVo = videoService.getVideoInfoById(id);
        return R.ok().data("item", videoVo);
    }

    @ApiOperation(value = "更新课时")
    @PutMapping("update-video-info")
    public R updateCourseInfoById(
            @ApiParam(name = "VideoVo", value = "课时基本信息", required = true)
            @RequestBody VideoVo videoVo) {
        videoService.updateVideoInfo(videoVo);
        return R.ok();
    }

    @ApiOperation("根据ID删除课时")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id",value = "课时ID",required = true)
            @PathVariable String id) {
        boolean result = videoService.removeVideoById(id);
        if (result) {
            return R.ok();
        } else {
            return R.error().message("删除失败");
        }
    }

}

