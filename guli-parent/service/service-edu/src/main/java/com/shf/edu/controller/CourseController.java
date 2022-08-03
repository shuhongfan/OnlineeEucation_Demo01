package com.shf.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.commonutils.R;
import com.shf.edu.entity.Course;
import com.shf.edu.entity.CourseQuery;
import com.shf.edu.entity.vo.CourseInfoVo;
import com.shf.edu.entity.vo.CoursePublishVo;
import com.shf.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
@Api("课程管理")
@CrossOrigin
@RestController
@RequestMapping("/admin/edu/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @ApiOperation("新增课程")
    @PostMapping("save-course-info")
    public R saveCourseInfo(
            @ApiParam(name = "CourseInfoForm", value = "课程基本信息", required = true)
            @RequestBody CourseInfoVo courseInfoVo
    ) {
        String courseId = courseService.saveCourseInfo(courseInfoVo);
        if (!StringUtils.isEmpty(courseId)) {
            return R.ok().data("courseId", courseId);
        } else {
            return R.error().message("保存失败");
        }
    }

    @ApiOperation(value = "根据ID查询课程")
    @GetMapping("course-info/{id}")
    public R getById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id
    ) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfoFormById(id);
        return R.ok().data("item", courseInfoVo);
    }

    @ApiOperation(value = "更新课程")
    @PutMapping("update-course-info")
    public R updateCourseInfoById(@ApiParam(name = "CourseInfoVo", value = "课程基本信息", required = true)
                                  @RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfoById(courseInfoVo);
        return R.ok().data("courseId", courseInfoVo.getId());
    }

    @ApiOperation("根据ID获取课程发布信息")
    @GetMapping("course-publish-info/{id}")
    public R getCoursePublishVoById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id) {
        CoursePublishVo coursePublishVoById = courseService.getCoursePublishVoById(id);
        return R.ok().data("item", coursePublishVoById);
    }

    @ApiOperation(value = "根据id发布课程")
    @PostMapping("publish-course/{id}")
    public R publishCourseById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id) {
        courseService.publishCourseById(id);
        return R.ok();
    }

    @ApiOperation("分页课程列表")
    @GetMapping("{page}/{limit}")
    public R pageQuery(@ApiParam(name = "page", value = "当前页码", required = true)
                       @PathVariable Long page,

                       @ApiParam(name = "limit", value = "每页记录数", required = true)
                       @PathVariable Long limit,

                       @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                       CourseQuery courseQuery) {
        Page<Course> pageParam = new Page<>(page, limit);
        courseService.pageQuery(pageParam, courseQuery);

        List<Course> records = pageParam.getRecords();
        long total = pageParam.getTotal();

        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation("根据ID删除课程")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id) {
        boolean result = courseService.removeCourseById(id);
        if (result) {
            return R.ok();
        } else {
            return R.error().message("删除失败");
        }
    }
}

