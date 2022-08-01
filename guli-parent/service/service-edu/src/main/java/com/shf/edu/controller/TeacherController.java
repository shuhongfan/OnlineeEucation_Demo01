package com.shf.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.commonutils.R;
import com.shf.edu.entity.Teacher;
import com.shf.edu.query.TeacherQuery;
import com.shf.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author shuhongfan
 * @since 2022-07-31
 */
@Api("讲师管理")
@RestController
@RequestMapping("/admin/edu/teacher")
@CrossOrigin
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    /**
     * 定义在类上：@Api
     * 定义在方法上：@ApiOperation
     * 定义在参数上：@ApiParam
     *
     * @return
     */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping
    public R list() {
        List<Teacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }


    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        boolean isRemove = teacherService.removeById(id);
        if (isRemove) {
            return R.ok().message("删除成功");
        } else {
            return R.error().message("删除失败");
        }
    }

    @ApiOperation("分页讲师列表")
    @PostMapping("{page}/{limit}")
    public R pageList(@ApiParam(name = "page", value = "当前页码", required = true)
                      @PathVariable Long page,

                      @ApiParam(name = "limit", value = "每页显示记录", required = true)
                      @PathVariable Long limit,

                      @ApiParam(name = "teacherQuery", value = "查询对象", required = false)
                      @RequestBody(required = false) TeacherQuery teacherQuery
    ) {
//        创建page对象
        Page<Teacher> eduTeacherPage = new Page<>(page, limit);

//        调用方法实现分页
//        调用方法的时候，底层封装，把分页所有数据封装到 eduTeacherPage对象中
        teacherService.pageQuery(eduTeacherPage, teacherQuery);

        List<Teacher> records = eduTeacherPage.getRecords();
        long total = eduTeacherPage.getTotal();

//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("total", total);
//        map.put("rows", records);

        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation("新增讲师")
    @PostMapping
    public R save(@ApiParam(name = "teacher", value = "讲师对象", required = true)
                  @RequestBody Teacher teacher) {
        teacherService.save(teacher);
        return R.ok();
    }

    @ApiOperation("根据ID查询讲师")
    @GetMapping("{id}")
    public R getByID(@ApiParam(name = "id", value = "讲师ID", required = true)
                     @PathVariable String id) {
//        try {
//            int i = 10 / 0;
//        } catch (Exception e) {
//            throw new GuliException(20001, "出现自定义异常");
//        }
        Teacher teacher = teacherService.getById(id);
        return R.ok().data("item", teacher);
    }

    @ApiOperation("根据ID修改讲师")
    @PutMapping("{id}")
    public R updateById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id,

            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody Teacher teacher
    ) {
        teacher.setId(id);
        teacherService.updateById(teacher);
        return R.ok();
    }
}

