package com.shf.edu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.commonutils.JwtUtils;
import com.shf.commonutils.R;
import com.shf.edu.client.OrderClient;
import com.shf.edu.entity.Course;
import com.shf.edu.entity.Teacher;
import com.shf.edu.entity.chapter.ChapterVo;
import com.shf.edu.entity.vo.CourseInfoVo;
import com.shf.edu.entity.vo.CourseQueryVo;
import com.shf.edu.entity.vo.CourseWebVo;
import com.shf.edu.service.ChapterService;
import com.shf.edu.service.CourseService;
import com.shf.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api("课程管理")
@RestController
@RequestMapping("/api/edu/course")
@CrossOrigin
public class CourseAPIController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private OrderClient orderClient;

    @ApiOperation(value = "分页课程列表")
    @PostMapping(value = "{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            @RequestBody(required = false) CourseQueryVo courseQuery) {
        Page<Course> pageParam = new Page<Course>(page, limit);
        Map<String, Object> map = courseService.pageListWeb(pageParam, courseQuery);
        return R.ok().data(map);
    }

    @ApiOperation("根据ID查询课程")
    @GetMapping("{courseId}")
    public R getById(@ApiParam(name = "courseId", value = "课程ID", required = true)
                     @PathVariable String courseId,
                     HttpServletRequest request) {
//        查询课程信息和讲师信息
        CourseWebVo courseWebVo = courseService.selectInfoWebById(courseId);

//        查询当前课程的章节信息
        List<ChapterVo> chapterVoList = chapterService.getChapterVideoByCourseID(courseId);

        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        //远程调用，判断课程是否被购买
        boolean buyCourse = orderClient.isBuyCourse(memberIdByJwtToken, courseId);

        return R.ok()
                .data("course", courseWebVo)
                .data("chapterVoList", chapterVoList)
                .data("isbuy",buyCourse);
    }

    @ApiOperation("根据课程id查询课程信息")
    @GetMapping("getDto/{courseId}")
    public CourseInfoVo getCourseInfoDto(@PathVariable String courseId) {
        CourseInfoVo courseInfoFormById = courseService.getCourseInfoFormById(courseId);
        return courseInfoFormById;
    }
}
