package com.shf.cms.controller;

import com.shf.cms.entity.Banner;
import com.shf.cms.service.BannerService;
import com.shf.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/cms/banner")
@Api("网站首页Banner列表")
@CrossOrigin
public class BannerApiController {
    @Autowired
    private BannerService bannerService;

    @Cacheable(value = "banner",key = "'getAllBanner'")
    @ApiOperation("获取首页banner")
    @GetMapping("getAllBanner")
    public R index() {
        List<Banner> list  = bannerService.selectIndexList();
        return R.ok().data("bannerList", list);
    }

}
