package com.shf.commonutils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum ResultCodeEnum {
    EXPORT_DATA_ERROR(30002, "数据导出失败"),
    FILE_UPLOAD_ERROR(30001, "文件上传失败"),
    UPLOAD_COURSE_ERROR(20002, "添加课程分类失败");


    private Integer code;//状态码
    private String message;//消息
}
