package com.shf.edu.mapper;

import com.shf.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shf.edu.entity.vo.CoursePublishVo;
import com.shf.edu.entity.vo.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-01
 */
public interface CourseMapper extends BaseMapper<Course> {
    /**
     * 根据ID获取课程发布信息
     * @param id
     * @return
     */
    CoursePublishVo selectCoursePublishVoById(String id);

    /**
     * 查询课程信息和讲师信息
     * @param courseId
     * @return
     */
    CourseWebVo selectInfoWebById(String courseId);
}
