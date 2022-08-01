package com.shf.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.edu.entity.Teacher;
import com.shf.edu.mapper.TeacherMapper;
import com.shf.edu.query.TeacherQuery;
import com.shf.edu.service.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-31
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    /**
     * 分页讲师列表
     *
     * @param eduTeacherPage
     * @param teacherQuery
     */
    @Override
    public void pageQuery(Page<Teacher> eduTeacherPage, TeacherQuery teacherQuery) {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.orderByAsc("sort");

        if (teacherQuery == null) {
            baseMapper.selectPage(eduTeacherPage, teacherQueryWrapper);
        }

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        teacherQueryWrapper
                .like(!StringUtils.isEmpty(name), "name", name)
                .eq(!StringUtils.isEmpty(name), "level", level)
                .ge(!StringUtils.isEmpty(begin), "gmt_create", begin)
                .le(!StringUtils.isEmpty(end), "gmt_create", end);

        baseMapper.selectPage(eduTeacherPage, teacherQueryWrapper);
    }


}
