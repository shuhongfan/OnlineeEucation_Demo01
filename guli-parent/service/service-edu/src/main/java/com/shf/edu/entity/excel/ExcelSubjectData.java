package com.shf.edu.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ExcelSubjectData {
    @ExcelProperty(value = "一级分类", index = 0)
    private String oneSubjectName;

    @ExcelProperty(value = "二级分类", index = 1)
    private String twoSubjectName;

    public String getOneSubjectName() {
        return oneSubjectName;
    }

    public void setOneSubjectName(String oneSubjectName) {
        this.oneSubjectName = oneSubjectName;
    }

    public String getTwoSubjectName() {
        return twoSubjectName;
    }

    public void setTwoSubjectName(String twoSubjectName) {
        this.twoSubjectName = twoSubjectName;
    }
}
