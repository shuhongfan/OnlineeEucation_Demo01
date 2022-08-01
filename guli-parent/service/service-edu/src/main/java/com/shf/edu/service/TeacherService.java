package com.shf.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.edu.entity.Teacher;
import com.shf.edu.query.TeacherQuery;

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
     *
     * @param eduTeacherPage
     * @param teacherQuery
     */
    void pageQuery(Page<Teacher> eduTeacherPage, TeacherQuery teacherQuery);
}
