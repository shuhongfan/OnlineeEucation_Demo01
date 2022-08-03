package com.shf.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.edu.entity.Course;
import com.shf.edu.entity.CourseDescription;
import com.shf.edu.entity.CourseQuery;
import com.shf.edu.entity.vo.CourseInfoVo;
import com.shf.edu.entity.vo.CoursePublishVo;
import com.shf.edu.mapper.CourseMapper;
import com.shf.edu.service.ChapterService;
import com.shf.edu.service.CourseDescriptionService;
import com.shf.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.edu.service.VideoService;
import com.shf.servicebase.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Autowired
    private VideoService videoService;

    @Autowired
    private ChapterService chapterService;

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

    /**
     * 根据ID获取课程发布信息
     * @param id
     * @return
     */
    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {
        CoursePublishVo coursePublishVo = baseMapper.selectCoursePublishVoById(id);
        return coursePublishVo;
    }

    /**
     * 根据id发布课程
     *
     * @param id
     * @return
     */
    @Override
    public boolean publishCourseById(String id) {
        Course course = new Course();
        course.setId(id);
        course.setStatus(Course.COURSE_NORMAL);
        int count = baseMapper.updateById(course);
        return count>0;
    }

    /**
     * 分页课程列表
     * @param pageParam
     * @param courseQuery
     */
    @Override
    public void pageQuery(Page<Course> pageParam, CourseQuery courseQuery) {
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("gmt_create");

        if (courseQuery == null) {
            baseMapper.selectPage(pageParam, courseQueryWrapper);
            return;
        }

        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();

        courseQueryWrapper.like(!StringUtils.isEmpty(title), "title", title);
        courseQueryWrapper.eq(!StringUtils.isEmpty(teacherId), "teacherId", teacherId);
        courseQueryWrapper.ge(!StringUtils.isEmpty(subjectParentId), "subject_parent_id", subjectParentId);
        courseQueryWrapper.ge(!StringUtils.isEmpty(subjectId), "subject_id", subjectId);

        baseMapper.selectPage(pageParam, courseQueryWrapper);
    }

    /**
     * 根据ID删除课程
     * @param id
     * @return
     */
    @Override
    public boolean removeCourseById(String id) {
//        1. 根据课程id删除小节
        videoService.removeByCourseId(id);
//        2.根据课程id删除章节
        chapterService.remoChapterByCourseId(id);
//        3.根据课程id删除描述
        courseDescriptionService.removeById(id);
//        4.根据课程id删除课程本身
        int result = baseMapper.deleteById(id);
        return result>0;
    }
}
