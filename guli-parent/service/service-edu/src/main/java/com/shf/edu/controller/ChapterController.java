package com.shf.edu.controller;


import com.shf.commonutils.R;
import com.shf.edu.entity.Chapter;
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
@RequestMapping("/edu/admin/chapter")
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

    @ApiOperation("新增章节")
    @PostMapping
    public R save(
            @ApiParam(name = "chapterVo",value = "章节对象",required = true)
            @RequestBody Chapter chapter) {
        chapterService.save(chapter);
        return R.ok();
    }

    @ApiOperation("根据ID查询章节")
    @GetMapping("{id}")
    public R getById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable String id
    ) {
        Chapter chapter = chapterService.getById(id);
        return R.ok().data("item", chapter);
    }

    @ApiOperation("根据ID修改章节")
    @PutMapping()
    public R updateById(
                        @ApiParam(name = "chapter", value = "章节对象", required = true)
                        @RequestBody Chapter chapter) {
        chapterService.updateById(chapter);
        return R.ok();
    }

    @ApiOperation("根据ID删除章节")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "章节ID",required = true)
            @PathVariable String id) {
        boolean result = chapterService.remoChapterById(id);
        if (result) {
            return R.ok();
        } else {
            return R.error().message("删除失败");
        }
    }
}

