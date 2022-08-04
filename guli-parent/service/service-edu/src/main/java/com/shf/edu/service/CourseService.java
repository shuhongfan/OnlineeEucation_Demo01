package com.shf.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.edu.entity.CourseQuery;
import com.shf.edu.entity.vo.CourseInfoVo;
import com.shf.edu.entity.vo.CoursePublishVo;
import com.shf.edu.entity.vo.CourseQueryVo;
import com.shf.edu.entity.vo.CourseWebVo;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据ID获取课程发布信息
     * @param id
     * @return
     */
    CoursePublishVo getCoursePublishVoById(String id);

    /**
     * 根据id发布课程
     *
     * @param id
     * @return
     */
    boolean publishCourseById(String id);

    /**
     * 分页课程列表
     * @param pageParam
     * @param courseQuery
     */
    void pageQuery(Page<Course> pageParam, CourseQuery courseQuery);

    /**
     * 根据ID删除课程
     * @param id
     * @return
     */
    boolean removeCourseById(String id);

    /**
     * 查询前8条热门课程
     * @return
     */
    List<Course> first8Course();

    /**
     * 根据讲师id查询出这个讲师的课程列表
     * @param id
     * @return
     */
    List<Course> selectByTeacherId(String id);

    /**
     * 分页课程列表
     * @param pageParam
     * @param courseQuery
     * @return
     */
    Map<String, Object> pageListWeb(Page<Course> pageParam, CourseQueryVo courseQuery);

    /**
     * 查询课程信息和讲师信息
     * @param courseId
     * @return
     */
    CourseWebVo selectInfoWebById(String courseId);

    /**
     * 更新课程浏览数
     * @param id
     */
    void updatePageViewCount(String id);
}
