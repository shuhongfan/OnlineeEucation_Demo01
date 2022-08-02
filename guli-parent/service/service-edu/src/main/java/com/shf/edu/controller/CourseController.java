package com.shf.edu.controller;


import com.shf.commonutils.R;
import com.shf.edu.entity.vo.CourseInfoVo;
import com.shf.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-01
 */
@Api(description = "课程管理")
@CrossOrigin //跨域
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
    public R updateCourseInfoById(@ApiParam(name = "CourseInfoVo",value = "课程基本信息",required = true)
                                  @RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfoById(courseInfoVo);
        return R.ok().data("courseId", courseInfoVo.getId());
    }
}

