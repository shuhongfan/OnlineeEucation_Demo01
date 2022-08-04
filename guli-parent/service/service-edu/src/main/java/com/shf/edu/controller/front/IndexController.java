package com.shf.edu.controller.front;

import com.shf.commonutils.R;
import com.shf.edu.entity.Course;
import com.shf.edu.entity.Teacher;
import com.shf.edu.service.CourseService;
import com.shf.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/edu/index")
@CrossOrigin
public class IndexController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    /**
     * 查询前8条热门课程，查询前4条名师
     * @return
     */
    @GetMapping("index")
    @Cacheable(value = "index",key = "'IndexController'")
    public R index() {
//        查询前8条热门课程
        List<Course> courseList = courseService.first8Course();

        //查询前4条名师
        List<Teacher> teacherList = teacherService.first4Teacher();

        return R.ok().data("courseList", courseList).data("teacherList", teacherList);
    }
}
