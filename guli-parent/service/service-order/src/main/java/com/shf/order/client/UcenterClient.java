package com.shf.order.client;

import com.shf.commonutils.ordervo.UcenterMemberOrder;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    @ApiOperation("根据用户id获取用户信息")
    @PostMapping("/ucenter/member/getInfoUc/{id}")
    public UcenterMemberOrder getInfo(@PathVariable String id);
}
