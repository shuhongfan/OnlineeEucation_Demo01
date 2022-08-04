package com.shf.edu.client;

import com.shf.commonutils.ordervo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name = "service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {
    @PostMapping("/ucenter/member/getInfoUc/{id}")
    public UcenterMemberOrder getInfo(@PathVariable String id);
}
