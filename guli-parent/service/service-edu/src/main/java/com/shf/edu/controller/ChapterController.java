package com.shf.edu.controller;


import com.shf.commonutils.R;
import com.shf.edu.entity.chapter.ChapterVo;
import com.shf.edu.service.ChapterService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-01
 */
@RestController
@CrossOrigin
@RequestMapping("/admin/edu/chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @ApiOperation("课程大纲列表")
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@ApiParam(value = "courseId", name = "课程ID", required = true)
                             @PathVariable String courseId) {
        List<ChapterVo> chapterVoList = chapterService.getChapterVideoByCourseID(courseId);
        return R.ok().data("list", chapterVoList);
    }
}

