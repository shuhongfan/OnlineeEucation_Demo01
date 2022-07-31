package com.shf.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.eduservice.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.eduservice.query.TeacherQuery;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-31
 */
public interface TeacherService extends IService<Teacher> {

    /**
     * 分页讲师列表
     * @param eduTeacherPage
     * @param teacherQuery
     */
    void pageQuery(Page<Teacher> eduTeacherPage, TeacherQuery teacherQuery);
}
