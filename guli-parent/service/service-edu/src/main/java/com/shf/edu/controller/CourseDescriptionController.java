package com.shf.edu.controller;


import com.shf.commonutils.R;
import com.shf.edu.entity.vo.CourseInfoVo;
import com.shf.edu.service.CourseDescriptionService;
import com.shf.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程简介 前端控制器
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-01
 */

@Api(description = "课程简介管理")
@CrossOrigin //跨域
@RestController
@RequestMapping("/admin/edu/course")
public class CourseDescriptionController {
    @Autowired
    private CourseDescriptionService courseDescriptionService;

}
