package com.shf.edu.service.impl;

import com.shf.edu.entity.Course;
import com.shf.edu.entity.CourseDescription;
import com.shf.edu.entity.vo.CourseInfoVo;
import com.shf.edu.mapper.CourseMapper;
import com.shf.edu.service.CourseDescriptionService;
import com.shf.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.servicebase.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-01
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    /**
     * 新增课程
     * @param courseInfoVo
     * @return
     */
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
//        保存课程基本信息
        Course course = new Course();
        course.setStatus(Course.COURSE_DRAFT);
        course.setIsDeleted(0);
        BeanUtils.copyProperties(courseInfoVo, course);
        boolean isSave = this.save(course);
        if (!isSave) {
            throw new GuliException(20001, "课程信息保存失败");
        }

//        保存课程详情信息
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
//        设置描述id就是课程id
        courseDescription.setId(course.getId());
        boolean isSaveCourseDescription = courseDescriptionService.save(courseDescription);
        if (!isSaveCourseDescription) {
            throw new GuliException(20001, "课程详情信息保存失败");
        }
        return course.getId();
    }

    /**
     * 根据ID查询课程
     * @param id
     * @return
     */
    @Override
    public CourseInfoVo getCourseInfoFormById(String id) {
        Course course = this.getById(id);
        if (course == null) {
            throw new GuliException(20001, "数据不存在");
        }
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(course, courseInfoVo);

        CourseDescription courseDescription = courseDescriptionService.getById(id);
        if (courseDescription != null) {
            courseInfoVo.setDescription(courseDescription.getDescription());
        }
        return courseInfoVo;
    }

    /**
     * 更新课程
     * @param courseInfoVo
     */
    @Override
    public void updateCourseInfoById(CourseInfoVo courseInfoVo) {
//        保存课程基本信息
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo, course);
        boolean resultCourseInfo = this.updateById(course);
        if (!resultCourseInfo) {
            throw new GuliException(20001, "课程信息保存失败");
        }

//        保存课程详情信息
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(courseInfoVo.getId());
        boolean resultDescription = courseDescriptionService.updateById(courseDescription);
        if (!resultDescription) {
            throw new GuliException(20001, "课程详情信息保存失败");
        }
    }
}
