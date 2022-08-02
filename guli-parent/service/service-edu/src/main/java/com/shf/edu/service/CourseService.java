package com.shf.edu.service;

import com.shf.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.edu.entity.vo.CourseInfoVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-01
 */
public interface CourseService extends IService<Course> {

    /**
     * 新增课程
     * @param courseInfoVo
     * @return
     */
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据ID查询课程
     * @param id
     * @return
     */
    CourseInfoVo getCourseInfoFormById(String id);

    /**
     * 更新课程
     * @param courseInfoVo
     */
    void updateCourseInfoById(CourseInfoVo courseInfoVo);
}
