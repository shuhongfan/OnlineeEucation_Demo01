package com.shf.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.edu.entity.Subject;
import com.shf.edu.entity.excel.ExcelSubjectData;
import com.shf.edu.entity.vo.SubjectNestedVo;
import com.shf.edu.entity.vo.SubjectVo;
import com.shf.edu.listener.SubjectExcelListener;
import com.shf.edu.mapper.SubjectMapper;
import com.shf.edu.service.SubjectService;
import com.shf.servicebase.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {


    /**
     * 添加课程分类
     *
     * @param file
     */
    @Override
    public void addSubject(MultipartFile file, SubjectService subjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(
                            inputStream,
                            ExcelSubjectData.class,
                            new SubjectExcelListener(subjectService))
                    .sheet()
                    .doRead();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new GuliException(200001, e.getMessage());
        }
    }

    /**
     * 嵌套数据列表
     *
     * @return
     */
    @Override
    public List<SubjectNestedVo> nestedList() {
//        最终要显示的数据列表
        ArrayList<SubjectNestedVo> subjectNestedVoArrayList = new ArrayList<>();

//        获取一级分类数据记录
        QueryWrapper<Subject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", 0)
                .orderByAsc("sort", "id");
        List<Subject> subjectOne = baseMapper.selectList(wrapperOne);

//        获取二级分类数据记录
        QueryWrapper<Subject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo
                .ne("parent_id", 0)
                .orderByAsc("sort", "id");
        List<Subject> subjectTwo = baseMapper.selectList(wrapperTwo);

//        填充一级分类数据Vo对象
        subjectOne.forEach(subject -> {
//            创建一级类别VO对象
            SubjectNestedVo subjectNestedVo = new SubjectNestedVo();
            BeanUtils.copyProperties(subject, subjectNestedVo);
            subjectNestedVoArrayList.add(subjectNestedVo);

//            填充二级分类Vo对象
            ArrayList<SubjectVo> subjectVoArrayList = new ArrayList<>();
            subjectTwo.forEach(sub -> {
                if (sub.getParentId().equals(subject.getId())) {
//                    创建二级类别VO对象
                    SubjectVo subjectVo = new SubjectVo();
                    BeanUtils.copyProperties(sub, subjectVo);
                    subjectVoArrayList.add(subjectVo);
                }
            });

//            建立连接
            subjectNestedVo.setChildren(subjectVoArrayList);
        });

        return subjectNestedVoArrayList;
    }

    /**
     * Excel批量导出
     *
     * @return
     */
    @Override
    public List<ExcelSubjectData> exportSubject() {
        List<ExcelSubjectData> excelSubjectDataList = new ArrayList<>();

        LambdaQueryWrapper<Subject> wrapperOne = new LambdaQueryWrapper<>();
        wrapperOne.ge(Subject::getParentId, 0)
                .orderByAsc(Subject::getSort, Subject::getId);
        List<Subject> subjectOne = baseMapper.selectList(wrapperOne);

        subjectOne.forEach(subject -> {
            LambdaQueryWrapper<Subject> wrapperTwo = new LambdaQueryWrapper<>();
            wrapperTwo.eq(Subject::getParentId, subject.getId())
                    .orderByAsc(Subject::getSort, Subject::getId);
            List<Subject> subjectTwo = baseMapper.selectList(wrapperTwo);

            subjectTwo.forEach(sub -> {
                ExcelSubjectData excelSubjectData = new ExcelSubjectData();
                excelSubjectData.setOneSubjectName(subject.getTitle());
                excelSubjectData.setTwoSubjectName(sub.getTitle());

                excelSubjectDataList.add(excelSubjectData);
            });

        });

        return excelSubjectDataList;
    }
}
