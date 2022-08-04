package com.shf.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.edu.entity.Teacher;
import com.shf.edu.query.TeacherQuery;

import java.util.List;
import java.util.Map;

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

    /**
     * 查询前4条名师
     * @return
     */
    List<Teacher> first4Teacher();

    /**
     * 分页讲师列表
     * @param pageParam
     * @return
     */
    Map<String, Object> pageListWeb(Page<Teacher> pageParam);
}
