package com.shf.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shf.edu.entity.Subject;
import com.shf.edu.entity.excel.ExcelSubjectData;
import com.shf.edu.service.SubjectService;
import com.shf.servicebase.exception.GuliException;

public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {

    public SubjectService subjectService;

    public SubjectExcelListener() {
    }

    //    创建有参数构造，传递subjectService用于操作数据库
    public SubjectExcelListener(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     * 一行一行读取excel内容
     *
     * @param user
     * @param analysisContext
     */
    @Override
    public void invoke(ExcelSubjectData user, AnalysisContext analysisContext) {
//        System.out.println(user);
        if (user == null) {
            throw new GuliException(20001, "添加失败");
        }

//        添加一级分类
        Subject existOneSubject = existSubject(subjectService, user.getOneSubjectName(), "0");
        if (existOneSubject == null) {
            existOneSubject = new Subject();
            existOneSubject.setTitle(user.getOneSubjectName());
            existOneSubject.setParentId("0");
            subjectService.save(existOneSubject);
        }

//        获取一级分类id值
        String pid = existOneSubject.getId();

//        添加二级分类
        Subject existTwoSubject = existSubject(subjectService, user.getTwoSubjectName(), pid);
        if (existTwoSubject == null) {
            existTwoSubject = new Subject();
            existTwoSubject.setTitle(user.getTwoSubjectName());
            existTwoSubject.setParentId(pid);
            subjectService.save(existTwoSubject);
        }
    }

    /**
     * 判断分类是否重复
     */
    private Subject existSubject(SubjectService subjectService, String name, String pid) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name)
                .eq("parent_id", pid);
        return subjectService.getOne(wrapper);
    }

    /**
     * 读取完成后执行
     *
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
