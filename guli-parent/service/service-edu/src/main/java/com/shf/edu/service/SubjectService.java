package com.shf.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shf.edu.entity.Subject;
import com.shf.edu.entity.excel.ExcelSubjectData;
import com.shf.edu.entity.vo.SubjectNestedVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SubjectService extends IService<Subject> {

    /**
     * 添加课程分类
     *
     * @param file
     */
    void addSubject(MultipartFile file, SubjectService subjectService);

    /**
     * 嵌套数据列表
     *
     * @return
     */
    List<SubjectNestedVo> nestedList();

    /**
     * Excel批量导出
     *
     * @return
     */
    List<ExcelSubjectData> exportSubject();
}
