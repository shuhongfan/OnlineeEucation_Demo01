package com.shf.cms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.cms.entity.Banner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-03
 */
public interface BannerService extends IService<Banner> {

    /**
     * 获取Banner分页列表
     * @param pageParam
     * @param o
     */
    void pageBanner(Page<Banner> pageParam, Object o);

    /**
     * 获取Banner
     * @param id
     * @return
     */
    Banner getBannerById(String id);

    /**
     * 新增Banner
     * @param banner
     */
    void saveBanner(Banner banner);

    /**
     * 修改Banner
     * @param banner
     */
    void updateBannerById(Banner banner);

    /**
     * 删除Banner
     * @param id
     */
    void removeBannerById(String id);

    /**
     * 获取首页banner
     * @return
     */
    List<Banner> selectIndexList();
}
