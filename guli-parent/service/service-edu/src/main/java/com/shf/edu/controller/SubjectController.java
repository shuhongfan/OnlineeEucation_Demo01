package com.shf.edu.controller;


import com.alibaba.excel.EasyExcel;
import com.shf.commonutils.R;
import com.shf.commonutils.ResultCodeEnum;
import com.shf.edu.entity.vo.SubjectNestedVo;
import com.shf.edu.service.SubjectService;
import com.shf.servicebase.exception.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-01
 */
@Api("课程分类管理")
@CrossOrigin
@RestController
@RequestMapping("/admin/edu/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    /**
     * 添加课程分类
     *
     * @param file
     * @return
     */
    @ApiOperation(value = "Excel批量导入")
    @PostMapping("addSubject")
    public R addSubject(@RequestParam("file") MultipartFile file) {
        try {
            subjectService.addSubject(file, subjectService);
            return R.ok();
        } catch (Exception e) {
            throw new GuliException(ResultCodeEnum.FILE_UPLOAD_ERROR);
        }
    }

    @ApiOperation(value = "Excel批量导出")
    @GetMapping("exportSubject")
    public void exportSubject(HttpServletResponse response) {
        try {
            // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("课程分类列表", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

            EasyExcel.write(response.getOutputStream(), ExceptionHandler.class)
                    .sheet("课程分类列表")
                    .doWrite(subjectService.exportSubject());
        } catch (IOException e) {
            throw new GuliException(ResultCodeEnum.EXPORT_DATA_ERROR);
        }
    }

    @ApiOperation(value = "嵌套数据列表")
    @GetMapping
    public R nestedList() {
        List<SubjectNestedVo> subjectNestedVoList = subjectService.nestedList();
        return R.ok().data("items", subjectNestedVoList);
    }
}

