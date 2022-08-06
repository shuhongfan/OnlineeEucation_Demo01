package com.shf.order.client;

import com.shf.commonutils.ordervo.CourseWebVoOrder;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-edu")
public interface EduClient {
    @ApiOperation("根据课程id查询课程信息")
    @GetMapping("/edu/api/course/getDto/{courseId}")
    public CourseWebVoOrder getCourseInfoDto(@PathVariable String courseId);
}
