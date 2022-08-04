package com.shf.cms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.cms.entity.Banner;
import com.shf.cms.mapper.BannerMapper;
import com.shf.cms.service.BannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author shuhongfan
 * @since 2022-08-03
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

    /**
     * 获取Banner分页列表
     * @param pageParam
     * @param o
     */
    @Override
    @Cacheable(value = "banner",key = "'selectPage'")
    public void pageBanner(Page<Banner> pageParam, Object o) {
        baseMapper.selectPage(pageParam, null);
    }

    /**
     * 获取Banner
     * @param id
     * @return
     */
    @Override
    public Banner getBannerById(String id) {
        return baseMapper.selectById(id);
    }

    /**
     * 新增Banner
     * @param banner
     */
    @Override
    @CacheEvict(value = "banner",allEntries = true)
    public void saveBanner(Banner banner) {
        baseMapper.insert(banner);
    }

    /**
     * 修改Banner
     * @param banner
     */
    @Override
    @CacheEvict(value = "banner",allEntries = true)
    public void updateBannerById(Banner banner) {
        baseMapper.updateById(banner);
    }

    /**
     * 删除Banner
     * @param id
     */
    @Override
    @CacheEvict(value = "banner",allEntries = true)
    public void removeBannerById(String id) {
        baseMapper.deleteById(id);
    }

    /**
     * 获取首页banner
     * @return
     */
    @Override
    public List<Banner> selectIndexList() {
        return baseMapper.selectList(null);
    }

}
